package org.websocket;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.Random;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.path.json.JsonPath.given;


public class WebSocketTest {
    private SocketContext context;

    private String getRandomId() {
        int leftLimit = 97; //letter 'a'
        int rightLimit = 122; //letter 'z'
        int targetStringLength = 7;

        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private String getSocketConnectUrl() {
        JsonPath response = given()
                .post("https://api.kucoin.com/api/v1/bullet-public")
                .then().log().body().extract()
                .response().jsonPath();
        String token = response.getString("data.token");
        String socketBaseEndpoint = response.getString("data.instanceServers[0].endpoint");
        return socketBaseEndpoint + "?token=" + token + "&connectId=" + getRandomId();
    }

    @Test
    public void socketKucoin() throws JsonProcessingException {
        SubscribeModel subscribeModel = new SubscribeModel();
        subscribeModel.setId(Math.abs(new Random().nextInt()));
        subscribeModel.setResponse(true);
        subscribeModel.setType("subscribe");
        subscribeModel.setTopic("/market/ticker:BTC-USDT");
        subscribeModel.setPrivateChannel(false);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(subscribeModel); //переводим модель в json в виде строки, чтобы передать в body

        context = new SocketContext();
        context.setBody(json);
        context.setTimeOut(10);
        context.setURI(getSocketConnectUrl());
        WebClient.getInstance().connectToSocket(context);
/*
        int size = context.getMassageList().size();
        System.out.println(size);

        context.getMassageList().get(0);
        //можно использовать pojo сайты для перевода json в объект и наоборот
        // и таким образом создать классы для полученных сообщений и инфо о самой крипто-валюте
*/
        String firstNormalMessage = context.getMassageList()//достаем лист с сообщениями
                .stream().filter(x -> x.contains("\"type\":\"message\"")) //фильтруем сообщения, которые содержат "type":"message"
                .findFirst().orElseThrow(() -> new RuntimeException("No normal message found"));//ищим первое и если нет ни одного, то выкидываем ошибку
        SocketMessageModel messageOne = objectMapper.readValue(firstNormalMessage, SocketMessageModel.class);

        String lastNormalMessage = context.getMassageList().get(context.getMassageList().size() - 1);
        SocketMessageModel messageLast = objectMapper.readValue(lastNormalMessage, SocketMessageModel.class);
    }

    @Test
    public void socketUI_ReceiveText() {
        Selenide.open("https://www.piesocket.com/websocket-tester");
        String url = $x("//input[@id='email']").getValue();
        $x("//button[@type='submit']").click();
        String expectedMessage = "threadqa message";
        context = new SocketContext();
        context.setURI(url);
        context.setBody(expectedMessage);
        context.setExpectedMessage(expectedMessage);
        context.setTimeOut(5);

        WebClient.getInstance().connectToSocket(context);
        $x("//*[@id='consoleLog']")
                .shouldHave(Condition.partialText(context.getBody()));
    }

    @Test
    public void socketUI_SendText() {
        Selenide.open("https://www.piesocket.com/websocket-tester");
        SelenideElement input = $x("//input[@id='email']");
        SelenideElement button = $x("//button[@type='submit']");
        String expectedMessage = "threadqa message";

        String url = input.getValue();
        button.click();

        Runnable sendUIMessage = new Runnable() {
            @Override
            public void run() {
                input.clear();
                input.sendKeys(expectedMessage);
                button.click();
            }
        };

        context = new SocketContext();
        context.setURI(url);
        context.setExpectedMessage(expectedMessage);
        context.setTimeOut(5);
        context.setRunnable(sendUIMessage);

        WebClient.getInstance().connectToSocket(context);
    }
}

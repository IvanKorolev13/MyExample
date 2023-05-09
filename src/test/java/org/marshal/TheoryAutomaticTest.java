package org.marshal;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class TheoryAutomaticTest {
    @Test
    public void methodClick (String data){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*", "--headless", "--window-size=1920,1080");

        WebDriver driver = new ChromeDriver();

        driver.get("https://dev.by");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //неявное задерживание

        WebElement element = driver.findElement(By.xpath("//a[text()='Вход']"));
        String par = element.getCssValue("transition"); //поиск CSS у элемент
        System.out.println(par + data); //вывод параметра

        element.click();
    }
    @Test
    public void methodSendKeys(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://udemy.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement element = driver.findElement(By.xpath("//input[@name='q']"));

        element.sendKeys(Keys.DELETE, "Java", Keys.ENTER); //удаляем, ввод данных и нажатие кнопки
    }
    @Test
    public void methodActions(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://dev.by");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Actions action = new Actions(driver);

        WebElement element = driver.findElement(By.xpath("//a[text()='Вход']"));

        action
                .click(element) //кликнуть на элемент
                .moveToElement(element) //навести курсом на элемент
                .moveToElement(element, 100, 100)
                .keyDown(Keys.BACK_SPACE).keyUp(Keys.BACK_SPACE) //нажать кнопку и отпустить кнопку
                .clickAndHold(element) //нажать и удержать клик
                .release() //отпустить клик
                .contextClick(element) //нажать правую кнопку мыши
                .doubleClick(element) //двойной клик
                .build() //собрать все выше
                .perform(); //выполнить все выше

        try {
            driver.get("https://crossbrowsertesting.github.io/drag-and-drop");
            Thread.sleep(2000);
            WebElement element1 = driver.findElement(By.id("draggable"));
            WebElement element2 = driver.findElement(By.id("droppable"));

            action
                    .moveToElement(element1)
                    .clickAndHold()
                    .moveToElement(element2)
                    .release()
                    .build()
                    .perform();
            action.dragAndDrop(element1, element2); //то же, что и до этого
            action.dragAndDropBy(element1, 100, 100);
            //забрать элемент и перетащить его на 100px вправо, 100px вниз

            action.moveToElement(element1).pause(Duration.ofSeconds(1000)).click().build().perform();
            //навести на элемент, дождаться чего-то 1 сек, кликнуть, собрать

        } catch (InterruptedException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
    @Test
    public void methodDelayWaitingToDisappear(){
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //неявное ожидание
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //явное ожидание

        try {
            driver.get("https://pagination.js.org/"); //заходим на сайт
            Thread.sleep(2000); //ждем загрузку
            //кладем в elements данные в списке
            List<WebElement> elements
                    = driver.findElements(By.xpath("div[@class='data-container']/ul/li"));
            //кладем в pages данные по номерам страницы
            List<WebElement> pages
                    = driver.findElements(By.xpath("div[@class='paginationjs-pages']/ul/li"));

            String text = elements.get(6).getText();
            System.out.println(text); //берем 6 элемент в списке на одной странице

            pages.get(2).click(); //переключаемся на 2 страницу
            //ждем пропадания (stalaness) определенного элемента (element 5)
            wait.until(ExpectedConditions.stalenessOf(elements.get(5)));

            //заново собираем скисок элементов на странице, т.к. он уже новый
            elements = driver.findElements(By.xpath("div[@class='data-container']/ul/li"));

            text = elements.get(6).getText();
            System.out.println(text); //берем 6 элемент в списке после переключения страницы

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            driver.quit();
        }
    }
    @Test
    public void methodDelayWaitingExamples(){
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //неявное ожидание
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //явное ожидание

        try {
            driver.get("https://pagination.js.org/"); //заходим на сайт
            Thread.sleep(2000);
            List<WebElement> elements
                    = driver.findElements(By.xpath("div[@class='data-container']/ul/li"));

            wait.until(ExpectedConditions.stalenessOf(elements.get(5)));
            wait.until(ExpectedConditions.attributeContains(elements.get(2),"color", "re"));
            wait.until(ExpectedConditions.invisibilityOf(elements.get(2)));
            wait.until(ExpectedConditions.visibilityOf(elements.get(4)));
            wait.until(ExpectedConditions.textToBePresentInElement(elements.get(1), "td"));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            driver.quit();
        }
    }
    @Test
    public void workingWithModelWindows(){
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://127.0.0.1:5500/index.html");
            Thread.sleep(5000);
            WebElement elementAlert = driver.findElement(By.id("a")); //обычное модальное окно с сообщением
            WebElement elementPromt = driver.findElement(By.id("b")); //модальное окно с полем ввода текста и двумя кнопками
            WebElement element3 = driver.findElement(By.id("c")); //окно с выбором да или нет

            elementAlert.click(); //кликаем на элемент с первым модальным окном
            Alert alert = wait.until(ExpectedConditions.alertIsPresent()); //ждем открытия окна и кладем его в переменную
            alert.accept(); //нажимаем в этом окне ок

            elementPromt.click();
            Alert promt = wait.until(ExpectedConditions.alertIsPresent());
            promt.sendKeys("Super"); //вводим в поле ввода текст
            promt.accept(); //нажимаем ок
            Alert alert2 = wait.until(ExpectedConditions.alertIsPresent()); //ждем открытия следующего сообщения
            Thread.sleep(2000); //смотрим на него
            alert2.accept(); //нажимаем ок

            element3.click();
            Alert alert3 = wait.until(ExpectedConditions.alertIsPresent());
            alert3.dismiss(); //нажимаем отказ

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            driver.quit();
        }
    }
    @Ignore //доделать
    @Test
    public void inputFileInWindow(){
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptException js = (JavascriptException) driver;

        try {
            driver.get("https://127.0.0.1:5500/index.html");
            Thread.sleep(5000);
            /*
            js.executeScript("document.querySelector('#a').setAttribute('style', 'opacity:1')");
            */
            //далее поменять все остальные свойства
            //найти потом этот элемент и передать ему файл с компа через sendKeys("путь к файлу")

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void useAssert(){
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://127.0.0.1:5500/index.html");
            Thread.sleep(5000);
            String title = driver.getTitle();
            Assert.assertEquals(title, "Document");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            driver.quit();
        }
    }
    @Test
    public void method(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://dev.by");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Actions action = new Actions(driver);

        WebElement element = driver.findElement(By.xpath("//a[text()='Вход']"));

    }
    //подключение параметров и данных для тестов
    @DataProvider(name="db") //задаем данные для теста/ов
    public static Object[][] dbData(){
        return new Object[][]{
                {"SQL", new Integer(1)},
                {"NoSQL", new Integer(2)}
        };
    }
    @Parameters({"data"}) //для примера, данные для переменной data получаем через xml
    @Test (groups = {"regressTests"})
    public void testParameter(String e){ //тест будет использовать данные из файла xml, в котором есть значание переменной data
        System.out.println("Test 1 " + e);
    }
    @Test(groups = {"smokeTests", "regressTests"}, dataProvider = "db")
    //group- для запуска теста по suit
    //dataProvider- для подключения данных для теста, которые записаны в аннотации @DataProvider
    public void testData(String e1, int e2){ //тест будет использовать данные из dataProvider = db
        System.out.println("Test 1 " + e1 + " " + e2);
    }

    //зависимости
    @Test (dependsOnMethods = {"testData"}, alwaysRun = true) //true- значит даже если TestData упал- выполняем
    public void testDependence(){
        System.out.println("testDependence зависим от теста testData" +
                "Т.e. сначала выполниться testData, и только после выполниться testDependence");
    }

    //повторный запуск тестов после того как он упал
    //создаем класс Retry, чтобы определить кол-во повторов запуска теста и установки кол-ва разрешенных повторов
    @Test (retryAnalyzer = Retry.class) //переопределяем класс анализа
    public void test1(){
        System.out.println("повторный запуск теста");
    }
}



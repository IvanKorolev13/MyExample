package org.mail;

import org.testng.annotations.Test;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class CreateMailMessageTest {
    @Test
    public void testCreateMailMessage() {
        /**
         * до этого нужно на почте настроить двухфакторную аутотефикацию
         * и создать пароль приложения
         * 1. перейти в google, 2. раздел управление аккаунтом, 3. безопасность, 4. пароли приложения
         * 5. логинимся, 6.создаем пароль, 7. указываем, что приложение будет почта, 8. устройство Win
         * 9. Создать, 10. копируем созданный пароль
         * 11. ДАННЫЙ ПАРОЛЬ ПОДСТАВЛЯЕМ ВМЕСТО password в session
         */
        final String from = "iv.ko.test13@gmail.com";
        String to = "iv.ko.test13@gmail.com";
        String host = "smtp.gmail.com";
        String smtpPort = "465";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(
                properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "vmigiqdctwpkfubh");
                    }
                }
        );

        session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Это будет тема сообщения");
            message.setText("Текст сообщения");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

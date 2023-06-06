package org.websocket;

public class SocketMessageModel {
    private String type;
    private String topic;
    private String subject;
    private TicketDataModel data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public TicketDataModel getData() {
        return data;
    }

    public void setData(TicketDataModel data) {
        this.data = data;
    }

    public SocketMessageModel(String type, String topic, String subject, TicketDataModel data) {
        this.type = type;
        this.topic = topic;
        this.subject = subject;
        this.data = data;
    }

    public SocketMessageModel() {
    }
}

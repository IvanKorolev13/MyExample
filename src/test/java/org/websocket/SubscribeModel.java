package org.websocket;

public class SubscribeModel {
    public long id;
    public String type;
    public String topic;
    public boolean privateChannel;
    public boolean response;

    public SubscribeModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public boolean isPrivateChannel() {
        return privateChannel;
    }

    public void setPrivateChannel(boolean privateChannel) {
        this.privateChannel = privateChannel;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public SubscribeModel(long id, String type, String topic, boolean privateChannel, boolean response) {
        this.id = id;
        this.type = type;
        this.topic = topic;
        this.privateChannel = privateChannel;
        this.response = response;
    }
}

package com.example.androidlabs;

public class Message {
    private String messageText;
    private boolean sent;
    private boolean received;
    long id;

    public Message(String messageText, boolean sent, boolean received, long id) {
        setMessageText(messageText);
        setReceived(received);
        setSent(sent);
        setId(id);

    }

    public Message(String messageText, boolean sent, boolean received) {
        this(messageText,sent,received,0);

    }
    public String getMessageText() {
        return messageText;

    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }



    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

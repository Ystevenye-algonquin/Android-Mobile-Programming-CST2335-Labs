package com.example.androidlabs;

public class Message {
    private String messageText;
    private boolean sent;
    private boolean received;

    public String getMessageText() {
        return messageText;

    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Message(String messageText, boolean sent, boolean received) {
        setMessageText(messageText);
      setReceived(received);
      setSent(sent);
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
}

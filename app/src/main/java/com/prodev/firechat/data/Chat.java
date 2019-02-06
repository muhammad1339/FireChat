package com.prodev.firechat.data;

public class Chat {
    private String msgID;
    private String fromID;
    private String toID;
    private String msgContent;
    private long timeStamp;

    public Chat() {
    }

    public Chat(String msgID, String fromID, String toID, String msgContent, long timeStamp) {
        this.msgID = msgID;
        this.fromID = fromID;
        this.toID = toID;
        this.msgContent = msgContent;
        this.timeStamp = timeStamp;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

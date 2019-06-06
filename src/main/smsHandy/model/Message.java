package smsHandy.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Message {
    private StringProperty content;
    private Date date;
    private StringProperty from;
    private StringProperty to;

    /**
     * Constructor without parameters
     */
    public Message() {}

    /**
     * Constructor with parameters
     * @param content Content of message
     * @param from Phone number of sender
     * @param to Phone number of receiver
     * @param date Date, when message was created
     */
    public Message(String content, String from, String to, Date date) {
        this.content = new SimpleStringProperty(content);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.date = date;
    }

    public StringProperty contentProperty() {
        return content;
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    /**
     * Returns the content of message
     * @return Current content of message
     */
    public String getContent() {
        return content.get();
    }

    /**
     * Sets the message content
     * @param content Content of message
     */
    public void setContent(String content) {
        this.content = new SimpleStringProperty(content);
    }

    /**
     * Returns the date of message, when it was created
     * @return Date of message it was created
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the creation date
     * @param date New date for message
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the sender of message
     * @return Sender of message
     */
    public String getFrom() {
        return from.get();
    }

    /**
     * Sets the message sender
     * @param from Message sender
     */
    public void setFrom(String from) {
        this.from = new SimpleStringProperty(from);
    }

    /**
     * Returns the message receiver
     * @return Message receiver
     */
    public String getTo() {
        return to.get();
    }

    /**
     * Sets the message receiver
     * @param to Message receiver
     */
    public void setTo(String to) {
        this.to = new SimpleStringProperty(to);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (content != null ? !content.equals(message.content) : message.content != null) return false;
        if (date != null ? !date.equals(message.date) : message.date != null) return false;
        if (from != null ? !from.equals(message.from) : message.from != null) return false;
        return to != null ? to.equals(message.to) : message.to == null;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    /**
     * Returns the full body of message
     * @return Message with all data
     */
    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}

package smsHandy.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import smsHandy.exception.NotEnoughBalanceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class SmsHandy {
    private StringProperty number;
    private List<Message> received;
    private List<Message> sent;
    private Provider provider;

    /**
     * Constructor for creation new phone
     *
     * @param provider phone's provider
     */
    public SmsHandy(Provider provider) {
        this.provider = provider;
        sent = FXCollections.observableArrayList();
        received = new ArrayList<>();
    }

    /**
     * Constructor for creation new phone
     *
     * @param number   phone's number
     * @param provider phone's provider
     */
    public SmsHandy(String number, Provider provider) {
        this.number = new SimpleStringProperty(number);
        this.provider = provider;
        sent = new ArrayList<>();
        received = new ArrayList<>();
        provider.register(this);
    }

    /**
     * Method for sending sms
     *
     * @param to      phone's number to which the message should be sent
     * @param content message that should be sent
     */
    public void sendSms(String to, String content) throws NotEnoughBalanceException {
        if (canSendSms()) {
            Message message = new Message(content, getNumber(), to, new Date());
            if (provider.send(message)) {
                sent.add(message);
                payForSms();
            }
        }
    }

    /**
     * Method for checking possibility of sending sms
     *
     * @return can it send sms?
     */
    public abstract boolean canSendSms();

    /**
     * Method for paying for sms
     */
    public abstract void payForSms() throws NotEnoughBalanceException;


    /**
     * Sends the message directly to number without provider
     *
     * @param peer    phone that gets a message
     * @param content message that should be sent
     */
    public void sendSmsDirect(SmsHandy peer, String content) {
        if (provider.getSubscriber().containsKey(peer.getNumber())) {
            Message message = new Message(content, getNumber(), peer.getNumber(), new Date());
            peer.receiveSms(message);
            sent.add(message);
        }
    }

    /**
     * Receives the message that was sent
     *
     * @param message message that is being received
     */
    public void receiveSms(Message message) {
        if (message != null)
            received.add(message);
    }

    /**
     * Finds all sent messages
     */
    public void listSent() {
        System.out.println("The list of sent messages: ");
        sent.forEach(System.out::println);
    }

    /**
     * Finds all received messages
     */
    public void listReceived() {
        System.out.println("The list of received messages: ");
        received.forEach(System.out::println);
    }

    /**
     * Returns the number of phone
     *
     * @return phone's number
     */
    public String getNumber() {
        return number.get();
    }

    public StringProperty   numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number = new SimpleStringProperty(number);
    }

    /**
     * Returns the provider of phone
     *
     * @return provider's number
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Sets the provider for phone
     *
     * @param provider provider for phone
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<Message> getReceived() {
        return received;
    }

    public List<Message> getSent() {
        return sent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsHandy smsHandy = (SmsHandy) o;

        if (number != null ? !number.equals(smsHandy.number) : smsHandy.number != null) return false;
        if (received != null ? !received.equals(smsHandy.received) : smsHandy.received != null) return false;
        if (sent != null ? !sent.equals(smsHandy.sent) : smsHandy.sent != null) return false;
        return provider != null ? provider.equals(smsHandy.provider) : smsHandy.provider == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (received != null ? received.hashCode() : 0);
        result = 31 * result + (sent != null ? sent.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }
}

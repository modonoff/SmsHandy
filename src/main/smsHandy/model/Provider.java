package smsHandy.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;

public class Provider {
    private StringProperty name;
    private Map<String, Integer> credits;
    private Map<String, SmsHandy> subscriber;
    private static List<Provider> providerList = new ArrayList<>();

    /**
     * Constructor without parameters
     */
    public Provider() {}

    /**
     * Constructor for creation new provider
     */
    public Provider(String name) {
        this.name = new SimpleStringProperty(name);
        credits = new HashMap<>();
        subscriber = new HashMap<>();
        providerList.add(this);
    }

    /**
     * Provider sends the message by number that gets from message
     *
     * @param message Message to be sent
     * @return is message sent?
     */
    public boolean send(Message message) {
        Provider provider = findProviderFor(message.getTo());
        if (message.getTo().equals("*101#")) {
            String currentBalance = "Your balance is " + credits.get(message.getFrom()) + " Euro";
            Message response = new Message(currentBalance, "provider", message.getFrom(), new Date());
            findProviderFor(message.getFrom()).getSubscriber().get(message.getFrom()).receiveSms(response);
        }
        if (provider != null) {
            provider.getSubscriber().get(message.getTo()).receiveSms(message);
            return true;
        }
        return false;
    }

    /**
     * Adds new phone to it's list of phones
     *
     * @param smsHandy New phone to be added
     */
    public void register(SmsHandy smsHandy) {
        if (smsHandy != null)
            subscriber.put(smsHandy.getNumber(), smsHandy);
    }

    /**
     * Removes existing from provider
     *
     * @param smsHandy Existing phone to delete
     */
    public void unregister(SmsHandy smsHandy) {
        if (smsHandy != null) {
            subscriber.remove(smsHandy.getNumber());
        }
    }

    /**
     * Adds money to the balance of phone-number
     *
     * @param number phone's number
     * @param amount amount that should be added to balance
     */
    public void deposit(String number, int amount) {
        if (amount <= 0 || number.equals("")) {
            throw new IllegalArgumentException("Wrong amount or number for deposit!");
        }
        if (credits.containsKey(number)) {
            credits.put(number, credits.get(number) + amount);
        }
    }

    /**
     * Shows the balance of number
     *
     * @param number Phone's number for getting balance
     * @return balance of number
     */
    public int getCreditForSmsHandy(String number) {
        if (number.equals("")) {
            throw new IllegalArgumentException("Number cannot be empty!");
        }
        if (credits.containsKey(number))
            return credits.get(number);
        return 0;
    }

    /**
     * Checks if this number exists
     *
     * @param receiver Phone's number
     * @return does such number exist?
     */
    private boolean canSendTo(String receiver) {
        if (receiver.equals("")) {
            throw new IllegalArgumentException("Number cannot be empty!");
        }
        return subscriber.containsKey(receiver);
    }

    /**
     * Searches number's provider
     *
     * @param receiver Phone's number
     * @return provider that belongs to number or null
     */
    private static Provider findProviderFor(String receiver) {
        for (Provider provider : providerList) {
            if (provider.canSendTo(receiver)) {
                return provider;
            }
        }
        return null;
    }

    /**
     * Returns map of numbers and credits
     * @return map of numbers and credits
     */
    public Map<String, Integer> getCredits() {
        return credits;
    }

    /**
     * Returns subscriber of provider
     * @return subscriber of provider
     */
    public Map<String, SmsHandy> getSubscriber() {
        return subscriber;
    }

    /**
     * Returns name of provider
     * @return name of provider
     */
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Returns quantity of registered phones to output in GUI
     * @return number of phones
     */
    public StringProperty quantityOfPhones() {
        int count = getSubscriber().size();
        return new SimpleStringProperty(String.valueOf(count));
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public static List<Provider> getProviderList() {
        return providerList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        if (credits != null ? !credits.equals(provider.credits) : provider.credits != null) return false;
        return subscriber != null ? subscriber.equals(provider.subscriber) : provider.subscriber == null;
    }

    @Override
    public int hashCode() {
        int result = credits != null ? credits.hashCode() : 0;
        result = 31 * result + (subscriber != null ? subscriber.hashCode() : 0);
        return result;
    }
}

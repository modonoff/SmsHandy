package smsHandy.model;

import smsHandy.exception.NotEnoughBalanceException;

public class PrepaidSmsHandy extends SmsHandy {
    private static final int COST_PER_SMS = 10; // Cost of one message
    private int balance = 100; // Start balance of phone

    /**
     * Constructor for creation of new phone with prepaid system
     * @param number Phone number
     * @param provider Phone provider
     */
    public PrepaidSmsHandy(String number, Provider provider) {
        super(number, provider);
        provider.getCredits().put(number, balance);
    }

    public PrepaidSmsHandy(Provider provider) {
        super(provider);
    }

    /**
     * Checks, if the balance is enough to send message
     * @return Is the balance enough?
     */
    @Override
    public boolean canSendSms() {
        return getProvider().getCreditForSmsHandy(getNumber()) >= COST_PER_SMS;
    }

    /**
     * Subtracts the cost of message from balance
     */
    @Override
    public void payForSms() throws NotEnoughBalanceException {
        if (getProvider().getCredits().get(getNumber()) == 0) {
            throw new NotEnoughBalanceException("Balance is already 0");
        }
        getProvider().getCredits().put(getNumber(), getProvider().getCredits().get(getNumber()) - COST_PER_SMS);
    }

    /**
     * Charges the balance
     * @param amount Sum to be recharged
     */
    public void deposit(int amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("Not acceptable value for deposit!");
        }
        getProvider().deposit(getNumber(), amount);
    }

    /**
     * Sets the balance to phone
     * @param balance New balance
     */
    public void setBalance(int balance) {
        if (balance < 0){
            throw new IllegalArgumentException("Not acceptable value for balance!");
        }
        this.balance = balance;
    }
}

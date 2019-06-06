package smsHandy.model;

import smsHandy.exception.NotEnoughBalanceException;

public class TariffPlanSmsHandy extends SmsHandy{
    private int remainingFreeSms = 100; // The number of free messages, that can be sent

    /**
     * Constructor for creation of new phone with Tariff plan
     * @param number Phone number
     * @param provider Phone provider
     */
    public TariffPlanSmsHandy(String number, Provider provider) {
        super(number, provider);
    }

    /**
     * Checks, if there are still enough free messages
     * @return Are there free messages?
     */
    @Override
    public boolean canSendSms() {
        return remainingFreeSms > 0;
    }

    /**
     * Reduces the free messages
     */
    @Override
    public void payForSms() throws NotEnoughBalanceException {
        if (remainingFreeSms <= 0)
            throw new NotEnoughBalanceException("There is no free sms");
        remainingFreeSms -= 1;
    }

    /**
     * Returns the number of remaining free messages
     * @return Number of free messages
     */
    public int getRemainingFreeSms(){
        return remainingFreeSms;
    }
}

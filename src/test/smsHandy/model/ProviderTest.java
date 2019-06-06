package smsHandy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smsHandy.exception.NotEnoughBalanceException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProviderTest {
    private Provider provider;
    private PrepaidSmsHandy phone1;
    private PrepaidSmsHandy phone2;

    @BeforeEach
    void beforeEach() {
        provider = new Provider("provider");
        phone1 = new PrepaidSmsHandy("777", provider);
        phone2 = new PrepaidSmsHandy("999", provider);
    }

    @Test
    void send() throws NotEnoughBalanceException {
        Message message = new Message("Testing...", "777", "999", new Date());
        phone1.sendSms(message.getTo().toString(), message.getContent().toString());
        for (Message m : phone2.getReceived()) {
            assertEquals(m.getContent(), message.getContent());
        }
        phone1.sendSms("*101#", "");
        for (Message m : phone1.getReceived()) {
            assertTrue(m.getFrom().equals("provider"));
        }
    }

    @Test
    void testDepositForGettingExpectedBalance() {
        int settingBalance = 100;
        int actualBalance = provider.getCreditForSmsHandy(phone1.getNumber());
        int expectedBalance = actualBalance + settingBalance;

        provider.deposit(phone1.getNumber(), 100);
        actualBalance = provider.getCreditForSmsHandy(phone1.getNumber());

        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void testDepositForExceptionToMinusAmountAndEmptyNumber(){
        Throwable exceptionToMinusAmountAndEmptyNumber = assertThrows(IllegalArgumentException.class, () -> provider.deposit("", -1));
        assertEquals("Wrong amount or number for deposit!", exceptionToMinusAmountAndEmptyNumber.getMessage());
    }

    @Test
    void testDepositForExceptionToMinusAmount(){
        Throwable exceptionToMinusAmount = assertThrows(IllegalArgumentException.class, () -> provider.deposit("051512", -1));
        assertEquals("Wrong amount or number for deposit!", exceptionToMinusAmount.getMessage());
    }

    @Test
    void testDepositForExceptionToZeroAmountAndEmptyNumber(){
        Throwable exceptionToZeroAmountAndEmptyNumber = assertThrows(IllegalArgumentException.class, () -> provider.deposit("", 0));
        assertEquals("Wrong amount or number for deposit!", exceptionToZeroAmountAndEmptyNumber.getMessage());
    }

    @Test
    void testDepositForExceptionToZeroAmount(){
        Throwable exceptionToZeroAmount = assertThrows(IllegalArgumentException.class, () -> provider.deposit("051512", 0));
        assertEquals("Wrong amount or number for deposit!", exceptionToZeroAmount.getMessage());
    }

    @Test
    void testDepositForExceptionToEmptyNumber(){
        Throwable exceptionToEmptyNumber = assertThrows(IllegalArgumentException.class, () -> provider.deposit("", 100));
        assertEquals("Wrong amount or number for deposit!", exceptionToEmptyNumber.getMessage());
    }

    @Test
    void testGetCreditForSmsHandyForExceptionToEmptyNumber(){
        Throwable exceptionToEmptyNumber = assertThrows(IllegalArgumentException.class, () -> provider.getCreditForSmsHandy(""));
        assertEquals("Number cannot be empty!", exceptionToEmptyNumber.getMessage());
    }

    @Test
    void testGetCreditForSmsHandyToExpectedBalance(){
        int settingBalance = 100;
        int actualBalance = provider.getCreditForSmsHandy(phone1.getNumber());
        int expectedBalance = actualBalance + settingBalance;
        provider.deposit(phone1.getNumber(), settingBalance);
        actualBalance = provider.getCreditForSmsHandy(phone1.getNumber());

        assertEquals(expectedBalance, actualBalance);
    }
}

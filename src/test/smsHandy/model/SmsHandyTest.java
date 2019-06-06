package smsHandy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smsHandy.exception.NotEnoughBalanceException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SmsHandyTest {
    private Provider provider;
    private TariffPlanSmsHandy phone1;
    private TariffPlanSmsHandy phone2;

    @BeforeEach
    void setUp() {
        provider = new Provider("provider");
        phone1 = new TariffPlanSmsHandy("777", provider);
        phone2 = new TariffPlanSmsHandy("999", provider);
    }

    @Test
    void sendSms() throws NotEnoughBalanceException {
        Message message = new Message("Testing... sendSms()", "777", "999", new Date());
        phone1.sendSms(message.getTo().toString(), message.getContent().toString());
        int expectedBalanceOfPhone1 = phone1.getRemainingFreeSms();
        int actual = 99;
        assertEquals(expectedBalanceOfPhone1, actual);
        assertNotNull(phone1.getSent());
        assertTrue(phone1.getSent().contains(message));
        assertNotNull(phone2.getReceived());
    }

    @Test
    void sendSmsDirect() {
        Message message = new Message("Testing... sendSmsDirect()", "777", "999", new Date());
        phone1.sendSmsDirect(phone2, message.getContent().toString());
        int expectedBalanceOfPhone1 = phone1.getRemainingFreeSms();
        int actual = 100;
        assertEquals(expectedBalanceOfPhone1, actual);
        assertNotNull(phone1.getSent());
        assertTrue(phone1.getSent().contains(message));
        assertNotNull(phone2.getReceived());
    }

    @Test
    void receiveSms() {
        Message message = new Message("Testing... receiveSms()", "777", "999", new Date());
        phone2.receiveSms(message);
        assertNotNull(phone2.getReceived());
        assertTrue(phone2.getReceived().contains(message));
    }
}
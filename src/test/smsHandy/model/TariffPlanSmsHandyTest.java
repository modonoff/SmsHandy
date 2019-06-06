package smsHandy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smsHandy.exception.NotEnoughBalanceException;

import static org.junit.jupiter.api.Assertions.*;

class TariffPlanSmsHandyTest {
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
    void canSendSms() {
        int expected = phone1.getRemainingFreeSms();
        int actual = 100;
        assertEquals(expected, actual);
        assertTrue(expected > 0);
        assertNotEquals(expected, -1);
        assertNotEquals(expected, 0);
    }

    @Test
    void payForSms() throws NotEnoughBalanceException {
        phone1.sendSms("999", "Testing...");
        int expectedPhone1 = phone1.getRemainingFreeSms();
        int actual = 99;
        assertEquals(expectedPhone1, actual);
        int expectedPhone2 = phone2.getRemainingFreeSms();
        assertEquals(expectedPhone2, 100);
    }

    @Test
    void payForSmsWhenThrowsNotEnoughBalanceException() {
        Throwable exceptionToZeroFreeSms = assertThrows(NotEnoughBalanceException.class, () ->  {
            for (int i = 0; i <= 100; i++){
                phone1.payForSms();
            }
        });
        assertEquals("There is no free sms", exceptionToZeroFreeSms.getMessage());
    }
}
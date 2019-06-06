package smsHandy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smsHandy.exception.NotEnoughBalanceException;

import static org.junit.jupiter.api.Assertions.*;

public class PrepaidSmsHandyTest {
    private PrepaidSmsHandy prepaidSmsHandy;
    private PrepaidSmsHandy phone2;
    private Provider provider;

    @BeforeEach
    void beforeEach(){
        provider = new Provider("provider");
        prepaidSmsHandy = new PrepaidSmsHandy("777", provider);
        phone2 = new PrepaidSmsHandy("999", provider);
    }

    @Test
    void testPayForSmsForExceptionToZeroBalance(){
        Throwable exceptionToZeroBalance= assertThrows(NotEnoughBalanceException.class, () ->  {
            for (int i = 0; i <= 10; i++){
                prepaidSmsHandy.payForSms();
            }
        });
        assertEquals("Balance is already 0", exceptionToZeroBalance.getMessage());
    }

    @Test
    void testPayForSmsForExpectedBalance() throws NotEnoughBalanceException {
        int actualBalance = prepaidSmsHandy.getProvider().getCreditForSmsHandy(prepaidSmsHandy.getNumber());
        int expectedBalance = actualBalance - 10;

        prepaidSmsHandy.payForSms();

        actualBalance = prepaidSmsHandy.getProvider().getCreditForSmsHandy(prepaidSmsHandy.getNumber());
        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void testSetBalance(){
        Throwable exceptionToMinusBalance= assertThrows(IllegalArgumentException.class, () ->  prepaidSmsHandy.setBalance(-1));
        assertEquals("Not acceptable value for balance!", exceptionToMinusBalance.getMessage());
    }

    @Test
    void testDepositExceptionToMinusAmount(){
        Throwable exceptionToMinusAmount= assertThrows(IllegalArgumentException.class, () ->  prepaidSmsHandy.deposit(-1));
        assertEquals("Not acceptable value for deposit!", exceptionToMinusAmount.getMessage());
    }

    @Test
    void testDepositExceptionToZeroAmount(){
        Throwable exceptionToZeroAmount = assertThrows(IllegalArgumentException.class, () ->  prepaidSmsHandy.deposit(0));
        assertEquals("Not acceptable value for deposit!", exceptionToZeroAmount .getMessage());
    }

    @Test
    void testDepositForExpectedBalance(){
        int settingBalance = 100;
        int actualBalance = prepaidSmsHandy.getProvider().getCreditForSmsHandy(prepaidSmsHandy.getNumber());
        int expectedBalance = actualBalance+settingBalance;

        prepaidSmsHandy.deposit(settingBalance);

        actualBalance = prepaidSmsHandy.getProvider().getCreditForSmsHandy(prepaidSmsHandy.getNumber());

        assertEquals(expectedBalance, actualBalance);
    }
}

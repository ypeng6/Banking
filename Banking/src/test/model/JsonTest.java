package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAccount(String name, double chequingBalance, double savingBalance, Account acc){
        assertEquals(name, acc.getName());
        assertEquals(chequingBalance,acc.getChequingBalance());
        assertEquals(savingBalance,acc.getSavingBalance());
    }
}

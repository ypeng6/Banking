package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account testRock;
    private Account testJack;
    private Account testMichael;
    private Accounts list1;

    @BeforeEach
    public void setup() {
        testRock = new Account("rock", 0, 0);
        testJack = new Account( "jack", 100.10, 2000.10);
        testMichael = new Account( "michael", 1000.10, 3000.10);
        list1 = new Accounts();
        list1.addCustomer(testJack);
        list1.addCustomer(testMichael);
    }

    @Test
    public void testDepositToChequing() {
        testJack.depositToChequing(100);
        assertEquals(2100.10, testJack.getChequingBalance(), 0.0001);
    }

    @Test
    public void testDepositToSaving() {
        testJack.depositToSaving(100);
        assertEquals(200.10, testJack.getSavingBalance(), 0.0001);
    }

    @Test
    public void testWithdrawFromChequing() {
        assertFalse(testJack.withdrawFromChequing(3000));
        assertTrue(testJack.withdrawFromChequing(2000));
        assertEquals(0.10, testJack.getChequingBalance(), 0.0001);
    }

    @Test
    public void testWithdrawFromSaving() {
        assertFalse(testJack.withdrawFromSaving(3000));
        assertTrue(testJack.withdrawFromSaving(100));
        assertEquals(0.10, testJack.getSavingBalance(), 0.0001);
    }

    @Test
    public void testTransferFromChequingToSaving() {
        assertFalse(testMichael.transferFromChquingToSaving(6000));
        assertTrue(testMichael.transferFromChquingToSaving(1000));
        assertEquals(2000.10, testMichael.getChequingBalance(), 0.0001);
        assertEquals(2000.10, testMichael.getSavingBalance(), 0.0001);
    }

    @Test
    public void testTransferFromSavingToChequing() {
        assertFalse(testJack.transferFromSavingToChequing(3000));
        assertTrue(testJack.transferFromSavingToChequing(100));
        assertEquals(2100.10, testJack.getChequingBalance(), 0.0001);
        assertEquals(0.10, testJack.getSavingBalance(), 0.0001);
    }

    @Test
    public void testTransferFromThisChequingToOthersChequing() {
        assertFalse(testMichael.transferFromThisChequingToOthersChequing(testJack, 3300));
        assertTrue(testMichael.transferFromThisChequingToOthersChequing(testRock, 3000));
        assertEquals(3000, testRock.getChequingBalance(), 0.0001);
        assertEquals(0.10, testMichael.getChequingBalance(), 0.0001);
    }

    @Test
    public void testTransferFromThisChequingToOthersSaving() {
        assertFalse(testMichael.transferFromThisChequingToOthersSaving(testJack, 3300));
        assertTrue(testMichael.transferFromThisChequingToOthersSaving(testRock, 3000));
        assertEquals(3000, testRock.getSavingBalance(), 0.0001);
        assertEquals(0.10, testMichael.getChequingBalance(), 0.0001);
    }

    @Test
    public void testTransferFromThisSavingToOthersChequing() {
        assertFalse(testMichael.transferFromThisSavingToOthersChequing(testJack, 3000));
        assertTrue(testMichael.transferFromThisSavingToOthersChequing(testRock, 1000));
        assertEquals(0.10, testMichael.getSavingBalance(), 0.0001);
        assertEquals(1000, testRock.getChequingBalance(), 0.0001);
    }

    @Test
    public void testTransferFromThisSavingToOthersSaving() {
        assertFalse(testMichael.transferFromThisSavingToOthersSaving(testJack, 3000));
        assertTrue(testMichael.transferFromThisSavingToOthersSaving(testRock, 1000));
        assertEquals(0.10, testMichael.getSavingBalance(), 0.0001);
        assertEquals(1000, testRock.getSavingBalance(), 0.0001);
    }

    @Test
    public void testToString() {
        assertEquals("[name = jack, chequing balance = $2000.10, saving balance = $100.10]"
                , testJack.toString());
    }

    @Test
    public void testAddCustomer() {
        assertEquals(2, list1.getSize());
        list1.addCustomer(testRock);
        assertEquals(3, list1.getSize());
    }

    @Test
    public void testRemoveCustomer() {
        assertEquals(2, list1.getSize());
        list1.removeCustomer(testJack);
        assertEquals(1, list1.getSize());
    }

    @Test
    public void testDisplayCustomers() {
        assertEquals("[[name = michael, chequing balance = $3000.10, saving balance = $1000.10]\n" +
                ", [name = jack, chequing balance = $2000.10, saving balance = $100.10]\n" +
                "]", list1.displayCustomers());
    }

    @Test
    public void testFindCustomer(){
        assertEquals(testJack, list1.findCustomer("jack"));
        assertNull(list1.findCustomer("rock"));
    }

    @Test
    public void testFindAndRemoveCustomer(){
        list1.findAndRemove("jack");
        assertEquals(1, list1.getSize());
        assertNull( list1.findCustomer("jack"));
    }

    @Test
    public void testEMT(){
        testJack.emt("c","s",100,testRock);
        assertEquals(1900.10,testJack.getChequingBalance(),0.0001);
        assertEquals(100,testRock.getSavingBalance(),0.0001);
        testJack.emt("c","c",100,testRock);
        assertEquals(1800.1,testJack.getChequingBalance(),0.0001);
        assertEquals(100,testRock.getChequingBalance(),0.0001);
        testJack.emt("s","c",10,testRock);
        assertEquals(90.10,testJack.getSavingBalance(),0.0001);
        assertEquals(110,testRock.getChequingBalance(),0.0001);
        testJack.emt("s","s",10,testRock);
        assertEquals(80.10,testJack.getSavingBalance(),0.0001);
        assertEquals(110,testRock.getSavingBalance(),0.0001);
    }

    @Test
    public void testDeposit(){
        testJack.deposit("c",100);
        assertEquals(2100.10, testJack.getChequingBalance(),0.0001);
        testJack.deposit("s",100);
        assertEquals(200.10, testJack.getSavingBalance(),0.0001);
    }

    @Test
    public void testWithdraw(){
        testJack.withdraw("c",100);
        assertEquals(1900.10, testJack.getChequingBalance(),0.0001);
        testJack.withdraw("s",100);
        assertEquals(0.10, testJack.getSavingBalance(),0.0001);
    }

    @Test
    public void testTransfer(){
        testJack.transfer("c",100);
        assertEquals(1900.10, testJack.getChequingBalance(),0.0001);
        assertEquals(200.10, testJack.getSavingBalance(),0.0001);
        testJack.transfer("s",100);
        assertEquals(2000.10, testJack.getChequingBalance(),0.0001);
        assertEquals(100.10, testJack.getSavingBalance(),0.0001);
    }
}

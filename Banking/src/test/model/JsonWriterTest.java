package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriteNoSuchFile() {
        try {
            Accounts accounts = new Accounts();
            JsonWriter writer = new JsonWriter("./data/NoSuchFile?.json");
            writer.open();
            fail("Exception was not caught");
        } catch (IOException e) {
            // Exception was caught
        }
    }

    @Test
    void testWriteEmptyAccounts() {
        try {
            Accounts accounts = new Accounts();
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyAccounts.json");
            writer.open();
            writer.write(accounts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyAccounts.json");
            Accounts accts = reader.read();
            assertEquals(0, accts.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteGeneralAccounts() {
        try {
            Accounts accounts = new Accounts();
            accounts.addCustomer(new Account("rock", 100,100));
            accounts.addCustomer(new Account("jack", 100,100));
            JsonWriter writer = new JsonWriter("./data/testWriteGeneralAccounts.json");
            writer.open();
            writer.write(accounts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteGeneralAccounts.json");
            Accounts accts1 = reader.read();
            List<Account> accts2 = accts1.getAccounts();
            assertEquals(2, accts2.size());
            checkAccount("rock", 100,100, accts2.get(0));
            checkAccount("jack", 100,100, accts2.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
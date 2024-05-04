package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReadNoSuchFileAccounts(){
        try {
            JsonReader reader = new JsonReader("./data/NoSuchFile.json");
            Accounts accounts = reader.read();
            fail("could not catch the exception");
        } catch (IOException e) {
            //Exception was caught
        }
    }
    @Test
    void testReadEmptyAccounts(){
        JsonReader reader = new JsonReader("./data/testReadEmptyAccounts.json");
        try {
            Accounts accounts = reader.read();
            assertEquals(0,accounts.getSize());
        } catch (IOException e) {
            fail("could not read the file");
        }
    }

    @Test
    void testReadGeneralAccounts(){
        JsonReader reader = new JsonReader("./data/testReadGeneralAccounts.json");
        try {
            Accounts accounts = reader.read();
            assertEquals(2,accounts.getSize());
            List<Account> accts = accounts.getAccounts();
            checkAccount("rock",100,100,accts.get(0));
            checkAccount("jack",100,200,accts.get(1));
        } catch (IOException e) {
            fail("could not read the file");
        }
    }
}

package persistence;

import model.Account;
import model.Accounts;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Cite from JsonSerializationDemo.java
// Citation Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads account list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Accounts read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccounts(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses the customer list from JSON object and returns it
    private Accounts parseAccounts(JSONObject jsonObject) {
        Accounts accts = new Accounts();
        addCustomers(accts, jsonObject);
        return accts;
    }

    // MODIFIES: accts
    // EFFECTS: parses accounts from JSON object and adds them to Accounts
    private void addCustomers(Accounts accts, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("customers");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addCustomer(accts, nextAccount);
        }
    }

    // MODIFIES: accts
    // EFFECTS: parses thingy from JSON object and adds it to Accounts
    private void addCustomer(Accounts accts, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double chequingBalance = jsonObject.getDouble("chequingBalance");
        double savingBalance = jsonObject.getDouble("savingBalance");
        Account account = new Account(name, chequingBalance, savingBalance);
        accts.addCustomer(account);
    }
}
package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a list of customers
public class Accounts implements Writable {
    private ArrayList<Account> accounts;

    // constructs an empty list of accounts
    public Accounts() {
        accounts = new ArrayList<>();
    }

    //Requires: Cannot add an existing customer twice (no duplicated customers)
    //Modifies: this
    //Effects: add a customer into file
    public void addCustomer(Account acc) {
        accounts.add(acc);
        Event e = new Event("Customer " + acc + " was added into file");
        EventLog.getInstance().logEvent(e);
    }

    //Modifies: this
    //Effects: remove a customer from file
    public void removeCustomer(Account acc) {
        accounts.remove(acc);
        Event e = new Event("Customer " + acc + " was removed from file");
        EventLog.getInstance().logEvent(e);
    }

    //Effects: get the number of customers in file
    public int getSize() {
        return accounts.size();
    }

    //Requires: there must be at least one customer in file
    //Effects: display a list of names of all customers in file
    public String displayCustomers() {
        HashSet<String> customers = new HashSet<>();
        for (Account next : accounts) {
            customers.add(next.toString() + "\n");
        }
        return customers.toString();
    }

    // EFFECTS: returns an unmodifiable list of thingies in this workroom
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    //Modifies: this
    //Effects: find the given customer and cancel the account
    public void findAndRemove(String str) {
        removeCustomer(findCustomer(str));
    }

    //Effects: find and return the given customer; if the given customer cannot be found, return null
    public Account findCustomer(String str) {
        for (Account next : accounts) {
            if (Objects.equals(str, next.getName())) {
                return next;
            }
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("customers", accountsToJson());
        return json;
    }

    // EFFECTS: returns accounts in this list as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account acc : accounts) {
            jsonArray.put(acc.toJson());
        }

        return jsonArray;
    }
}

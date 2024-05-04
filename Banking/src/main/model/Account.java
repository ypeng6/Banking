package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a customer having a name, a chequing and saving accounts.
public class Account implements Writable {
    private final String name;
    private double savingBalance;
    private double chequingBalance;

    // EFFECTS: name on account is set to customerName;
    // if initialBalance >= 0 then balance on account is set to
    // initialBalance, otherwise balance is zero.
    public Account(String customerName, double saving, double chequing) {
        this.name = customerName;
        this.savingBalance = saving;
        this.chequingBalance = chequing;
    }

    //Requires: amount to be deposited must be greater than 0
    //Modifies: this
    //Effect: deposit a given amount of money into chequing
    public void depositToChequing(double amount) {
        this.chequingBalance = this.chequingBalance + amount;
        Event e = new Event("Customer " + getName() + " deposited "
                + String.valueOf(amount) + " into chequing");
        EventLog.getInstance().logEvent(e);
    }

    //Requires: amount to be deposited must be greater than 0
    //Modifies: this
    //Effect: deposit a given amount of money into saving
    public void depositToSaving(double amount) {
        this.savingBalance = this.savingBalance + amount;
        Event e = new Event("Customer " + getName() + " deposited "
                + String.valueOf(amount) + " into saving");
        EventLog.getInstance().logEvent(e);
    }

    //Effect: get current balance of chequing account
    public double getChequingBalance() {
        return chequingBalance;
    }

    //Effect: deposit given amount of money into given account type
    public void deposit(String strD, double amt) {
        if (Objects.equals(strD, "c")) {
            this.depositToChequing(amt);
        }
        if (Objects.equals(strD, "s")) {
            this.depositToSaving(amt);
        }
    }

    //Effect: get current balance of saving account
    public double getSavingBalance() {
        return savingBalance;
    }

    //Requires: amount to be withdrawn must be greater than 0
    //Modifies: this
    //Effect: if there is enough money in chequing to be withdrawn, withdraw the given amount of money from chequing
    // and return true; otherwise, return false
    public boolean withdrawFromChequing(double amount) {
        if (this.chequingBalance - amount >= 0) {
            this.chequingBalance = this.chequingBalance - amount;
            Event e = new Event("Customer " + getName() + " withdrawn " + String.valueOf(amount)
                    + " from chequing");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    //Requires: amount to be withdrawn must be greater than 0
    //Modifies: this
    //Effect:if there is enough money in saving to be withdrawn, withdraw the given amount of money from saving,
    // and return true; otherwise, return false
    public boolean withdrawFromSaving(double amount) {
        if (this.savingBalance - amount >= 0) {
            this.savingBalance = this.savingBalance - amount;
            Event e = new Event("Customer " + getName() + " withdrawn " + String.valueOf(amount)
                    + " from saving");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    //Effect: withdraw given amount of money into given account type
    public void withdraw(String strD, double amt) {
        if (Objects.equals(strD, "c")) {
            this.withdrawFromChequing(amt);
        }
        if (Objects.equals(strD, "s")) {
            this.withdrawFromSaving(amt);
        }
    }

    //Requires: amount to be transferred must be greater than 0
    //Modifies: this
    //Effect: if there is enough money in chequing to be transferred, transfer the given amount of money to saving,
    // and return true; otherwise, return false
    public boolean transferFromChquingToSaving(double amount) {
        if (this.chequingBalance - amount >= 0) {
            this.chequingBalance = this.chequingBalance - amount;
            this.savingBalance = this.savingBalance + amount;
            Event e = new Event("Customer " + getName() + " transferred " + String.valueOf(amount)
                    + " from chequing to saving");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    //Requires: amount to be transferred must be greater than 0
    //Modifies: this
    //Effect:if there is enough money in saving to be transferred, transfer the given amount of money to chequing,
    // and return true; otherwise, return false
    public boolean transferFromSavingToChequing(double amount) {
        if (this.savingBalance - amount >= 0) {
            this.savingBalance = this.savingBalance - amount;
            this.chequingBalance = this.chequingBalance + amount;
            Event e = new Event("Customer " + getName() + " transferred " + String.valueOf(amount)
                    + " from saving to chequing");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    //Requires: amount to be transferred must be greater than 0
    //Modifies: this, acc
    //Effect:if there is enough money in saving to be transferred, transfer the given amount of money
    // to target customer's chequing, and return true; otherwise, return false
    public boolean transferFromThisSavingToOthersChequing(Account acc, double amount) {
        if (this.savingBalance - amount >= 0) {
            this.savingBalance = this.savingBalance - amount;
            acc.chequingBalance = acc.chequingBalance + amount;
            Event e = new Event("Customer " + getName() + " transferred " + String.valueOf(amount)
                    + " from saving to " + acc.getName() + "'s chequing");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    //Requires: amount to be transferred must be greater than 0
    //Modifies: this, acc
    //Effect:if there is enough money in saving to be transferred, transfer the given amount of money
    // to target customer's saving, and return true; otherwise, return false
    public boolean transferFromThisSavingToOthersSaving(Account acc, double amount) {
        if (this.savingBalance - amount >= 0) {
            this.savingBalance = this.savingBalance - amount;
            acc.savingBalance = acc.savingBalance + amount;
            Event e = new Event("Customer " + getName() + " transferred " + String.valueOf(amount)
                    + " from saving to " + acc.getName() + "'s saving");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    //Requires: amount to be transferred must be greater than 0
    //Modifies: this, acc
    //Effect: if there is enough money in chequing to be transferred, transfer the given amount of money
    // to target customer's chequing, and return true; otherwise, return false
    public boolean transferFromThisChequingToOthersChequing(Account acc, double amount) {
        if (this.chequingBalance - amount >= 0) {
            this.chequingBalance = this.chequingBalance - amount;
            acc.chequingBalance = acc.chequingBalance + amount;
            Event e = new Event("Customer " + getName() + " transferred " + String.valueOf(amount)
                    + " from chequing to " + acc.getName() + "'s chequing");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    //Requires: amount to be transferred must be greater than 0
    //Modifies: this, acc
    //Effect:if there is enough money in saving to be transferred, transfer the given amount of money
    // to target customer's saving, and return true; otherwise, return false
    public boolean transferFromThisChequingToOthersSaving(Account acc, double amount) {
        if (this.chequingBalance - amount >= 0) {
            this.chequingBalance = this.chequingBalance - amount;
            acc.savingBalance = acc.savingBalance + amount;
            Event e = new Event("Customer " + getName() + " transferred " + String.valueOf(amount)
                    + " from cheqing to " + acc.getName() + "'s saving");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    //Effect: transfer given amount of money within given customer's accounts
    public void transfer(String strD, double amt) {
        if (Objects.equals(strD, "c")) {
            this.transferFromChquingToSaving(amt);
        }
        if (Objects.equals(strD, "s")) {
            this.transferFromSavingToChequing(amt);
        }
    }

    //Effect: transfer money between customers
    public void emt(String strS, String strD, double amt, Account receiver) {
        if (Objects.equals(strS, "c") && Objects.equals(strD, "c")) {
            this.transferFromThisChequingToOthersChequing(receiver, amt);
        }
        if (Objects.equals(strS, "c") && Objects.equals(strD, "s")) {
            this.transferFromThisChequingToOthersSaving(receiver, amt);
        }
        if (Objects.equals(strS, "s") && Objects.equals(strD, "c")) {
            this.transferFromThisSavingToOthersChequing(receiver, amt);
        }
        if (Objects.equals(strS, "s") && Objects.equals(strD, "s")) {
            transferFromThisSavingToOthersSaving(receiver, amt);
        }
    }

    //Effects: get the name of the customer
    public String getName() {
        return this.name;
    }

    //Effects: return a string representation of an account
    public String toString() {
        String chequingStr = String.format("%.2f", chequingBalance);
        String savingStr = String.format("%.2f", savingBalance);
        return "[name = " + name + ", "
                + "chequing balance = $" + chequingStr + ", saving balance = $" + savingStr + "]";
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("savingBalance", savingBalance);
        json.put("chequingBalance", chequingBalance);
        return json;
    }


}


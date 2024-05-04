package ui;

import model.Account;
import model.Accounts;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Banking application
public class Banking {
    private static final String JSON_STORE = "./data/accounts.json";
    private static final String CUSTOMERS_COMMAND = "customers";
    private static final String REGISTER_CUSTOMER_COMMAND = "sign up";
    private static final String REMOVE_CUSTOMER_COMMAND = "remove";
    private static final String DEPOSIT_COMMAND = "deposit";
    private static final String WITHDRAW_COMMAND = "withdraw";
    private static final String TRANSFER_COMMAND = "transfer";
    private static final String A_TRANSFER_TO_B_COMMAND = "emt";
    private static final String SAVE_COMMAND = "save";
    private static final String LOAD_COMMAND = "load";
    private static final String QUIT_COMMAND = "quit";
    private final Scanner input;
    private boolean runProgram;
    private Accounts accounts;
    private Account account;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

// Construct a banking object
    public Banking(Accounts accounts) throws FileNotFoundException {
        input = new Scanner(System.in);
        runProgram = true;
        this.accounts = accounts;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        handleUserInput();
    }

    //EFFECTS: parses user input until user quits
    public void handleUserInput() {
        printInstructions();
        String str;

        while (runProgram) {
            str = getUserInputString();
            parseInput(str);
        }
    }

    //EFFECTS: prints menu options and info depending on input str
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void parseInput(String str) {
        if (str.length() > 0) {
            switch (str) {
                case CUSTOMERS_COMMAND:
                    System.out.println(accounts.displayCustomers());
                    System.out.println("Enter available commands to continue");
                    break;
                case REGISTER_CUSTOMER_COMMAND:
                    registerCustomer();
                    break;
                case REMOVE_CUSTOMER_COMMAND:
                    removeCustomer();
                    break;
                case DEPOSIT_COMMAND:
                    doDeposit();
                    break;
                case WITHDRAW_COMMAND:
                    doWithdraw();
                    break;
                case TRANSFER_COMMAND:
                    doTransfer();
                    break;
                case A_TRANSFER_TO_B_COMMAND:
                    doATransferToB();
                    break;
                case SAVE_COMMAND:
                    saveAccounts();
                    break;
                case LOAD_COMMAND:
                    loadAccounts();
                    break;
                case QUIT_COMMAND:
                    runProgram = false;
                    endProgram();
                    printLogEvent();
                    break;
                default:
                    System.out.println("Sorry, I didn't understand that command. Please try again.");
                    printInstructions();
                    break;
            }
        }
    }

    public static void printLogEvent() {
        EventLog eventLog = EventLog.getInstance();
        for (Event next : eventLog) {
            System.out.println(next.toString() + "\n\n");
        }
    }

    //EFFECTS: prints instructions to start banking
    private void printInstructions() {
        System.out.println("Welcome to banking center:");
        System.out.println("Enter '" + CUSTOMERS_COMMAND + "' for a list of all customers.");
        System.out.println("Enter '" + REGISTER_CUSTOMER_COMMAND + "' to register a new customer ");
        System.out.println("Enter '" + REMOVE_CUSTOMER_COMMAND + "' cancel a current customer's account ");
        System.out.println("Enter '" + DEPOSIT_COMMAND + "' to deposit money ");
        System.out.println("Enter '" + WITHDRAW_COMMAND + "' to withdraw money ");
        System.out.println("Enter '" + TRANSFER_COMMAND + "' to transfer money ");
        System.out.println("Enter '" + A_TRANSFER_TO_B_COMMAND + "' to transfer money between customers");
        System.out.println("Enter '" + SAVE_COMMAND + "' to save current customer file");
        System.out.println("Enter '" + LOAD_COMMAND + "' to reload previous customer file");
        System.out.println("To quit at any time, enter '" + QUIT_COMMAND + "'.");
    }

    //Effects: Deposit money into either chequing or saving account
    private void doDeposit() {
        System.out.println("Which customer is going to perform transactions?");
        String str = input.nextLine();
        Account target = accounts.findCustomer(str);
        System.out.println("Enter 'c' for chequing or 's' for saving");
        String strD = input.nextLine();
        System.out.println("Enter amount of money to be deposited");
        double amt = input.nextDouble();
        target.deposit(strD, amt);
        System.out.println("Money has been deposited");
        System.out.println(target);
        printInstructions();
    }

    //Effects: Withdraw Money from either chequing or saving account
    private void doWithdraw() {
        System.out.println("Which customer is going to perform transactions?");
        String str = input.nextLine();
        Account target = accounts.findCustomer(str);
        System.out.println("Enter 'c' for chequing or 's' for saving");
        String strD = input.nextLine();
        System.out.println("Enter amount of money to be withdrawn");
        double amt = input.nextDouble();
        target.withdraw(strD, amt);
        System.out.println("Money has been deposited");
        System.out.println(target);
        printInstructions();
    }

    //Effects: Transfer money within this customer's account
    private void doTransfer() {
        System.out.println("Which customer is going to perform transactions?");
        String str = input.nextLine();
        Account target = accounts.findCustomer(str);
        System.out.println("Enter 'c' for from chequing to saving or 's' for the other way");
        String strD = input.nextLine();
        System.out.println("Enter amount of money to be transferred");
        double amt = input.nextDouble();
        target.transfer(strD, amt);
        System.out.println("Money has been deposited");
        System.out.println(target);
        printInstructions();
    }

    //Effects: Transfer money from this customer to another customer
    private void doATransferToB() {
        System.out.println("Which customer is going to perform transactions?");
        String str = input.nextLine();
        Account sender = accounts.findCustomer(str);
        System.out.println("Enter 'c' for from chequing or 's' for from saving");
        String strS = input.nextLine();
        System.out.println("Enter amount of money to be transferred");
        double amt = input.nextDouble();
        System.out.println("Which customer is receiving the money?");
        String str2 = input.nextLine();
        str2 = input.nextLine();
        Account receiver = accounts.findCustomer(str2);
        System.out.println("Enter 'c' for chequing or 's' for saving");
        String strD = input.nextLine();
        sender.emt(strS, strD, amt, receiver);
        System.out.println("Money has been deposited");
        System.out.println(sender);
        System.out.println(receiver);
        printInstructions();
    }


    //Effects: register a new customer
    private void registerCustomer() {
        System.out.println("Please enter the new customer's name:");
        String name = input.nextLine();
        account = new Account(name, 0, 0);
        System.out.println("Please enter the amount of money to be deposited into chequqing account");
        double amtC = input.nextDouble();
        account.depositToChequing(amtC);
        System.out.println("Please enter the amount of money to be deposited into saving account");
        double amtS = input.nextDouble();
        account.depositToSaving(amtS);
        System.out.println(account.toString());
        accounts.addCustomer(account);
        printInstructions();
    }

    //Effects: remove an existing customer
    private void removeCustomer() {
        System.out.println("Please enter the existing customer's name:");
        String name = input.nextLine();
        accounts.findAndRemove(name);
        System.out.println("The account has been canceled.");
        printInstructions();
    }

    // EFFECTS: saves the accounts to file
    private void saveAccounts() {
        try {
            jsonWriter.open();
            jsonWriter.write(accounts);
            jsonWriter.close();
            System.out.println("Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads accounts from file
    private void loadAccounts() {
        try {
            accounts = jsonReader.read();
            System.out.println("Loaded " + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private String getUserInputString() {
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
            str = makePrettyString(str);
        }
        return str;
    }

    //EFFECTS: removes white space and quotation marks around s
    private String makePrettyString(String s) {
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\"|'", "");
        return s;
    }

    //EFFECTS: stops receiving user input
    public void endProgram() {
        System.out.println("Quitting...");
        input.close();
    }

}

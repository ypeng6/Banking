package ui;

import model.Accounts;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        Accounts accounts = new Accounts();
        System.out.println("Banking application now starts to run");
        try {
            Banking banking = new Banking(accounts);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}

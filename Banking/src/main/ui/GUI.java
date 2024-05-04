/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ui;

/*
 * ToolBarDemo2.java requires the following additional files:
 * images/SignUp.gif
 */

import model.Account;
import model.Accounts;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;


// Cite from ToolBarDemo2Project.java
// Citation Source: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

// GUI application of Banking
public class GUI extends JPanel
        implements ActionListener {
    protected JTextArea textArea;
    protected String newline = "\n";
    private Accounts accounts;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/accounts.json";
    static final String SIGN_UP = "sign up";
    static final String REMOVE = "remove";
    static final String LOAD = "load";
    static final String SAVE = "save";
    static final String FILE = "file";
    static final String WITHDRAW = "withdraw";
    static final String DEPOSIT = "deposit";
    static final String TRANSFER = "transfer";
    static final String EMT = "emt";
    static final String TEXT_ENTERED = "text";
    static final String QUIT = "quit";

    // Construct a GUI for banking
    public GUI() {
        super(new BorderLayout());

        //Create the toolbar.
        JToolBar toolBar = new JToolBar("Still draggable");
        addButtons(toolBar);
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        //Create another toolbar.
        JToolBar toolBar2 = new JToolBar("Still draggable");
        addButtons2(toolBar2);
        toolBar2.setFloatable(false);
        toolBar2.setRollover(true);

        //Create the text area used for output.  Request
        //enough space for 5 rows and 30 columns.
        textArea = new JTextArea(5, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Lay out the main panel.
        setPreferredSize(new Dimension(1200, 800));
        add(toolBar, BorderLayout.PAGE_END);
        add(toolBar2, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);

        accounts = new Accounts();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

    }


    // Modifies: this
    // Effects: creating buttons on the GUI
    protected void addButtons(JToolBar toolBar) {
        JButton button = null;

        //SignUp button
        button = makeNavigationButton("SignUp", SIGN_UP,
                "register a new customer",
                "Sign Up");
        toolBar.add(button);

        //remove button
        button = createButton("Remove", REMOVE, "remove an existing account");
        toolBar.add(button);

        //separator
        toolBar.addSeparator();

        //This component is a text field
        JTextField textField = new JTextField("Type in a customer's name to find his/her account");
        textField.setColumns(50);
        textField.addActionListener(this);
        textField.setActionCommand(TEXT_ENTERED);
        toolBar.add(textField);

        //separator
        toolBar.addSeparator();

        //load button
        button = createButton("Load", LOAD, "load previously saved file");
        toolBar.add(button);

        button = createButton("Save", SAVE, "save current file");
        toolBar.add(button);

        //separator
        toolBar.addSeparator();

        //file button
        button = createButton("File", FILE, "display all customers in file");
        toolBar.add(button);

        //file button
        button = createButton("Quit", QUIT, "quit the system and log all events");
        toolBar.add(button);

    }

    // Effects: make functional buttons
    protected void addButtons2(JToolBar toolBar2) {
        JButton button = null;
        //withdraw button
        button = createButton("Withdraw", WITHDRAW, "withdraw money from a account");
        toolBar2.add(button);

        //deposit button
        button = createButton("Deposit", DEPOSIT, "deposit money into a account");
        toolBar2.add(button);

        //transfer button
        button = createButton("Transfer", TRANSFER, "transfer money within a account");
        toolBar2.add(button);

        //emt button
        button = createButton("EMT", EMT, "transfer money between customers");
        toolBar2.add(button);
    }

    // Effects: make a new button
    public JButton createButton(String name, String command, String tipText) {
        JButton button = null;
        button = new JButton(name);
        button.setActionCommand(command);
        button.setPreferredSize(new Dimension(300, 100));
        button.setToolTipText(tipText);
        button.addActionListener(this);
        return button;
    }


    // Effects: make a new navigation button
    protected JButton makeNavigationButton(String imageName,
                                           String actionCommand,
                                           String toolTipText,
                                           String altText) {
        //Look for the image.
        String imgLocation = "images/"
                + imageName
                + ".gif";
        URL imageURL = GUI.class.getResource(imgLocation);

        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null) {                      //image found
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: "
                    + imgLocation);
        }

        return button;
    }

    // Effects: perform actions according to user's order
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String description = null;

        // Handle each button.
        if (SIGN_UP.equals(cmd)) { //Sign_Up button clicked
            description = registerCustomer();
        } else if (REMOVE.equals(cmd)) { // second button clicked
            removeCustomer();
            description = "Account is successfully canceled";
        } else if (LOAD.equals(cmd)) { // third button clicked
            description = loadAccounts();
        } else if (SAVE.equals(cmd)) { // fourth button clicked
            description = saveAccounts();
        } else if (FILE.equals(cmd)) { // fifth button clicked
            description = accounts.displayCustomers();
        } else if (WITHDRAW.equals(cmd)) { // sixth button clicked
            description = withdraw();
        } else if (DEPOSIT.equals(cmd)) { // seventh button clicked
            description = deposit();
        } else if (TRANSFER.equals(cmd)) { // eighth button clicked
            description = transfer();
        } else if (EMT.equals(cmd)) { // ninth button clicked
            description = emt();
        } else if (QUIT.equals(cmd)) { // tenth button clicked
            printLogEvent();
        } else if (TEXT_ENTERED.equals(cmd)) { // text field
            JTextField tf = (JTextField) e.getSource();
            String name = tf.getText();
            tf.setText("");
            description = accounts.findCustomer(name).toString();
        }

        displayResult(description);
    }

    // Effects: display what have been executed
    protected void displayResult(String actionDescription) {
        textArea.append(actionDescription + newline);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */

    // Effects: create and show the GUI
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Banking Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new GUI());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                printLogEvent();
                System.exit(0);
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // main method, run the GUI application
    public static void main(String[] args) {
        //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGUI();
    }

    // Effects: print all logged events
    public static void printLogEvent() {
        EventLog eventLog = EventLog.getInstance();
        for (Event next : eventLog) {
            System.out.println(next.toString() + "\n");
        }
    }

    // Modifies: this
    //Effects: register a new customer
    public String registerCustomer() {
        String name = JOptionPane.showInputDialog("Name");
        Account acc = new Account(name, 0, 0);
        double amtC = Double.parseDouble(JOptionPane.showInputDialog("Chequing Balance"));
        acc.depositToChequing(amtC);
        double amtS = Double.parseDouble(JOptionPane.showInputDialog("Saving Balance"));
        acc.depositToSaving(amtS);
        accounts.addCustomer(acc);
        return acc.toString();
    }

    // Modifies : this
    //Effects: remove an existing customer
    public void removeCustomer() {
        String name = JOptionPane.showInputDialog("Name");
        accounts.findAndRemove(name);
    }

    // EFFECTS: loads accounts from file
    private String loadAccounts() {
        try {
            accounts = jsonReader.read();
            return ("Loaded " + " from " + JSON_STORE);
        } catch (IOException e) {
            return ("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the accounts to file
    private String saveAccounts() {
        try {
            jsonWriter.open();
            jsonWriter.write(accounts);
            jsonWriter.close();
            return ("Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            return ("Unable to write to file: " + JSON_STORE);
        }
    }

    // Modifies: this, acc
    // Effects: withdraw money from an account
    public String withdraw() {
        String name = JOptionPane.showInputDialog("Name");
        Account acc = accounts.findCustomer(name);
        String option = JOptionPane.showInputDialog("c for chequing" + newline + "s for saving");
        if ("c".equals(option)) {
            double amt = Double.parseDouble(JOptionPane.showInputDialog("amount"));
            acc.withdrawFromChequing(amt);
            return acc.toString();
        } else if ("s".equals(option)) {
            double amt = Double.parseDouble(JOptionPane.showInputDialog("amount"));
            acc.withdrawFromSaving(amt);
            return acc.toString();
        } else {
            return "no such account";
        }
    }

    // Modifies: this, acc
    // Effects: deposit money from an account
    public String deposit() {
        String name = JOptionPane.showInputDialog("Name");
        Account acc = accounts.findCustomer(name);
        String option = JOptionPane.showInputDialog("c for chequing" + newline + "s for saving");
        if ("c".equals(option)) {
            double amt = Double.parseDouble(JOptionPane.showInputDialog("amount"));
            acc.depositToChequing(amt);
            return acc.toString();
        } else if ("s".equals(option)) {
            double amt = Double.parseDouble(JOptionPane.showInputDialog("amount"));
            acc.depositToSaving(amt);
            return acc.toString();
        } else {
            return "no such account";
        }
    }

    // Modifies: this, acc
    // Effects: transfer money within an account
    public String transfer() {
        String name = JOptionPane.showInputDialog("Name");
        Account acc = accounts.findCustomer(name);
        String option = JOptionPane.showInputDialog("c for from chequing" + newline + "s for from saving");
        double amt = Double.parseDouble(JOptionPane.showInputDialog("amount"));
        acc.transfer(option, amt);
        return acc.toString();
    }

    // Modifies: this, accS, accR
    // Effects: withdraw money between customers
    public String emt() {
        String nameS = JOptionPane.showInputDialog("Sender Name");
        Account accS = accounts.findCustomer(nameS);
        String optionS = JOptionPane.showInputDialog("c for from chequing" + newline + "s for from saving");
        String nameR = JOptionPane.showInputDialog("Receiver Name");
        Account accR = accounts.findCustomer(nameR);
        String optionR = JOptionPane.showInputDialog("c for from chequing" + newline + "s for from saving");
        double amt = Double.parseDouble(JOptionPane.showInputDialog("amount"));
        if ("c".equals(optionS) && "c".equals(optionR)) {
            accS.transferFromThisChequingToOthersChequing(accR, amt);
            return (accS + newline + accR.toString());
        } else if ("c".equals(optionS) && "s".equals(optionR)) {
            accS.transferFromThisChequingToOthersChequing(accR, amt);
            return (accS + newline + accR.toString());
        } else if ("s".equals(optionS) && "c".equals(optionR)) {
            accS.transferFromThisSavingToOthersChequing(accR, amt);
            return (accS + newline + accR.toString());
        } else if ("s".equals(optionS) && "s".equals(optionR)) {
            accS.transferFromThisSavingToOthersSaving(accR, amt);
            return (accS + newline + accR.toString());
        }
        return "Unrecognized input";
    }

}


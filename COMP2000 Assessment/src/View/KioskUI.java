package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import Controller.*;
import Model.*;
import com.company.runnableMain;

public class KioskUI extends JFrame {
    private JPanel kioskPanel;
    private JPanel loginPanel;
    private JButton adminButton;
    private JButton customerButton;
    private JPanel adminPanel;
    private JTextArea dBTextArea;
    private JButton adminBackButton;
    private JButton newOrderButton;
    private JPanel customerPanel;
    private JList basketContent;
    private JButton scanButton;
    private JButton searchItemButton;
    private JButton checkoutButton;
    private JButton increaseButton;
    private JButton decreaseButton;
    private JButton customerBackButton;
    private JTextPane dBTextPanel;
    private JScrollPane basketScroll;
    private JPanel cardPanel;
    private JTextField cNumberInput;
    private JTextField sNumberInput;
    private JTextField cHolderInput;
    private JButton submitButton;
    private JTextField bAddress1Input;
    private JTextField bAddress2Input;
    private JPanel cashPanel;
    private JTextField cashInput;
    private JButton payCashButton;
    private JLabel totalLabel;
    private JPanel receiptPanel;
    private JButton deleteOrderButton;
    private JButton increaseStockButton;
    private JButton decreaseStockButton;
    private JButton printReceiptButton;
    private JTextArea receiptTextArea;

    runnableMain kioskThread = new runnableMain();
    stockDatabase kioskDB = new stockDatabase();
    automatedCheckoutSystem kioskSystem = new automatedCheckoutSystem();
    int cashPaid;
    ArrayList<String> kioskList = new ArrayList<>();
    ArrayList<String> basketList = new ArrayList<>();
    ArrayList<Integer> totalList = new ArrayList<>();

    //This is a View in the MVC pattern.
    public KioskUI(String title) {
        super(title);
        setContentPane(kioskPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        pack();

        // ADMIN PANEL CODE
        adminButton.addActionListener(new ActionListener() {    //Button to go to admin login and screen.
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInsert = JOptionPane.showInputDialog(kioskPanel, "Please insert admin Username.", null);
                String passwordInsert = JOptionPane.showInputDialog(kioskPanel, "Please insert admin Password", null);

                //int auth = 0;
                //automatedCheckoutSystem.adminLogin(usernameInsert, passwordInsert, auth);

                if (kioskSystem.adminCheck(usernameInsert, passwordInsert)) {   // Checks if inserted values match the saved admin username and password.
                    switchPanel(kioskPanel, "adminCard");
                    automatedCheckoutSystem.dBRead(kioskList);
                    dBTextPanel.setText(String.valueOf(kioskList));

                    //kioskDB.readStock(kioskList);
                    //kioskList.add(automatedCheckoutSystem.dBRead(basketList).toString());
                } else {
                    JOptionPane.showConfirmDialog(kioskPanel, "Incorrect credentials");
                }
            }
        });

        adminBackButton.addActionListener(new ActionListener() {    //Takes you from admin screen back to start screen.
            @Override
            public void actionPerformed(ActionEvent e) {
                backButton("loginCard");
            }
        });

        newOrderButton.addActionListener(new ActionListener() {     //Allows admin to input new stock entry.
            @Override
            public void actionPerformed(ActionEvent e) {
                int success = 1;
                String newSName = JOptionPane.showInputDialog(adminPanel, "Enter new stock name.", null);
                String newSPrice = JOptionPane.showInputDialog(adminPanel, "Enter new stock price, in pence.", null);   //The input is needed in pence since there was no true way of distinguishing a decimal number from letters and numbers at the same time. Only method found caused weird issues.
                String newSQuantity = JOptionPane.showInputDialog(adminPanel, "Enter new stock stored quantity, in numbered format.", null);
                if (newSName.isEmpty() == false) {      //Checks if the name entry is empty.
                    try {
                        Integer.parseInt(newSPrice);
                    } catch (NumberFormatException ee) {
                        success = 0;
                    }
                    if (success == 1) {     //Checks if the price input is actually a number.
                        try {
                            Integer.parseInt(newSQuantity);
                        } catch (NumberFormatException eee) {
                            success = 0;
                        }
                        if (success == 1) {     //Checks if quantity input is actually a number. If successful, writes new order into textfile through kioskSystem and updates admin screen to display new stock.
                            try {
                                kioskSystem.writeDB(newSName.toLowerCase(), newSPrice, newSQuantity);
                                kioskList.clear();
                                automatedCheckoutSystem.dBRead(kioskList);
                                dBTextPanel.setText(String.valueOf(kioskList));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(adminPanel, "Error: Quantity field is not a number. Try again in the specified format.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(adminPanel, "Error: Price field is not a number. Try again in the specified format.");
                    }
                } else {
                    JOptionPane.showMessageDialog(adminPanel, "Error: No entry detected in Name box, please try again.");
                }
            }
        });

        deleteOrderButton.addActionListener(new ActionListener() {      //Allows admin to delete stock orders. Also updates admin screen to display new stock.
            @Override
            public void actionPerformed(ActionEvent e) {
                String dID = JOptionPane.showInputDialog(adminPanel, "Please enter barcode of item to delete.");
                int warning = JOptionPane.showConfirmDialog(adminPanel, "Please confirm you would like to delete this stock: " + dID, "WARNING", JOptionPane.YES_NO_OPTION);
                if (warning == JOptionPane.YES_OPTION) {
                    try {
                        kioskSystem.removeDB(dID);
                        kioskList.clear();
                        automatedCheckoutSystem.dBRead(kioskList);
                        dBTextPanel.setText(String.valueOf(kioskList));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    remove(warning);
                }
            }
        });

        decreaseStockButton.addActionListener(new ActionListener() {    //Decreases the quantity of a stock.
            @Override
            public void actionPerformed(ActionEvent e) {
                String cQItemID = JOptionPane.showInputDialog(adminPanel, "Please enter item ID.", null);
                String cQAmountString = JOptionPane.showInputDialog(adminPanel, "Please enter amount to remove.", null);
                Integer cQAmount = Integer.valueOf(cQAmountString);
                try {
                    kioskSystem.changeQuantityDB(cQItemID, cQAmount, false);    //Sends false, to show the quantity is to be removed.
                    kioskList.clear();
                    automatedCheckoutSystem.dBRead(kioskList);
                    dBTextPanel.setText(String.valueOf(kioskList));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        increaseStockButton.addActionListener(new ActionListener() {    //Increases quantity of a stock.
            @Override
            public void actionPerformed(ActionEvent e) {
                String cQItemID = JOptionPane.showInputDialog(adminPanel, "Please enter item ID.", null);
                String cQAmountString = JOptionPane.showInputDialog(adminPanel, "Please enter amount to add.", null);
                Integer cQAmount = Integer.valueOf(cQAmountString);
                try {
                    kioskSystem.changeQuantityDB(cQItemID, cQAmount, true);     //Sends true, to show the quantity is to be added.
                    kioskList.clear();
                    automatedCheckoutSystem.dBRead(kioskList);
                    dBTextPanel.setText(String.valueOf(kioskList));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        //CUSTOMER PANEL CODE
        customerButton.addActionListener(new ActionListener() {     //Takes user to the customer screen of the kiosk.
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(kioskPanel, "customerCard");
            }
        });

        customerBackButton.addActionListener(new ActionListener() {     //Takes user back to the start screen.
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(kioskPanel, "loginCard");
            }
        });

        increaseButton.addActionListener(new ActionListener() {     //Increase quantity of item in customer basket.
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemID = JOptionPane.showInputDialog(customerPanel, "Which item quantity are you increasing? Please insert bardcode.");
                String itemChange = JOptionPane.showInputDialog(customerPanel, "How much would you like to add to " + basketList.get(Integer.parseInt(itemID)) + "? Current amount is: " + basketList.get(Integer.valueOf(itemID) + 2));
                if (Integer.parseInt(itemID) * 4 > basketList.size()) {     //ItemID is multiplied by 4 as each "item" representing the stock is 4 indexes long.
                    JOptionPane.showMessageDialog(customerPanel, "Sorry, the item is not in your basket.");
                } else {    //If itemID is in the basket, is finds the position of the quantity index, saves it, changes it into new quantity, replaces the value in the arraylist then resets the list model.
                    Integer quantityPos = basketList.indexOf(itemID) + 3;
                    Integer originalPrice = Integer.parseInt(basketList.get(quantityPos - 1)) / Integer.parseInt(basketList.get(quantityPos));
                    Integer newQuantity = Integer.parseInt(basketList.get(quantityPos)) + Integer.parseInt(itemChange);
                    basketList.set(quantityPos, String.valueOf(newQuantity));
                    DefaultListModel<String> model = new DefaultListModel<>();
                    model.addElement(basketList.toString());
                    basketContent.setModel(model);
                    totalList.add(originalPrice * newQuantity);
                    totalList.remove(originalPrice);

                    //Integer newPrice = originalPrice * newQuantity;
                    //basketList.set(quantityPos - 1, String.valueOf(newPrice));
                }
            }
        });

        decreaseButton.addActionListener(new ActionListener() {     //This button reduces the quantity of an item in the basket. Code is identical to the increase button, except for reducing quantity instead of increasing.
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemID = JOptionPane.showInputDialog(customerPanel, "Which item quantity are you decreasing? Please insert bardcode.");
                String itemChange = JOptionPane.showInputDialog(customerPanel, "How much would you like to remove from " + basketList.get(Integer.parseInt(itemID) + 1) + "? Current amount is: " + basketList.get(Integer.valueOf(itemID) + 3));
                if (Integer.parseInt(itemID) * 4 > basketList.size()) {
                    JOptionPane.showMessageDialog(customerPanel, "Sorry, the item is not in your basket.");
                } else {
                    Integer quantityPos = basketList.indexOf(itemID) + 3;
                    Integer originalPrice = Integer.parseInt(basketList.get(quantityPos - 1)) / Integer.parseInt(basketList.get(quantityPos));
                    Integer newQuantity = Integer.parseInt(basketList.get(quantityPos)) - Integer.parseInt(itemChange);
                    basketList.set(quantityPos, String.valueOf(newQuantity));
                    basketList.set(quantityPos - 1, String.valueOf(originalPrice * newQuantity));
                    validate();
                }
            }
        });

        scanButton.addActionListener(new ActionListener() {     //This allows the user to scan a new item into the basket.
            @Override
            public void actionPerformed(ActionEvent e) {
                String scanInsert = JOptionPane.showInputDialog(customerPanel, "Please scan purchase ID below.", null);
                Integer insertID = Integer.parseInt(scanInsert.trim());
                kioskDB.readStock();
                try {
                    kioskDB.searchDB(insertID);
                    basketList.add(String.valueOf(kioskDB.sID));
                    basketList.add(kioskDB.sName.trim());
                    basketList.add(kioskDB.sPrice.trim());
                    basketList.add(String.valueOf(1));
                    DefaultListModel<String> model = new DefaultListModel<>();
                    model.addElement(basketList.toString() + System.lineSeparator());
                    basketContent.setModel(model);
                    totalList.add(Integer.valueOf(kioskDB.sPrice.trim()));
                } catch (IndexOutOfBoundsException eeee) {
                    JOptionPane.showMessageDialog(customerPanel, "Sorry, the specified item could not be found. Please be precise with the item barcode.");
                }
            }
        });

        searchItemButton.addActionListener(new ActionListener() {   //This is the code to search for an item by name instead of ID. COde is mostly identical, since it just finds the ID of the inputted name.
            @Override
            public void actionPerformed(ActionEvent e) {
                String scanInsert = JOptionPane.showInputDialog(customerPanel, "Please type in product name below.", null);
                String scanInsertID = kioskSystem.nameSearchDB(scanInsert);
                Integer sInsertID = Integer.valueOf(scanInsertID);
                kioskDB.readStock();
                try {
                    kioskDB.searchDB(sInsertID);
                    basketList.add(String.valueOf(kioskDB.sID));
                    basketList.add(kioskDB.sName.trim());
                    basketList.add(kioskDB.sPrice.trim());
                    basketList.add(String.valueOf(1));
                    DefaultListModel<String> model = new DefaultListModel<>();
                    model.addElement(basketList.toString() + System.lineSeparator());
                    basketContent.setModel(model);
                    totalList.add(Integer.valueOf(kioskDB.sPrice.trim()));
                } catch (IndexOutOfBoundsException eee) {
                    JOptionPane.showMessageDialog(customerPanel, "Sorry, the specified item could not be found. Please be precise with the item name.");
                }
            }
        });

        // PAYMENT PANEL CODE
        checkoutButton.addActionListener(new ActionListener() {     //Button for moving on to selecting purchase method.
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Cash", "Card", "Cancel"};
                int choice = JOptionPane.showOptionDialog(customerPanel, "How would you like you pay for your shopping?", "Payment Method", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                if (choice == 0) {  //SOMETHING WRONG WITH THIS CODE, MESSES UP TOTAL FOR SOME REASON ON CHECKOUT. COULD BE ANY OF THE METHODS RELATED TO CURRENCY CONVERSION. Nevermind, fixed.
                    switchPanel(kioskPanel, "cashCard");
                    String totalTemp = String.valueOf(kioskSystem.getTotal(totalList));
                    totalLabel.setText("Total: £" + kioskSystem.currencyConversion(totalTemp));     //This code sets the textlabel of the amount to pay.

                    //totalLabel.setText("Total: £" + totalTemp);
                } else if (choice == 1) {
                    switchPanel(kioskPanel, "cardCard");
                }
            }
        });

        payCashButton.addActionListener(new ActionListener() {      //Pays the total amount in cash.
            @Override
            public void actionPerformed(ActionEvent e) {
                //cashPaid = Integer.parseInt(String.valueOf(cashInput));   //Attempted to track the amount entered, but caused a really bizarre error. It's to do with reading from the text entered, but I have been unable to find a workaround. Hopefully the rest of the code can prove I'd be able to do it.
                switchPanel(kioskPanel, "receiptCard");

                Integer q = basketList.size() / 4;  //Divided by 4, to represent each item entered into the database.
                for (int i = 0;0 < q;i++) {
                    Integer quantity = Integer.valueOf(basketList.get(i + 3));
                    try {
                        kioskSystem.changeQuantityDB(String.valueOf(i + 1), quantity, false);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {   //Submit the credit card details entered. Ideally, the bank would have a method that would call the values and compare it to the entered amount.
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNum = String.valueOf(cNumberInput.getText());
                String securityNum = String.valueOf(sNumberInput.getText());
                String cHolderName = String.valueOf(cHolderInput.getText());
                String bAddressOne = String.valueOf(bAddress1Input.getText());
                String bAddressTwo = String.valueOf(bAddress2Input.getText());
                boolean isCNum = cardNum.chars().allMatch( Character::isDigit );
                boolean isSNum = securityNum.chars().allMatch( Character::isDigit );

                if (cardNum.length() == 16) {
                    if (isCNum == true) {
                        if (securityNum.length() == 3 | securityNum.length() == 4) {
                            if (isSNum == true) {
                                if (cHolderInput.getText().isEmpty() == false) {
                                    if (bAddress1Input.getText().isEmpty() == false | bAddress2Input.getText().isEmpty() == false) {
                                        bank confirmBank = new bank();
                                        confirmBank.acceptDenyPayment(cardNum, securityNum, cHolderName, bAddressOne, bAddressTwo);
                                        JOptionPane.showConfirmDialog(cardPanel, "Your payment has been accepted!");

                                        Integer q = basketList.size() / 4;
                                        for (int i = 0;0 < q;i++) { //This code throws an error, but everything seems to work just fine at removing from the database.
                                            Integer quantity = Integer.valueOf(basketList.get(i + 3));
                                            try {
                                                kioskSystem.changeQuantityDB(String.valueOf(i + 1), quantity, false);
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
                                        }

                                        switchPanel(kioskPanel, "receiptCard");
                                    } else {
                                        JOptionPane.showConfirmDialog(cardPanel, "You did not input an address, please try again.", "Error", JOptionPane.OK_CANCEL_OPTION);
                                    }
                                } else {
                                    JOptionPane.showConfirmDialog(cardPanel, "You did not input a card holder name, please try again.", "Error", JOptionPane.OK_CANCEL_OPTION);
                                }
                            } else {
                                JOptionPane.showConfirmDialog(cardPanel, "Sorry, the security number inserted contains invalid characters, please double check your input.", "Error", JOptionPane.OK_CANCEL_OPTION);
                            }
                        } else {
                            JOptionPane.showConfirmDialog(cardPanel, "Sorry, the security number is not the correct size, please double check your input.", "Error", JOptionPane.OK_CANCEL_OPTION);
                        }
                    } else {
                        JOptionPane.showConfirmDialog(cardPanel, "Sorry, the card number inserted contains invalid characters. Please double check your input.", "Error", JOptionPane.OK_CANCEL_OPTION);
                    }
                } else {
                    JOptionPane.showConfirmDialog(cardPanel, "Sorry, the card number is not the correct size, please double check your input.", "Error", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        });

        printReceiptButton.addActionListener(new ActionListener() {     //Prints the receipt, showing all the purchased items and total cost. If payment worked properly, then the change as well.
            @Override
            public void actionPerformed(ActionEvent e) {
                kioskThread.run();
                receiptTextArea.append("00:12 25/12/2042" + System.lineSeparator() + "SHOP CO. - Thank you for shopping at Shop Co. Remember: We're Right Behind You, Every Step Of The WayTM." + System.lineSeparator());

                Integer s = basketList.size() / 4;

                for (int i = 0;i < s;i++) {
                    kioskThread.run();
                    Integer purchaseIDPos = i * 4;
                    String purchaseName = basketList.get(purchaseIDPos + 1);
                    String purchasePrice = basketList.get(purchaseIDPos + 2);
                    String purchaseQuantity = basketList.get(purchaseIDPos + 3);
                    System.out.println("This is item " + purchaseName);     // Thread wasn't able to output new value for each thread, so this piggybacks off the thread to print the name.
                    receiptTextArea.append("x" + purchaseQuantity + " " + purchaseName + " - £" + kioskSystem.currencyConversion(purchasePrice) + System.lineSeparator());
                }
                receiptTextArea.append(System.lineSeparator() + "SUBTOTAL: £" + kioskSystem.currencyConversion(String.valueOf(kioskSystem.getTotal(totalList))) + System.lineSeparator() + "VAT = £.00" + System.lineSeparator() + "TOTAL: £" + kioskSystem.currencyConversion(String.valueOf(kioskSystem.getTotal(totalList))));
            }
        });
    }

    // OTHER CODE
    public void switchPanel(Container container, String panelName) {
        CardLayout card = (CardLayout) (container.getLayout());
        card.show(container, panelName);
    }

    public void backButton(String backCardName) {
        switchPanel(kioskPanel, backCardName);
    }

    /*public void quantityIncrease (String stockItem) {
        int stockItemIndex = basketList.indexOf(stockItem);
        int stockQuantityIndex = stockItemIndex + 2;
        String stockItemQuantityString = basketList.get(stockQuantityIndex);
        int stockItemQuantityReplace = Integer.parseInt(stockItemQuantityString);
        stockItemQuantityReplace++;
        stockItemQuantityString = String.valueOf(stockItemQuantityReplace);
        basketList.set(stockQuantityIndex, stockItemQuantityString);
    }*/
}

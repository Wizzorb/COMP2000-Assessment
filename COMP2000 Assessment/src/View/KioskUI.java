package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import Controller.automatedCheckoutSystem;
import Model.stockDatabase;
import com.company.runnableMain;

public class KioskUI extends JFrame {
    private JPanel kioskPanel;
    private JPanel loginPanel;
    private JButton adminButton;
    private JButton customerButton;
    private JPanel adminPanel;
    private JTextArea dBTextArea;
    private JButton backButton;
    private JButton newOrderButton;
    private JPanel customerPanel;
    private JList basketContent;
    private JButton scanButton;
    private JButton searchItemButton;
    private JButton checkoutButton;
    private JButton increaseButton;
    private JButton decreaseButton;
    private JButton backButton1;
    private JTextPane dBTextPanel;
    private JScrollPane basketScroll;
    private JPanel cardPanel;
    private JPasswordField cNumberInput;
    private JTextField sNumberInput;
    private JTextField cHolderInput;
    private JButton submitButton;
    private JTextField bAddress1Input;
    private JTextField bAddress2Input;
    private JPanel cashPanel;
    private JTextField textField1;
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

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInsert = JOptionPane.showInputDialog(kioskPanel, "Please insert admin Username.", null);
                String passwordInsert = JOptionPane.showInputDialog(kioskPanel, "Please insert admin Password", null);
                int auth = 0;
                //automatedCheckoutSystem.adminLogin(usernameInsert, passwordInsert, auth);
                if (usernameInsert == "a" & passwordInsert == "p") {
                    JOptionPane.showConfirmDialog(kioskPanel, "Incorrect credentials");
                } else {
                    switchPanel(kioskPanel, "adminCard");
                    //kioskDB.readStock(kioskList);
                    automatedCheckoutSystem.dBRead(basketList);
                    kioskList.add(automatedCheckoutSystem.dBRead(basketList).toString());
                    dBTextPanel.setText(kioskList.toString());
                }
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(kioskPanel, "customerCard");
            }
        });

        increaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String itemID = JOptionPane.showInputDialog(customerPanel, "Which item are you re-adding?")
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Cash", "Card", "Cancel"};
                int choice = JOptionPane.showOptionDialog(customerPanel, "How would you like you pay for your shopping?", "Payment Method", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                if (choice == 0) {  //SOMETHING WRONG WITH THIS CODE, FUCKS UP TOTAL FOR SOME REASON ON CHECKOUT. COULD BE ANY OF THE METHODS RELATED TO CURRENCY CONVERSION.
                    switchPanel(kioskPanel, "cashCard");
                    String totalTemp = String.valueOf(kioskSystem.getTotal(totalList));
                    totalLabel.setText("Total: £" + kioskSystem.currencyConversion(totalTemp));
                    //totalLabel.setText("Total: £" + totalTemp);
                } else if (choice == 1) {
                    switchPanel(kioskPanel, "cardCard");
                }
            }
        });

        payCashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(kioskPanel, "receiptCard");
            }
        });

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scanInsert = JOptionPane.showInputDialog(customerPanel, "Please scan purchase ID below.", null);
                Integer insertID = Integer.parseInt(scanInsert.trim());
                kioskDB.readStock();
                try {
                    kioskDB.searchDB(insertID);
                    basketList.add(String.valueOf(kioskDB.sID));
                    basketList.add(kioskDB.sName.trim());
                    basketList.add(kioskSystem.currencyConversion(kioskDB.sPrice.trim()));
                    basketList.add(kioskDB.sQuantity.trim());
                    DefaultListModel<String> model = new DefaultListModel<>();
                    model.addElement(basketList.toString());
                    basketContent.setModel(model);
                    totalList.add(Integer.valueOf(kioskDB.sPrice.trim()));
                } catch (IndexOutOfBoundsException eeee) {
                    JOptionPane.showMessageDialog(customerPanel, "Sorry, the specified item could not be found. Please be precise with the item barcode.");
                }
            }
        });

        searchItemButton.addActionListener(new ActionListener() {
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
                    basketList.add(kioskSystem.currencyConversion(kioskDB.sPrice.trim()));
                    basketList.add(kioskDB.sQuantity.trim());
                    DefaultListModel<String> model = new DefaultListModel<>();
                    model.addElement(basketList.toString());
                    basketContent.setModel(model);
                    totalList.add(Integer.valueOf(kioskDB.sPrice.trim()));
                } catch (IndexOutOfBoundsException eee) {
                    JOptionPane.showMessageDialog(customerPanel, "Sorry, the specified item could not be found. Please be precise with the item name.");
                }
            }
        });

        newOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int success = 1;
                String newSName = JOptionPane.showInputDialog(adminPanel, "Enter new stock name.", null);
                String newSPrice = JOptionPane.showInputDialog(adminPanel, "Enter new stock price, in pence.", null);
                String newSQuantity = JOptionPane.showInputDialog(adminPanel, "Enter new stock stored quantity, in numbered format.", null);
                if (newSName.isEmpty() == false) {
                    try {
                        Integer.parseInt(newSPrice);
                    } catch (NumberFormatException ee) {
                        success = 0;
                    }
                    if (success == 1) {
                        try {
                            Integer.parseInt(newSQuantity);
                        } catch (NumberFormatException eee) {
                            success = 0;
                        }
                        if (success == 1) {
                            try {
                                kioskSystem.writeDB(newSName.toLowerCase(), newSPrice, newSQuantity);
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

        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dID = JOptionPane.showInputDialog(adminPanel, "Please enter barcode of item to delete.");
                int warning = JOptionPane.showConfirmDialog(adminPanel, "Please confirm you would like to delete this stock: " + dID, "WARNING", JOptionPane.YES_NO_OPTION);
                if (warning == JOptionPane.YES_OPTION) {
                    try {
                        kioskSystem.removeDB(dID);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    remove(warning);
                }
            }
        });

        decreaseStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cQItemID = JOptionPane.showInputDialog(adminPanel, "Please enter item ID.", null);
                String cQAmountString = JOptionPane.showInputDialog(adminPanel, "Please enter amount to remove.", null);
                Integer cQAmount = Integer.valueOf(cQAmountString);
                try {
                    kioskSystem.changeQuantityDB(cQItemID, cQAmount, false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        increaseStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cQItemID = JOptionPane.showInputDialog(adminPanel, "Please enter item ID.", null);
                String cQAmountString = JOptionPane.showInputDialog(adminPanel, "Please enter amount to add.", null);
                Integer cQAmount = Integer.valueOf(cQAmountString);
                try {
                    kioskSystem.changeQuantityDB(cQItemID, cQAmount, true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        printReceiptButton.addActionListener(new ActionListener() {
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
                    receiptTextArea.append("x" + purchaseQuantity + " " + purchaseName + " - £" + purchasePrice + System.lineSeparator());
                }
                receiptTextArea.append(System.lineSeparator() + "SUBTOTAL: £" + kioskSystem.currencyConversion(String.valueOf(kioskSystem.getTotal(totalList))) + System.lineSeparator() + "VAT = £0.00" + System.lineSeparator() + "TOTAL: £" + kioskSystem.currencyConversion(String.valueOf(kioskSystem.getTotal(totalList))));
            }
        });
    }

    public void switchPanel(Container container, String panelName) {
        CardLayout card = (CardLayout) (container.getLayout());
        card.show(container, panelName);
    }

    public void quantityIncrease (String stockItem) {
        int stockItemIndex = basketList.indexOf(stockItem);
        int stockQuantityIndex = stockItemIndex + 2;
        String stockItemQuantityString = basketList.get(stockQuantityIndex);
        int stockItemQuantityReplace = Integer.parseInt(stockItemQuantityString);
        stockItemQuantityReplace++;
        stockItemQuantityString = String.valueOf(stockItemQuantityReplace);
        basketList.set(stockQuantityIndex, stockItemQuantityString);
    }
}

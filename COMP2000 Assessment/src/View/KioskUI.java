package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import Controller.automatedCheckoutSystem;
import Model.stockDatabase;

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

    stockDatabase kioskDB = new stockDatabase();
    ArrayList<String> kioskList = new ArrayList<>();
    ArrayList<String> basketList = new ArrayList<>();

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
                if (usernameInsert == "admin" && passwordInsert == "password") {
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

        scanButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scanInsert = JOptionPane.showInputDialog(customerPanel, "Please scan purchase ID below.", null);
                Integer insertID = Integer.parseInt(scanInsert);
                kioskDB.readStock();
                kioskDB.searchDB(insertID);
                basketList.add(kioskDB.sID, kioskDB.sID.toString());
                basketList.add(kioskDB.sID+1, kioskDB.sName);
                basketList.add(kioskDB.sID+2, kioskDB.sPrice.toString());
                basketList.add(kioskDB.sID+3, kioskDB.sQuantity.toString());
            }
        }));
    }

    public void switchPanel(Container container, String panelName) {
        CardLayout card = (CardLayout) (container.getLayout());
        card.show(container, panelName);
    }
}

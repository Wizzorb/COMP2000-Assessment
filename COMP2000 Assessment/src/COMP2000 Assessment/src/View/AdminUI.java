package View;

import Model.stockDatabase;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminUI extends JFrame {
    private JTextArea dBDisplay;
    private JButton backButton;
    private JButton orderButton;
    private JPanel adminPanel;

    stockDatabase stockDisplay = new stockDatabase();
    ArrayList<String> sDList = new ArrayList<>();

    public void main(String [] args) {
        AdminUI page = new AdminUI("AdminPage");
        page.setVisible(true);
    }

    public AdminUI(String title) {
        super(title);
        setContentPane(adminPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        dBDisplay.setLineWrap(true);
        pack();
        stockDisplay.readStock(sDList);
        dBDisplay.setText(sDList.toString());
    }
}

package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.company.Main;

public class LoginUI extends JFrame {
    private JPanel loginPanel;
    private JButton adminButton;
    private JButton customerButton;

    public LoginUI(String title) {
        super(title);
        setContentPane(loginPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        pack();

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void  actionPerformed(ActionEvent e) {
                AdminUI adminPage = new AdminUI("AdminPage");
                adminPage.setVisible(true);

            }
        });
    }
}

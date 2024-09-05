package atm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class Pin {
    public void pinView(String cardNum) {
        atm.Commons common = new atm.Commons();
        JFrame frame = common.Frame(); // No need for type casting
        Font txt = new Font("", Font.BOLD, 15);
        Home home = new Home();
        Admin admin = new atm.Admin();

        //---------------PASSWORD----------------
        JLabel pswd = new JLabel("ENTER YOUR PIN");
        pswd.setBounds(50, 270, 250, 20);
        pswd.setFont(txt);
        JPasswordField pswdField = new JPasswordField();
        pswdField.setBounds(50, 300, 500, 35);
        pswdField.setFont(txt);
        frame.add(pswdField);
        frame.add(pswd);
        //-----------------------------------------

        //-----------------BUTTON-----------------
        JButton cont = new JButton("CONTINUE"); // Corrected typo "COUNTINUE" to "CONTINUE"
        cont.setBounds(200, 400, 200, 50);
        cont.setFont(new Font("Rockwell", Font.BOLD, 25));
        frame.add(cont);
        cont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SQLManage man = new SQLManage();
                    // Use getPassword() to retrieve password from JPasswordField
                    ResultSet rst = man.check(cardNum, new String(pswdField.getPassword()));
                    if (rst.next()) {
                        if (rst.getString("card").equals("admin")) {
                            admin.adminView();
                        } else {
                            home.homeView(rst.getInt("id"));
                        }
                        frame.dispose(); // Dispose the frame after view changes
                    } else {
                        Fail fail = new Fail();
                        fail.failView("WRONG PIN!!!");
                        frame.dispose();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //----------------------------------------

        // Set frame size and location if not set in Commons
        frame.setSize(600, 500); // Example size, adjust as needed
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure application exits on close
        frame.setVisible(true);
    }
}

/*
 * Created by JFormDesigner on Tue May 16 12:43:23 CEST 2023
 */

package org.example;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import net.miginfocom.swing.*;

import static javax.swing.JOptionPane.*;


/**
 * @author koron
 */
public class NewCompetitor extends JFrame {
    public NewCompetitor() {
        initComponents();
        setTitle("Add New Competitor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        getCountries();
        getTechs();
    }

    private void newDbInsertMouseClicked(MouseEvent e) {
        try {
            String contestantName = tfName.getText().toString();
            String tech = techCombo.getSelectedItem().toString();
            String country = countryCombo.getSelectedItem().toString();
            String points = tfPoints.getText().toString();
            Class.forName("com.mysql.cj.jdbc.Driver"); //set sql driver for java then create the connection below.
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vizsga_szakma?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8", "java", "7M7udObER.a-TFy6");
            Statement stmt = conn.createStatement();
            stmt.addBatch("SET @orszagIdF = (SELECT id FROM orszag WHERE orszagNev = '"+country+"');");
            stmt.addBatch("SET @szakmaIdF = (SELECT id FROM szakma WHERE szakmaNev = '"+tech+"');");
            stmt.addBatch("INSERT INTO versenyzo(nev, szakmaId, orszagId, pont) VALUES ('"+contestantName+"', @szakmaIdF, @orszagIdF, '"+points+"');");
            stmt.executeBatch();
            Object[] option1 = {"CONTINUE", "EXIT"};
            int option = JOptionPane.showOptionDialog(null, "Successfully added new competitor", "Success", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option1, null);
            if(option != JOptionPane.YES_OPTION){
                dispose();
            }
        }
        catch (Exception E){
            System.out.println("Hiba a beillesztésben!\n" +E);
        }
    }

    private void btnCloseWindowMouseClicked(MouseEvent e) {
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Korondi Kristóf
        label1 = new JLabel();
        tfName = new JTextField();
        label2 = new JLabel();
        techCombo = new JComboBox();
        label3 = new JLabel();
        countryCombo = new JComboBox();
        label4 = new JLabel();
        tfPoints = new JTextField();
        btnCloseWindow = new JButton();
        newDbInsert = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[133,fill]" +
            "[354,fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Full name");
        contentPane.add(label1, "cell 0 0");
        contentPane.add(tfName, "cell 1 0");

        //---- label2 ----
        label2.setText("Technology");
        contentPane.add(label2, "cell 0 1");
        contentPane.add(techCombo, "cell 1 1");

        //---- label3 ----
        label3.setText("Country");
        contentPane.add(label3, "cell 0 2");
        contentPane.add(countryCombo, "cell 1 2");

        //---- label4 ----
        label4.setText("Points");
        contentPane.add(label4, "cell 0 3");
        contentPane.add(tfPoints, "cell 1 3");

        //---- btnCloseWindow ----
        btnCloseWindow.setText("Close Window");
        btnCloseWindow.setBackground(new Color(0xff4945));
        btnCloseWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnCloseWindowMouseClicked(e);
            }
        });
        contentPane.add(btnCloseWindow, "cell 0 4 2 1");

        //---- newDbInsert ----
        newDbInsert.setText("Add new competitor");
        newDbInsert.setBackground(new Color(0x33ff33));
        newDbInsert.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newDbInsertMouseClicked(e);
            }
        });
        contentPane.add(newDbInsert, "cell 0 4 2 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Korondi Kristóf
    private JLabel label1;
    private JTextField tfName;
    private JLabel label2;
    private JComboBox techCombo;
    private JLabel label3;
    private JComboBox countryCombo;
    private JLabel label4;
    private JTextField tfPoints;
    private JButton btnCloseWindow;
    private JButton newDbInsert;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void getCountries(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //set sql driver for java then create the connection below.
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vizsga_szakma?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8", "java", "7M7udObER.a-TFy6");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT orszagNev FROM orszag");
            while(rs.next()){
                countryCombo.addItem(rs.getString("orszagNev"));
            }
        }
        catch (Exception E){
            System.out.println("Hiba a beillesztésben!\n" +E);
        }
    }

    private void getTechs(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //set sql driver for java then create the connection below.
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vizsga_szakma?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8", "java", "7M7udObER.a-TFy6");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT szakmaNev FROM szakma");
            while(rs.next()){
                techCombo.addItem(rs.getString("szakmaNev"));
            }
        }
        catch (Exception E){
            System.out.println("Hiba a beillesztésben!\n" +E);
        }
    }
}
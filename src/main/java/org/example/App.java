/*
 * Created by JFormDesigner on Tue May 16 09:47:13 CEST 2023
 */

package org.example;

import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.*;


/**
 * @author koron
 */
public class App extends JFrame {

    private String[] dataTableCols = new String[]{"Id", "Teljes név", "Szakma", "Ország", "Pont"}; //define the tables columns


    public App() {
        initComponents();
        putriJavaPlaceholder();
        setTitle("Ablakxd");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addTableColumns();
        getTableData();
    }



    private void okBtnMouseClicked(MouseEvent e) {
        System.out.println("Valami");
    }

    private void delRowMouseClicked(MouseEvent e) {

        String[] rowData = new String[4];
        for (int i = 0; i < rowData.length; i++){
            rowData[i] = dataTable.getValueAt(dataTable.getSelectedRow(), i+1).toString();
        }
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //set sql driver for java then create the connection below.
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vizsga_szakma?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8","vizsga","rX7KNBz-wwZTP9*9");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM versenyzo WHERE nev = '"+rowData[0]+"' AND szakmaId IN (SELECT id FROM szakma WHERE szakmaNev = '"+rowData[1]+"') AND orszagId IN (SELECT id FROM orszag WHERE orszagNev = '"+rowData[2]+"') AND pont = "+rowData[3]);
            //System.out.println("Törölve cigo");
            getTableData();
        } catch (Exception E) {
            System.out.println("Hiba a törlésnél!"+E);
        }
        /*
        Use only for debug! Console will show the info.
        for (int i = 0; i < rowData.length; i++){
            System.out.println(rowData[i]);
        }*/
    }

    private void searchDataKeyReleased(KeyEvent e) {
        if(searchData.getText().length() >= 4){
            try{
                DefaultTableModel table = (DefaultTableModel) dataTable.getModel();
                Class.forName("com.mysql.cj.jdbc.Driver"); //set sql driver for java then create the connection below.
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vizsga_szakma?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8","vizsga","rX7KNBz-wwZTP9*9");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM versenyzo INNER JOIN szakma ON versenyzo.szakmaId = szakma.id INNER JOIN orszag ON versenyzo.orszagId = orszag.id WHERE nev LIKE '%"+searchData.getText()+"%' OR szakma.szakmaNev LIKE '%"+searchData.getText()+"%' OR orszag.orszagNev LIKE '%"+searchData.getText()+"%'");
                String[] result = new String[5];
                delTableContent();
                int identifier = 1; //basic identifier from 1
                while(rs.next()) { //this will add the data to the table.
                    result[0] = String.valueOf(identifier);
                    result[1] = rs.getString("nev");
                    result[2] = rs.getString("szakmaNev");
                    result[3] = rs.getString("orszagNev");
                    result[4] = rs.getString("pont");
                    table.addRow(result);
                    identifier++;
                }
            }
            catch (Exception E) {
                System.out.println("Hiba a keresésnél!" +E);
            }
        }
    }

    private void newCompMouseClicked(MouseEvent e) {
        JFrame newStuff = new NewCompetitor();
        newStuff.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                delTableContent();
                getTableData();
                setVisible(true);
            }
        });
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Korondi Kristóf
        searchData = new JTextField();
        scrollPane1 = new JScrollPane();
        dataTable = new JTable();
        okBtn = new JButton();
        delRow = new JButton();
        newComp = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- searchData ----
        searchData.setToolTipText("Points can not be searched for.");
        searchData.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchDataKeyReleased(e);
            }
        });
        contentPane.add(searchData, "cell 0 1 5 1");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(dataTable);
        }
        contentPane.add(scrollPane1, "cell 0 2 5 1");

        //---- okBtn ----
        okBtn.setText("OK test");
        okBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                okBtnMouseClicked(e);
            }
        });
        contentPane.add(okBtn, "cell 0 4 5 1");

        //---- delRow ----
        delRow.setText("Delete Selected Row");
        delRow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delRowMouseClicked(e);
            }
        });
        contentPane.add(delRow, "cell 0 4 5 1");

        //---- newComp ----
        newComp.setText("Add New Competitor");
        newComp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newCompMouseClicked(e);
            }
        });
        contentPane.add(newComp, "cell 0 5 5 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Korondi Kristóf
    private JTextField searchData;
    private JScrollPane scrollPane1;
    private JTable dataTable;
    private JButton okBtn;
    private JButton delRow;
    private JButton newComp;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public void addTableColumns(){
        DefaultTableModel table = (DefaultTableModel) dataTable.getModel();
        for(int i = 0; i < dataTableCols.length; i++){
            table.addColumn(dataTableCols[i]);
        } //INFO: THIS IS HOW YOU ADD COLUMNS TO DATATABLE!!!!!
    }

    public void delTableContent(){
        DefaultTableModel table = (DefaultTableModel) dataTable.getModel();
        int sortorles = table.getRowCount();
        for(int i = 0; i < sortorles; i++) {
            table.removeRow(0); //If there are previous values, this snippet will delete all of them
        }
    }

    public void getTableData() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //set sql driver for java then create the connection below.
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vizsga_szakma?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8","vizsga","rX7KNBz-wwZTP9*9");
            Statement stmt = conn.createStatement(); //create a statement object, that gets passed to the sql.
            DefaultTableModel table = (DefaultTableModel) dataTable.getModel(); //get table model and write the query you want below.
            ResultSet rs = stmt.executeQuery("SELECT * FROM versenyzo INNER JOIN szakma ON versenyzo.szakmaId = szakma.id INNER JOIN orszag ON versenyzo.orszagId = orszag.id ORDER BY szakma.szakmaNev, nev");
            String[] result = new String[5]; //this defines how many data it needs. This is 4 = 4 data is required but when filling it, it'll index from 0!


            int identifier = 1; //basic identifier from 1
            while(rs.next()) { //this will add the data to the table.
                result[0] = String.valueOf(identifier);
                result[1] = rs.getString("nev");
                result[2] = rs.getString("szakmaNev");
                result[3] = rs.getString("orszagNev");
                result[4] = rs.getString("pont");
                table.addRow(result);
                identifier++;
            }
        }
        catch(Exception E) {
            System.out.println("Hiba van az adatbetöltéssel!"+E);
        }
    }

    public void putriJavaPlaceholder(){
        searchData.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchData.getText().equals("What do you want to search for? (Min 5 characters)")) {
                    searchData.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchData.getText().isEmpty()) {
                    searchData.setText("What do you want to search for? (Min 5 characters)");
                }
            }
        });
    }

    public static void main(String[] args) { //you need a main branch to run the app
        new App();

    }
}

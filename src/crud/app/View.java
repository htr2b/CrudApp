package crud.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class View extends JFrame {
    private List<User> userList = new ArrayList<>();
    private JTextField fNameField;
    private JTextField lNameField;
    private JTextField emailField;
    private JTextField searchField;
    private JTable userTable;

    public View() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("CRUD Application");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        userTable = new JTable();
        userTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "First Name", "Last Name", "Email"}
        ));
        userTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(userTable);

    }

  

    private void loadData() {
       
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new View().setVisible(true));
    }
}

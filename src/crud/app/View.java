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

        // Initialize components
        JLabel fNameLabel = new JLabel("First Name:");
        JLabel lNameLabel = new JLabel("Last Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel searchLabel = new JLabel("Search by ID:");

        fNameField = new JTextField(20);
        lNameField = new JTextField(20);
        emailField = new JTextField(20);
        searchField = new JTextField(20);

        JButton submitButton = new JButton("Create");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");

        // Set button styles
        setButtonStyle(submitButton);
        setButtonStyle(updateButton);
        setButtonStyle(deleteButton);
        setButtonStyle(searchButton);
        setButtonStyle(clearButton);

        // Table setup
        userTable = new JTable();
        userTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "First Name", "Last Name", "Email"}
        ));
        userTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(userTable);

        // Button actions
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreate();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdate();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDelete();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleClear();
            }
        });

        // Layout setup using GroupLayout for a modern look
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(fNameLabel, gbc);

        gbc.gridx = 1;
        panel.add(fNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lNameLabel, gbc);

        gbc.gridx = 1;
        panel.add(lNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(submitButton, gbc);

        gbc.gridx = 1;
        panel.add(updateButton, gbc);

        gbc.gridx = 2;
        panel.add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(searchLabel, gbc);

        gbc.gridx = 1;
        panel.add(searchField, gbc);

        gbc.gridx = 2;
        panel.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(clearButton, gbc);

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(tableScrollPane, BorderLayout.CENTER);
    }

  
  private void setButtonStyle(JButton button) {
       
    }

   private void handleCreate() {
       
    }

    private void handleUpdate() {
        
    }

    private void handleDelete() {
       
    }

    private void handleSearch() {
      
    }

    private void handleClear() {
      
    }

    private void clearFields() {
       
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);

        for (User user : userList) {
            model.addRow(new Object[]{user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new View().setVisible(true));
    }
}

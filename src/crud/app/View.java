package crud.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class View extends JFrame {

    private JTextField fNameField;
    private JTextField lNameField;
    private JTextField emailField;
    private JTextField searchField;
    private JTable userTable;

    // MySQL Connection
    private final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private final String DB_USER = "htr2b";
    private final String DB_PASSWORD = "htr2b";

    public View() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setTitle("CRUD Application");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        setButtonStyle(submitButton);
        setButtonStyle(updateButton);
        setButtonStyle(deleteButton);
        setButtonStyle(searchButton);
        setButtonStyle(clearButton);

        userTable = new JTable();
        userTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "First Name", "Last Name", "Email"}
        ));
        userTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(userTable);

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
        button.setBackground(new Color(0x007BFF));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
    }

    private Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        System.out.println("Connection established: " + (conn != null));
        return conn;
    }

    private void handleCreate() {
        String firstName = fNameField.getText();
        String lastName = lNameField.getText();
        String email = emailField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = getConnection()) {
            String query = "INSERT INTO users (first_name, last_name, email) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.executeUpdate();
            loadData();
            clearFields();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void handleUpdate() {
        try {
            int id = Integer.parseInt(searchField.getText());
            String firstName = fNameField.getText();
            String lastName = lNameField.getText();
            String email = emailField.getText();

            try (Connection conn = getConnection()) {
                String query = "UPDATE users SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, email);
                stmt.setInt(4, id);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    loadData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void handleDelete() {
        try {
            int id = Integer.parseInt(searchField.getText());

            try (Connection conn = getConnection()) {
                String query = "DELETE FROM users WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    loadData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void handleSearch() {
        try {
            int id = Integer.parseInt(searchField.getText());

            try (Connection conn = getConnection()) {
                String query = "SELECT * FROM users WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    fNameField.setText(rs.getString("first_name"));
                    lNameField.setText(rs.getString("last_name"));
                    emailField.setText(rs.getString("email"));
                } else {
                    JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void handleClear() {
        clearFields();
        loadData();
    }

    private void clearFields() {
        fNameField.setText("");
        lNameField.setText("");
        emailField.setText("");
        searchField.setText("");
    }

    private void loadData() {
        List<User> users = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                users.add(new User(id, firstName, lastName, email));
            }

            populateTable(users);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void populateTable(List<User> users) {
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);

        for (User user : users) {
            model.addRow(new Object[]{
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new View().setVisible(true));
    }
}

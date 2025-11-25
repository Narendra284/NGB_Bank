package ngb_bank;

import java.util.Scanner;
import java.sql.*;

public class BankMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("NGB Bank Management");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Delete Account");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            switch(choice) {
                case 1: createAccount(sc); break;
                case 2: deposit(sc); break;
                case 3: withdraw(sc); break;
                case 4: checkBalance(sc); break;
                case 5: deleteAccount(sc); break;
                case 6: System.exit(0);
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }

    // The following methods reference your databaseconnection class from earlier:

    private static void createAccount(Scanner sc) {
        try (Connection conn = databaseconnection.getConnection()) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Set password: ");
            String password = sc.nextLine();
            System.out.print("Initial deposit: ");
            double balance = sc.nextDouble();
            sc.nextLine();
            String sql = "INSERT INTO accounts(name, password, balance) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setDouble(3, balance);
            ps.executeUpdate();
            System.out.println("Account created successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deposit(Scanner sc) {
        try (Connection conn = databaseconnection.getConnection()) {
            System.out.print("Account number: ");
            int accountNo = sc.nextInt();
            System.out.print("Amount to deposit: ");
            double amount = sc.nextDouble();
            sc.nextLine();
            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, accountNo);
            int updated = ps.executeUpdate();
            if(updated > 0) {
                System.out.println("Deposit successful.");
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void withdraw(Scanner sc) {
        try (Connection conn = databaseconnection.getConnection()) {
            System.out.print("Account number: ");
            int accountNo = sc.nextInt();
            System.out.print("Amount to withdraw: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            String checkSql = "SELECT balance FROM accounts WHERE account_no = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, accountNo);
            ResultSet rs = checkPs.executeQuery();
            if(rs.next()) {
                double bal = rs.getDouble("balance");
                if(bal >= amount) {
                    String sql = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setDouble(1, amount);
                    ps.setInt(2, accountNo);
                    ps.executeUpdate();
                    System.out.println("Withdrawal successful.");
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void checkBalance(Scanner sc) {
        try (Connection conn = databaseconnection.getConnection()) {
            System.out.print("Account number: ");
            int accountNo = sc.nextInt();
            sc.nextLine();
            String sql = "SELECT balance FROM accounts WHERE account_no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                System.out.println("Current Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteAccount(Scanner sc) {
        try (Connection conn = databaseconnection.getConnection()) {
            System.out.print("Account number: ");
            int accountNo = sc.nextInt();
            sc.nextLine();
            String sql = "DELETE FROM accounts WHERE account_no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountNo);
            int deleted = ps.executeUpdate();
            if(deleted > 0) {
                System.out.println("Account deleted.");
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

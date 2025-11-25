
package ngb_bank;

public class account {
    private int accountNo;
    private String name;
    private String password;
    private double balance;

    public account(int accountNo, String name, String password, double balance) {
        this.accountNo = accountNo;
        this.name = name;
        this.password = password;
        this.balance = balance;
    }

    // Getters and setters for all fields
    public int getAccountNo() { return accountNo; }
    public void setAccountNo(int accountNo) { this.accountNo = accountNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}

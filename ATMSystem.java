import java.util.*;

class User {
    private String name;
    private String pin;
    private double balance;
    private List<String> history;

    public User(String name, String pin, double balance) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.history = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        balance += amount;
        history.add("Deposited: " + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            history.add("Withdrawn: " + amount);
            return true;
        } else {
            System.out.println("❌ Insufficient Balance!");
            return false;
        }
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("📜 Transaction History for " + name + ":");
            for (String record : history) {
                System.out.println("- " + record);
            }
        }
    }
}

public class ATMSystem {
    private static Map<String, User> users = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Pre-register some users
        users.put("1234", new User("Shashank", "1234", 1000.0));
        users.put("5678", new User("Aarav", "5678", 1500.0));

        System.out.println("===== Welcome to CodSoft ATM =====");

        int mainChoice;
        do {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Login");
            System.out.println("2. Register New User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            mainChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (mainChoice) {
                case 1 -> login();
                case 2 -> registerUser();
                case 3 -> System.out.println("👋 Thank you for visiting our ATM!");
                default -> System.out.println("❌ Invalid choice. Try again.");
            }

        } while (mainChoice != 3);
    }

    private static void registerUser() {
        System.out.println("\n===== NEW USER REGISTRATION =====");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Set a PIN: ");
        String pin = sc.nextLine();

        if (users.containsKey(pin)) {
            System.out.println("❌ This PIN is already taken. Try a different one.");
            return;
        }

        System.out.print("Enter initial deposit amount: ");
        double balance = sc.nextDouble();
        sc.nextLine();

        users.put(pin, new User(name, pin, balance));
        System.out.println("✅ User Registered Successfully! You can now login with your PIN.");
    }

    private static void login() {
        System.out.print("\nEnter your PIN: ");
        String pin = sc.nextLine();

        User currentUser = users.get(pin);
        if (currentUser == null) {
            System.out.println("❌ Invalid PIN! Access Denied.");
            return;
        }

        System.out.println("✅ Login Successful. Welcome, " + currentUser.getName() + "!");

        int choice;
        do {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transaction History");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> System.out.println("💰 Balance: " + currentUser.getBalance());
                case 2 -> {
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = sc.nextDouble();
                    currentUser.deposit(depositAmount);
                    System.out.println("✅ Deposited: " + depositAmount);
                }
                case 3 -> {
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = sc.nextDouble();
                    if (currentUser.withdraw(withdrawAmount)) {
                        System.out.println("✅ Withdrawn: " + withdrawAmount);
                    }
                }
                case 4 -> currentUser.showHistory();
                case 5 -> System.out.println("🔒 Logged out successfully.");
                default -> System.out.println("❌ Invalid choice, try again.");
            }

        } while (choice != 5);
    }
}

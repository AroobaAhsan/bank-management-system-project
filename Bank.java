import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Account implements Serializable {
    String name;
    int accountNumber;
    int pin;
    double amount;

    Account() {
        name = null;
        accountNumber = 0;
        pin = 0;
        amount = 0;
    }

    Account(String name, int acc, int pin, double amount) {
        this.name = name;
        this.accountNumber = acc;
        this.pin = pin;
        this.amount = 1000 + amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPIN() {
        return pin;
    }

    public void setPIN(int pin) {
        this.pin = pin;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

public class Bank {
    ArrayList<Account> accounts = new ArrayList<>();

    public void addNewRecord() {
        Scanner input = new Scanner(System.in);
        String name;
        boolean validName;
        
        do {
            System.out.print("\nEnter name of Account Holder: ");
            name = input.nextLine().toUpperCase();
            validName = name.matches("[A-Z ]+");
            if (!validName) {
                System.out.println("Invalid name. Please enter alphabets only.");
            }
        } while (!validName);

        Random random = new Random();
        int accountNumber = 10000000 + random.nextInt(90000000);
        System.out.println("Your Account Number is: " + accountNumber);

        int pin = 1000 + random.nextInt(9000);
        System.out.println("Your PIN is: " + pin);

        System.out.print("Enter the initial amount to be deposited: ");
        double initialAmount = input.nextDouble();

        Account account = new Account(name, accountNumber, pin, initialAmount);
        accounts.add(account);
        System.out.println("Account created successfully.");
    }

    public void deposit() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your 4-digit PIN: ");
        int pin = input.nextInt();

        Account account = findAccount(pin);
        if (account != null) {
            System.out.print("Enter the amount to be deposited: ");
            double amount = input.nextDouble();
            account.setAmount(account.getAmount() + amount);
            System.out.println("Deposit of " + amount + " successful!");
        } else {
            System.out.println("Account not found!");
        }
    }

    public void withdraw() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your 4-digit PIN: ");
        int pin = input.nextInt();

        Account account = findAccount(pin);
        if (account != null) {
            System.out.print("Enter the amount to be withdrawn: ");
            double amount = input.nextDouble();
            if (amount <= account.getAmount()) {
                account.setAmount(account.getAmount() - amount);
                System.out.println("Withdrawal of " + amount + " successful!");
            } else {
                System.out.println("Insufficient balance!");
            }
        } else {
            System.out.println("Account not found!");
        }
    }

    public void transfer() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your 4-digit PIN: ");
        int senderPin = input.nextInt();
        Account sender = findAccount(senderPin);

        if (sender != null) {
            System.out.print("Enter the recipient's 4-digit PIN: ");
            int receiverPin = input.nextInt();
            Account receiver = findAccount(receiverPin);

            if (receiver != null) {
                System.out.print("Enter the amount to transfer: ");
                double amount = input.nextDouble();
                if (amount <= sender.getAmount()) {
                    sender.setAmount(sender.getAmount() - amount);
                    receiver.setAmount(receiver.getAmount() + amount);
                    System.out.println("Transfer of " + amount + " successful!");
                } else {
                    System.out.println("Insufficient balance!");
                }
            } else {
                System.out.println("Recipient's account not found!");
            }
        } else {
            System.out.println("Sender's account not found!");
        }
    }

    public void deleteAccount() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your 4-digit PIN: ");
        int pin = input.nextInt();

        Account account = findAccount(pin);
        if (account != null) {
            accounts.remove(account);
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account not found!");
        }
    }

    public void searchAccount() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your 4-digit PIN: ");
        int pin = input.nextInt();

        Account account = findAccount(pin);
        if (account != null) {
            System.out.println("\nAccount found:");
            System.out.println("Name: " + account.getName());
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Balance: " + account.getAmount());
            System.out.println("PIN: " + account.getPIN() + "\n");
        } else {
            System.out.println("Account not found!");
        }
    }

    public void print() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
        } else {
            for (Account account : accounts) {
                System.out.println("\nName: " + account.getName());
                System.out.println("Account Number: " + account.getAccountNumber());
                System.out.println("Balance: " + account.getAmount() + "\n");
                System.out.println("PIN: " + account.getPIN() + "\n");
            }
        }
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream("BankRecord.ser");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(accounts);
            out.close();
            fos.close();
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public void load() {
        try {
            FileInputStream fis = new FileInputStream("BankRecord.ser");
            ObjectInputStream in = new ObjectInputStream(fis);
            accounts = (ArrayList<Account>) in.readObject();
            in.close();
            fis.close();
            System.out.println("Accounts loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

    private Account findAccount(int pin) {
        for (Account account : accounts) {
            if (account.getPIN() == pin) {
                return account;
            }
        }
        return null;
    }
    public void changePIN() {
    Scanner input = new Scanner(System.in);
    System.out.print("Enter your current 4-digit PIN: ");
    int currentPIN = input.nextInt();
    
    Account account = findAccount(currentPIN);
    if (account != null) {
        System.out.print("Enter your new 4-digit PIN: ");
        int newPIN = input.nextInt();
        account.setPIN(newPIN);
        System.out.println("PIN changed successfully!");
    } else {
        System.out.println("Account not found!");
    }
}


    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nBank Menu:");
                        System.out.println("1. Add new account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Delete account");
            System.out.println("6. Search account");
            System.out.println("7. Print all accounts");
            System.out.println("8. Save accounts");
            System.out.println("9. Load accounts");
            System.out.println("10. Change PIN"); // New option for changing PIN
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bank.addNewRecord();
                    break;
                case 2:
                    bank.deposit();
                    break;
                case 3:
                    bank.withdraw();
                    break;
                case 4:
                    bank.transfer();
                    break;
                case 5:
                    bank.deleteAccount();
                    break;
                case 6:
                    bank.searchAccount();
                    break;
                case 7:
                    bank.print();
                    break;
                case 8:
                    bank.save();
                    break;
                case 9:
                    bank.load();
                    break;
                case 10:
                    bank.changepin();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        } while (choice != 0);
    }
}





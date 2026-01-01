package test_package;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    static void printMenu() {
        System.out.println("\n==== Personal Bank Tracker ====");
        System.out.println("1. Add money");
        System.out.println("2. Remove money");
        System.out.println("3. View total balance");
        System.out.println("4. View transaction history");
        System.out.println("5. Exit");
    }

    static void addMoney(Scanner sc, ArrayList<Double> addedAmounts) {
        System.out.print("Enter amount to add: ");
        double amount = sc.nextDouble();
        addedAmounts.add(amount);
        System.out.println("Money added: ₹" + amount);
    }

    static void removeMoney(Scanner sc, ArrayList<Double> removedAmounts) {
        System.out.print("Enter amount to deduct: ");
        double amount = sc.nextDouble();
        removedAmounts.add(amount);
        System.out.println("Money removed: ₹" + amount);
    }

    static void viewTotalBalance(ArrayList<Double> addedAmounts, ArrayList<Double> removedAmounts) {
        double addedTotal = 0;
        for (double a : addedAmounts) addedTotal += a;

        double removedTotal = 0;
        for (double r : removedAmounts) removedTotal += r;

        System.out.println("Total Added: ₹" + addedTotal);
        System.out.println("Total Removed: ₹" + removedTotal);
        System.out.println("Current Balance: ₹" + (addedTotal - removedTotal));
    }

    static void viewHistory(ArrayList<Double> addedAmounts, ArrayList<Double> removedAmounts) {

        if (addedAmounts.isEmpty() && removedAmounts.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }

        System.out.println("\n--- Transaction History ---");

        if (!addedAmounts.isEmpty()) {
            System.out.println("Money Added:");
            for (int i = 0; i < addedAmounts.size(); i++) {
                System.out.println((i + 1) + ". ₹" + addedAmounts.get(i));
            }
        }

        if (!removedAmounts.isEmpty()) {
            System.out.println("Money Removed:");
            for (int i = 0; i < removedAmounts.size(); i++) {
                System.out.println((i + 1) + ". ₹" + removedAmounts.get(i));
            }
        }
    }

    static void saveToFile(ArrayList<Double> list, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (double value : list) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to " + filename);
        }
    }

    static void loadFromFile(ArrayList<Double> list, String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(Double.parseDouble(line));
            }
        } catch (Exception e) {
            System.out.println("Error loading from " + filename);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Double> addedAmounts = new ArrayList<>();
        ArrayList<Double> removedAmounts = new ArrayList<>();

        loadFromFile(addedAmounts, "added.txt");
        loadFromFile(removedAmounts, "removed.txt");

        int choice = 0;

        while (choice != 5) {
            printMenu();
            System.out.print("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addMoney(sc, addedAmounts);
                case 2 -> removeMoney(sc, removedAmounts);
                case 3 -> viewTotalBalance(addedAmounts, removedAmounts);
                case 4 -> viewHistory(addedAmounts, removedAmounts);
                case 5 -> {
                    saveToFile(addedAmounts, "added.txt");
                    saveToFile(removedAmounts, "removed.txt");
                    System.out.println("Data saved. Exiting program...");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}
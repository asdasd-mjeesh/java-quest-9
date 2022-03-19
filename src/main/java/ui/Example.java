package ui;

import dao.exception.DaoException;
import domain.store.Store;
import util.ConnectionManager;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Example {
    private final ExecuteManager executeManager;
    private final ShowManager showManager;

    public Example() {
        Store store = new Store();
        executeManager = new ExecuteManager(store);
        showManager = new ShowManager(store);
    }

    public static void main(String[] args) {
        Example asd = new Example();

        try {
            asd.run();
        } finally {
            ConnectionManager.closePool();
            System.out.println("program close");
        }
    }

    private void run() {
        Scanner in = new Scanner(System.in);
        String choice;
        String nameFilter;
        int costFilter;
        LocalDate dateFilter;

        boolean cycleIsRun = true;
        while (cycleIsRun) {
            showManager.showMenu();
            System.out.print("CHOICE:\t");
            choice = in.nextLine();

            try {
                switch (choice) {
                    case "a", "A" -> {
                        System.out.print("name:\t");
                        nameFilter = in.nextLine();
                        showManager.showProductsWithNameAndSortedByShelfLife(nameFilter);
                    }
                    case "b", "B" -> {
                        System.out.print("name:\t");
                        nameFilter = in.nextLine();
                        System.out.print("\nmax cost:\t");
                        costFilter = in.nextInt();
                        showManager.showProductsWithNameAndCostALess(nameFilter, costFilter);
                    }
                    case "c", "C" -> {
                        dateFilter = executeManager.createDate();
                        showManager.showProductWithShelfLifeAlong(dateFilter);
                    }
                    case "d", "D" -> showManager.showAllProductsSortedByPrice();
                    case "e", "E" -> showManager.showAllProducers();
                    case "f", "F" -> showManager.showAllProducersWithThemProducts();
                    case "1" -> showManager.showAll();
                    case "2" -> executeManager.addProduct();
                    case "3" -> {
                        System.out.print("id:\t");
                        long id = in.nextLong();
                        executeManager.deleteProduct(id);
                    }
                    case "0" -> cycleIsRun = false;

                    default -> {
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("was input invalid value");
            } catch (DaoException e) {
                System.out.println("input error. Was enter invalid value or item with this name is exist");
            }
        }
    }
}

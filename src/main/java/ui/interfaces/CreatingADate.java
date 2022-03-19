package ui.interfaces;

import ui.util.DateCreator;

import java.time.LocalDate;
import java.util.Scanner;

public interface CreatingADate {
    default LocalDate createDate() {
        Scanner in = new Scanner(System.in);

        System.out.println(" Введите срок годности продукта:");

        System.out.print("  Год:\t");
        int year = in.nextInt();
        System.out.print("  Месяц:\t");
        int mount = in.nextInt();
        System.out.print("  День:\t");
        int day = in.nextInt();

        return DateCreator.createDate(year, mount, day);
    }
}

package ui;

import domain.entity.ProducerEntity;
import domain.entity.ProductEntity;
import domain.store.Store;

import java.time.LocalDate;
import java.util.List;

public class ShowManager {
    private final Store store;

    public ShowManager(Store store) {
        this.store = store;
    }

    public void showAll() {
        List<ProductEntity> products = store.getProducts();
        for (ProductEntity product : products) {
            System.out.println(product);
        }
    }

    //a
    public void showProductsWithNameAndSortedByShelfLife(String name) {
        var toShow = store.getAllSortedByShelfLifeWithName(name);

        System.out.println("__________\n" +
                "Все продукты с названием \"" + name + "\", \n" +
                "отсортированные по сроку годности:");

        for (ProductEntity product : toShow) {
            System.out.println("-> " + product + "--");
        }

        System.out.println("__________");
    }

    //b
    public void showProductsWithNameAndCostALess(String name, int maxCost) {
        var toShow = store.getProductsWithNameAndCostALess(name, maxCost);

        System.out.println("__________\n" +
                "Все продукты с названием \"" + name + "\", \n" +
                "не превышающие стоимость " + maxCost + ":");

        for (ProductEntity product : toShow) {
            System.out.println("-> " + product + "--");
        }

        System.out.println("__________");
    }

    //c
    public void showProductWithShelfLifeAlong(LocalDate minShelfLife) {
        var toShow = store.getAllWithShelfLifeALong(minShelfLife);

        System.out.println("__________\n" +
                "Все продукты со сроком годности, \n" +
                "бОльшим, чем " + minShelfLife + ":");

        for (ProductEntity product : toShow) {
            System.out.println("-> " + product + "--");
        }

        System.out.println("__________");
    }

    //d
    public void showAllProductsSortedByPrice() {
        var toShow = store.getAllSortedByPrice();

        System.out.println("""
                __________
                Все продукты, отсортированные по параметру
                стоимость * количество""");

        for (ProductEntity product : toShow) {
            System.out.println("-> " + product + "--");
        }

        System.out.println("__________");
    }

    //e
    public void showAllProducers() {
        var producers = store.getProducers();
        System.out.println("_________\n" +
                "Все производители:");

        for (ProducerEntity producer : producers) {
            System.out.println("-> " + producer.getName());
        }

        System.out.println("__________");
    }

    //f
    public void showAllProducersWithThemProducts() {
        var producers = store.getProducers();

        System.out.println("__________");
        for (ProducerEntity producer : producers) {
            System.out.println(producer);
        }
    }

    public void showMenu() {
        System.out.println("""
                __________
                Меню:
                -> Показать все товары -- 1
                -> Добавить товар -- 2
                -> Удалить товар -- 3
                -> Список товаров с заданным названием, отсортированным по сроку годности -- A
                -> Список товаров с заданным названием и стоимостью, меньшей заданной -- B
                -> Список товаров, срок хранения которых больше заданного -- C
                -> Список товаров, упорядочанный по возрастанию цены(стоимость * количество) -- D
                -> Список производителей -- E
                -> Список производителей и продуктов, которые они производят -- F
                -> Выход -- 0
                __________""");
    }
}

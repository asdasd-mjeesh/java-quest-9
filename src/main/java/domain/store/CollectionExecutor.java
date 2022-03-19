package domain.store;

import dao.ProducerDao;
import dao.ProductDao;
import domain.entity.ProducerEntity;
import domain.entity.ProductEntity;
import dto.ProductFilter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface CollectionExecutor {

    default List<ProductEntity> getProducts() {
        return ProductDao.getInstance().findAll();
    }

    default List<ProducerEntity> getProducers() {
        return ProducerDao.getInstance().findAll();
    }

    // supported method for a and b
    default List<ProductEntity> getAllWithName(String name) {
        ProductFilter filterDto = new ProductFilter(
                99999, 0, name, null, null, null, null, null);

        return ProductDao.getInstance().findAll(filterDto);
    }

    // a
    default List<ProductEntity> getAllSortedByShelfLifeWithName(String name) {
        var productsWithName = getAllWithName(name);
        return productsWithName.stream()
                .sorted(Comparator.comparing(ProductEntity::getShelfLife).reversed())
                .collect(Collectors.toList());
    }

    // b
    default List<ProductEntity> getProductsWithNameAndCostALess(String name, int maxCost) {
        var productsWithName = getAllWithName(name);
        return productsWithName.stream()
                .filter(product -> product.getCost() <= maxCost)
                .collect(Collectors.toList());
    }

    // c
    default List<ProductEntity> getAllWithShelfLifeALong(LocalDate minShelfLife) {
        var products = getProducts();
        return products.stream()
                .filter(product -> product.getShelfLife().isAfter(minShelfLife))
                .collect(Collectors.toList());
    }

    // d
    default List<ProductEntity> getAllSortedByPrice() {
        var products = getProducts();
        return products.stream()
                .sorted(Comparator.comparing(ProductEntity::getPrice)
                        .thenComparing(ProductEntity::getCost))
                .collect(Collectors.toList());
    }
}

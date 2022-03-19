package dao;

import domain.entity.ProducerEntity;
import domain.entity.ProductEntity;
import dto.ProductFilter;

import java.sql.SQLException;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) throws SQLException {
        Test asd = new Test();
        asd.run();
    }

    private void run() {
        //testFindByIdProducer(9L);
        //testSaveProduct(9L);
        //testSaveProducer();
        //testFindByIdProducer(11L);
        //testFindAllProduct();
        //testFindByIdProducer(3L);

        //testFindAllProduct();

        //testUpdateProduct();
        //testDeleteProduct();

        //testFindByIdProducer(10L);

        //testUpdateProducer();
        //testDeleteProducer();

        /*
        var producerDao = ProducerDao.getInstance();
        var producerEntity = producerDao.findById(10L).get();
        System.out.println(producerEntity);
        producerEntity.getProducts().get(0).getProducer().setName("asasddadas");
        System.out.println(producerEntity);
        */

        /*
        var productDao = ProductDao.getInstance();
        var productEntity = productDao.findById(4L).get();
        System.out.println(productEntity.getProducer());
        productEntity.getProducer().setName("asdsad");
        System.out.println(productEntity.getProducer());
         */
    }

    // TODO PRODUCER_DAO_TEST
    private void testSaveProducer() {
        var producerDao = ProducerDao.getInstance();
        var producerEntity = new ProducerEntity();

        producerEntity.setName("test asd-11");
        producerEntity.setContact(656);

        producerDao.save(producerEntity);
    }

    private void testFindByIdProducer(Long id) {
        var producerDao = ProducerDao.getInstance();

        var result = producerDao.findById(id).get();
        System.out.println(result);
    }
    
    private void testFindAllProducer() {
        var producerDao = ProducerDao.getInstance();
        var result = producerDao.findAll();

        for (ProducerEntity producer : result) {
            System.out.println(producer);
        }
    }

    private void testUpdateProducer() {
        var producerDao = ProducerDao.getInstance();
        var producerEntity = producerDao.findById(3L).get();

        producerEntity.setName("Валерий Альбертович Шпак");
        producerDao.update(producerEntity);
    }

    private void testDeleteProducer() {
        var producerDao = ProducerDao.getInstance();
        producerDao.delete(9L);
    }

    // TODO PRODUCT_DAO_TEST

    private void testSaveProduct(Long producerId) {
        var productDao = ProductDao.getInstance();
        var productEntity = new ProductEntity();

        productEntity.setName("mjeesh");
        productEntity.setProducer(ProducerDao.getInstance().findById(producerId).orElse(null));
        productEntity.setCost(4);
        productEntity.setShelfLife(LocalDate.of(2034, 12, 31));
        productEntity.setCount(1);
        productEntity.setPrice(productEntity.getCost() * productEntity.getCount());

        productDao.save(productEntity);
    }

    private void testFindByIdProduct(Long id) {
        var productDao = ProductDao.getInstance();

        var result = productDao.findById(id).get();
        System.out.println(result);
    }

    private void testFindAllByFilterProduct() {
        var productFilter = new ProductFilter(
                3, 0, null, 10L, null,
                LocalDate.of(2020, 2, 2),
                null, null);
        var result = ProductDao.getInstance().findAll(productFilter);

        for (ProductEntity productEntity : result) {
            System.out.println(productEntity);
        }
    }

    private void testFindAllProduct() {
        var productDao = ProductDao.getInstance();
        var result = productDao.findAll();

        for (ProductEntity product : result) {
            System.out.println(product);
        }
    }

    private void testUpdateProduct() {
        var productDao = ProductDao.getInstance();
        var productEntity = productDao.findById(1L).get();
        productEntity.setName("asdasd_product");
        productDao.update(productEntity);
    }

    private void testDeleteProduct() {
        var productDao = ProductDao.getInstance();
        productDao.delete(1L);
    }
}

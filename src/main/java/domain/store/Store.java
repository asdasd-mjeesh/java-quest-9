package domain.store;

import dao.ProducerDao;
import dao.ProductDao;
import domain.entity.ProducerEntity;
import domain.entity.ProductEntity;

public class Store implements CollectionExecutor {
    private static final ProductDao productDao = ProductDao.getInstance();
    private static final ProducerDao producerDao = ProducerDao.getInstance();

    public Store() {  }

    public ProductEntity addProduct(ProductEntity product) {
        return productDao.save(product);
    }

    public boolean deleteProduct(Long id) {
        return productDao.delete(id);
    }

    public ProducerEntity addProducer(ProducerEntity producer) {
        return producerDao.save(producer);
    }

    public boolean deleteProducer(Long id) {
        return producerDao.delete(id);
    }
}

package domain.entity;

import dao.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class ProducerEntity {
    private Long id;
    private String name;
    private int contact;
    private List<ProductEntity> products;

    public ProducerEntity() {  }

    public ProducerEntity(Long id, String name, int contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    private void lazyInitialization() {
        if (products == null) {
            products = ProductDao.getInstance().findAll(id);
        }
        for (ProductEntity product : products) {
            product.setProducer(this);
        }
    }

    @Override
    public String toString() {
        lazyInitialization();
        List<String> productNames = new ArrayList<>(products.size());
        for (ProductEntity product : products) {
            productNames.add(product.getName());
        }
        return "id: " + id + "\n" +
                "name: " + name + "\n" +
                "contact: " + contact + "\n" +
                "products: " + productNames + "\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public List<ProductEntity> getProducts() {
        lazyInitialization();
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}

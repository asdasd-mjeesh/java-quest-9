package domain.entity;

import dao.ProducerDao;

import java.time.LocalDate;

public class ProductEntity {
    private Long id;
    private String name;
    private ProducerEntity producer;
    private Long producerId;
    private int cost;
    private LocalDate shelfLife;
    private int count;
    private int price;

    public ProductEntity() {  }

    public ProductEntity(String name, Long producerId, int cost, LocalDate shelfLife, int count) {
        this.name = name;
        this.producerId = producerId;
        this.cost = cost;
        this.shelfLife = shelfLife;
        this.count = count;
        this.price = cost * count;
    }

    public ProductEntity(Long id, String name, Long producerId, int cost, LocalDate shelfLife, int count) {
        this.id = id;
        this.name = name;
        this.producerId = producerId;
        this.cost = cost;
        this.shelfLife = shelfLife;
        this.count = count;
        this.price = cost * count;
    }

    private void lazyInitialization() {
        if (producer == null) {
            producer = ProducerDao.getInstance().findById(producerId).orElse(null);
        }
    }

    @Override
    public String toString() {
        lazyInitialization();
        return ("id: " + id + "\n" +
                        "name: " + name + "\n" +
                        "cost: " + cost + "\n" +
                        "shelf life: " + shelfLife + "\n" +
                        "count: " + count + "\n" +
                        "price: " + price + "\n" +
                        "producer: " + producer.getName() + "\n"
                );
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

    public ProducerEntity getProducer() {
        lazyInitialization();
        return producer;
    }

    public void setProducer(ProducerEntity producer) {
        this.producer = producer;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDate getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(LocalDate shelfLife) {
        this.shelfLife = shelfLife;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

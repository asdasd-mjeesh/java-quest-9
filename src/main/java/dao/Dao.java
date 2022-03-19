package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    E save(E entity);            // Create
    Optional<E> findById(K id);  // Read #1
    List<E> findAll();           // Read #2
    void update(E entity);       // Update
    boolean delete(K id);        // Delete
}

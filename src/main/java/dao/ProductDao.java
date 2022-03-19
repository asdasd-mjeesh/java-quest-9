package dao;

import dao.exception.DaoException;
import util.ConnectionManager;
import domain.entity.ProductEntity;
import dto.ProductFilter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class ProductDao implements Dao<Long, ProductEntity> {
    private static final ProductDao INSTANCE = new ProductDao();

    private static final String SAVE_SQL = """
            INSERT INTO product (name, producer_id, cost, shelf_life, count, price)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
               name,
               producer_id,
               cost,
               shelf_life,
               count,
               price
            FROM product
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String FIND_BY_PRODUCER_ID_SQL = FIND_ALL_SQL + """
            WHERE producer_id = ?;
            """;
    private static final String UPDATE_SQL = """
            UPDATE product
            SET name = ?,
                producer_id = ?,
                cost = ?,
                shelf_life = ?,
                count = ?,
                price = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM product
            WHERE id = ?
            """;

    private ProductDao() {  }

    @Override
    public ProductEntity save(ProductEntity entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getProducer().getId());
            statement.setInt(3, entity.getCost());
            statement.setDate(4, Date.valueOf(entity.getShelfLife()));
            statement.setInt(5, entity.getCount());
            statement.setInt(6, entity.getPrice());
            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong("id"));
            }
            return entity;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private ProductEntity buildEntity(ResultSet resultSet) throws SQLException {
        return new ProductEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getLong("producer_id"),
                resultSet.getInt("cost"),
                resultSet.getDate("shelf_life").toLocalDate(),
                resultSet.getInt("count")
        );
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);

            ProductEntity product = null;
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = buildEntity(resultSet);
            }
            return Optional.ofNullable(product);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<ProductEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();

            List<ProductEntity> persons = new ArrayList<>();
            while (resultSet.next()) {
                persons.add(buildEntity(resultSet));
            }
            return persons;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<ProductEntity> findAll(ProductFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();

        if (filter.name() != null) {
            whereSql.add("name LIKE ?");
            parameters.add("%" + filter.name() + "%");
        }
        if (filter.producer_id() != null) {
            whereSql.add("producer_id = ?");
            parameters.add(filter.producer_id());
        }
        if (filter.cost() != null) {
            whereSql.add("cost <= ?");
            parameters.add(filter.cost());
        }
        if (filter.shelf_life() != null) {
            whereSql.add("shelf_life >= ?");
            parameters.add(filter.shelf_life());
        }
        if (filter.count() != null) {
            whereSql.add("count >= ?");
            parameters.add(filter.count());
        }
        if (filter.price() != null) {
            whereSql.add("price <= ?");
            parameters.add(filter.price());
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        String where;
        if (filter.name() != null || filter.producer_id() != null) {
            where = whereSql.stream()
                    .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        } else {
            where = whereSql.stream()
                    .collect(joining(" AND ", "", " LIMIT ? OFFSET ? "));
        }
        String dynamicSql = FIND_ALL_SQL + where;

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(dynamicSql)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            //System.out.println(statement);
            var resultSet = statement.executeQuery();
            List<ProductEntity> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(buildEntity(resultSet));
            }
            return products;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<ProductEntity> findAll(Long producerId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_PRODUCER_ID_SQL)) {
            statement.setLong(1, producerId);

            var resultSet = statement.executeQuery();
            List<ProductEntity> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(buildEntity(resultSet));
            }
            return products;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void update(ProductEntity entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getProducer().getId());
            statement.setInt(3, entity.getCost());
            statement.setDate(4, Date.valueOf(entity.getShelfLife()));
            statement.setInt(5, entity.getCount());
            statement.setInt(6, entity.getPrice());
            statement.setLong(7, entity.getId());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}

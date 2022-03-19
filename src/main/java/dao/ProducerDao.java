package dao;

import dao.exception.DaoException;
import util.ConnectionManager;
import domain.entity.ProducerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerDao implements Dao<Long, ProducerEntity> {
    private static final ProducerDao INSTANCE = new ProducerDao();

    private static final String SAVE_SQL = """
            INSERT INTO producer (name, contact)
            VALUES (?, ?);
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
               name,
               contact
            FROM producer
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?""";

    private static final String  UPDATE_SQL = """
            UPDATE producer
            SET id = ?,
                name = ?,
                contact = ?
            WHERE id = ?
            """ ;

    private static final String DELETE_SQL = """
            DELETE FROM producer
            WHERE id = ?
            """;

    private ProducerDao() {  }

    @Override
    public ProducerEntity save(ProducerEntity entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getContact());
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

    private ProducerEntity buildEntity(ResultSet resultSet) throws SQLException {
        return new ProducerEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("contact")
        );
    }

    @Override
    public Optional<ProducerEntity> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);

            ProducerEntity producer = null;
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                producer = buildEntity(resultSet);
            }
            return Optional.ofNullable(producer);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<ProducerEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();

            List<ProducerEntity> producers = new ArrayList<>();
            while (resultSet.next()) {
                producers.add(buildEntity(resultSet));
            }
            return producers;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void update(ProducerEntity entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getContact());
            statement.setLong(4, entity.getId());

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

    public static ProducerDao getInstance() {
        return INSTANCE;
    }
}

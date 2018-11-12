package by.training.taskdao.dao.mysql.implementation;

import by.training.taskdao.dao.interfaces.ReaderDAO;
import by.training.taskdao.dao.mysql.config.ConfigurationManager;
import by.training.taskdao.dao.mysql.factory.MySQLDAOFactory;
import by.training.taskdao.entities.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access implementation class to the table reader in MySQL database
 * @author Anton Puhachou
 * */
public class MySQLReaderDAO implements ReaderDAO {

    /**
     * method of obtaining a specific {@link Reader} entity by id
     * @param id id of entity
     * @return {@link Reader} entity
     * @throws SQLException error close result set or connection
     */
    @Override
    public Reader get(int id) throws SQLException {
        Connection connection = MySQLDAOFactory.createConnection();
        ResultSet resultSet = null;
        Reader entity = null;
        if(connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(ConfigurationManager.getInstance().getMySQLQueryReaderGet());
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    entity = new Reader(resultSet.getInt(1),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getInt(4));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if(resultSet != null)
                    resultSet.close();
                connection.close();
            }
        }
        return entity;
    }

    /**
     * method of adding an {@link Reader} entity to the database
     * @param entity to add in database
     * @return id of added entity
     * @throws SQLException error close connection
     */
    @Override
    public int create(Reader entity) throws SQLException {
        Connection connection = MySQLDAOFactory.createConnection();
        int newId = -1;
        if(connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(ConfigurationManager.getInstance().getMySQLQueryReaderCreate());
                preparedStatement.setInt(1, entity.getId());
                preparedStatement.setString(2, entity.getLogin());
                preparedStatement.setString(3, entity.getPassword());
                preparedStatement.setInt(4, entity.getLanguageId());
                newId = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }
        return newId;
    }

    /**
     * method of updating an {@link Reader} entity in the database
     * @param entity to update in database
     * @return id of updated entity
     * @throws SQLException error close connection
     */
    @Override
    public int update(Reader entity) throws SQLException {
        Connection connection = MySQLDAOFactory.createConnection();
        int updatedId = -1;
        if(connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(ConfigurationManager.getInstance().getMySQLQueryReaderUpdate());
                preparedStatement.setString(1, entity.getLogin());
                preparedStatement.setString(2, entity.getPassword());
                preparedStatement.setInt(3, entity.getLanguageId());
                preparedStatement.setInt(4, entity.getId());
                updatedId = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }
        return updatedId;
    }

    /**
     * method of deleting an {@link Reader} entity in the database
     * @param id to delete in database
     * @return id of deleted entity
     * @throws SQLException error close connection
     */
    @Override
    public int delete(int id) throws SQLException {
        Connection connection = MySQLDAOFactory.createConnection();
        int deletedId = -1;
        if(connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(ConfigurationManager.getInstance().getMySQLQueryReaderDelete());
                preparedStatement.setInt(1, id);
                deletedId = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }
        return deletedId;
    }

    /**
     * method of getting all of {@link Reader} entities from the database
     * @return list of entities
     * @throws SQLException error close result set or connection
     */
    @Override
    public List<Reader> getAll() throws SQLException {
        Connection connection = MySQLDAOFactory.createConnection();
        ResultSet resultSet = null;
        List<Reader> entities = new ArrayList<>();
        if(connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(ConfigurationManager.getInstance().getMySQLQueryReaderGetAll());
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    entities.add(new Reader(resultSet.getInt(1),
                                            resultSet.getString(2),
                                            resultSet.getString(3),
                                            resultSet.getInt(4)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if(resultSet != null)
                    resultSet.close();
                connection.close();
            }
        }
        return entities;
    }
}

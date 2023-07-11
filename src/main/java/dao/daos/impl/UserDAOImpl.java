package dao.daos.impl;

import dao.daos.UserDAO;
import entity.User;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean add(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO user(userName, password) VALUES (?,?)");
    }

    @Override
    public boolean update(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE user SET userName = ?, password = ?, status = ?", dto.getUserName(), dto.getPassword(), dto.getStatus());
    }

    @Override
    public boolean delete(String userName) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM user WHERE userName = ?", userName);
    }

    @Override
    public User search(String userName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE userName = ?", userName);
        if (resultSet.next()) {
            return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
        }
        return null;
    }

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user");
        while (resultSet.next()) {
            users.add(new User(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
        }
        return users;
    }
}

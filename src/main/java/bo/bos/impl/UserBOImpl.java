package bo.bos.impl;

import bo.bos.UserBO;
import dao.DAOFactory;
import dao.daos.UserDAO;
import dto.UserDTO;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    //Dependency Injection
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.add(new User(dto.getUserName(), dto.getPassword()));
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(dto.getUserName(), dto.getPassword(), dto.getStatus()));
    }

    @Override
    public boolean deleteUser(String userName) throws SQLException, ClassNotFoundException {
        return userDAO.delete(userName);
    }

    @Override
    public UserDTO searchUser(String userName) throws SQLException, ClassNotFoundException {
        User user = userDAO.search(userName);
        return new UserDTO(user.getUserName(), user.getPassword(), user.getStatus());
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        ArrayList<User> users = userDAO.getAll();
        for (User user : users) {
            userDTOS.add(new UserDTO(user.getUserName(), user.getPassword(), user.getStatus()));
        }
        return userDTOS;
    }
}

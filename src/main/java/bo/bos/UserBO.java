package bo.bos;

import bo.SuperBO;
import dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {

    boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteUser(String userName) throws SQLException, ClassNotFoundException;

    UserDTO searchUser(String userName) throws SQLException, ClassNotFoundException;

    ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;
}

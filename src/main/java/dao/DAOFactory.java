package dao;

import dao.daos.impl.UserDAOImpl;

public class DAOFactory {

    private DAOFactory() {
    }

    private static DAOFactory daoFactory;

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {USER}

    public SuperDAO getDAO(DAOTypes daoTypes) {
        if (daoTypes == DAOTypes.USER) {
            return new UserDAOImpl();
        }
        return null;
    }
}

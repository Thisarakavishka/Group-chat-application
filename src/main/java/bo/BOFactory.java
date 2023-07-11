package bo;

import bo.bos.impl.UserBOImpl;

public class BOFactory {

    private BOFactory() {
    }

    private static BOFactory boFactory;

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {USER}

    public SuperBO getBO(BOTypes boTypes) {
        if (boTypes == BOTypes.USER) {
            return new UserBOImpl();
        }
        return null;
    }
}

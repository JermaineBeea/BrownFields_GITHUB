package za.co.wethinkcode.robots.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {
    private static final EntityManagerFactory EMF =
        Persistence.createEntityManagerFactory("robot_world_pu");

    private JpaUtil() {} // prevent instantiation

    public static EntityManager em() {
        return EMF.createEntityManager();
    }

    public static void close() {
        EMF.close();
    }
}


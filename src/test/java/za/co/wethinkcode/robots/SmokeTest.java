package za.co.wethinkcode.robots.persistence;

import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class SmokeTest {

    @Test
    void testEntityManagerSetup() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("robot_world_pu");
        EntityManager em = emf.createEntityManager();

        assertNotNull(em, "EntityManager should not be null — check persistence.xml config");

        em.close();
        emf.close();
    }
}


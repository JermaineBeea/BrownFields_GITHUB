package za.co.wethinkcode.robots.persistence;

import jakarta.persistence.EntityManager;
import java.util.List;
import za.co.wethinkcode.robots.domain.World;

public interface WorldDao {
    void saveWorld(EntityManager em, World world);
    World loadWorld(EntityManager em, String name);
    List<String> listWorldNames(EntityManager em);
    void deleteWorld(EntityManager em, String name);
}


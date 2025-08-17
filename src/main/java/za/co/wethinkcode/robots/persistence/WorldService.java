package za.co.wethinkcode.robots.persistence;

import jakarta.persistence.EntityManager;
import java.util.List;
import za.co.wethinkcode.robots.domain.World;

public class WorldService {
    private final WorldDao dao;

    public WorldService(WorldDao dao) {
        this.dao = dao;
    }

    public void save(World world) {
        EntityManager em = JpaUtil.em();
        try {
            em.getTransaction().begin();
            dao.saveWorld(em, world);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public World load(String name) {
        EntityManager em = JpaUtil.em();
        try {
            return dao.loadWorld(em, name);
        } finally {
            em.close();
        }
    }

    public List<String> list() {
        EntityManager em = JpaUtil.em();
        try {
            return dao.listWorldNames(em);
        } finally {
            em.close();
        }
    }

  public void delete(String name) {
        EntityManager em = JpaUtil.em();
        try {
            em.getTransaction().begin();
            dao.deleteWorld(em, name);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}


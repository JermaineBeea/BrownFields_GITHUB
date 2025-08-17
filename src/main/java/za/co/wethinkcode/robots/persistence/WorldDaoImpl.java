package za.co.wethinkcode.robots.persistence;

import za.co.wethinkcode.robots.domain.World;
import za.co.wethinkcode.robots.persistence.POJO.WorldDO;
import za.co.wethinkcode.robots.persistence.POJO.ObstacleDO;
import za.co.wethinkcode.robots.persistence.mapper.WorldMapper;

import javax.persistence.EntityManager;
import java.util.List;

public class WorldDaoImpl implements WorldDao {

    @Override
    public void saveWorld(EntityManager em, World world) {
        // Domain -> Persistence
        WorldDO worldDO = WorldMapper.toWorldDO(world);
        em.persist(worldDO);

        List<ObstacleDO> obstacleDOs = WorldMapper.toObstacleDOs(world);
        for (ObstacleDO obstacleDO : obstacleDOs) {
            em.persist(obstacleDO);
        }
    }

    @Override
    public World loadWorld(EntityManager em, String name) {
        WorldDO worldDO = em.find(WorldDO.class, name);
        if (worldDO == null) return null;

        List<ObstacleDO> obstacleDOs = em.createQuery(
                "SELECT o FROM ObstacleDO o WHERE o.worldName = :name", ObstacleDO.class)
                .setParameter("name", name)
                .getResultList();

        // Persistence -> Domain
        return WorldMapper.fromDO(worldDO, obstacleDOs);
    }

    @Override
    public List<String> listWorldNames(EntityManager em) {
        return em.createQuery("SELECT w.name FROM WorldDO w", String.class)
                 .getResultList();
    }

    @Override
    public void deleteWorld(EntityManager em, String name) {
        em.createQuery("DELETE FROM ObstacleDO o WHERE o.worldName = :name")
          .setParameter("name", name)
          .executeUpdate();

        WorldDO worldDO = em.find(WorldDO.class, name);
        if (worldDO != null) {
            em.remove(worldDO);
        }
    }
}

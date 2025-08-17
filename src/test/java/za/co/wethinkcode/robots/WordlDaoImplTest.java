package za.co.wethinkcode.robots;

import org.junit.jupiter.api.*;
import za.co.wethinkcode.robots.domain.*;
import za.co.wethinkcode.robots.persistence.WorldDao;
import za.co.wethinkcode.robots.persistence.WorldDaoImpl;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WorldDaoImplTest {
    private WorldDao dao;

    @BeforeEach
    void setup() {
        dao = new WorldDaoImpl("jdbc:sqlite::memory:");
    }

    @Test
    void testSaveAndLoadWorld() {
        DomainWorld world = new DomainWorld("testworld", 10, 10,
                Arrays.asList(new Obstacle(1, 2, 3, 4)));
        dao.saveWorld(world);

        DomainWorld loaded = dao.loadWorld("testworld");
        assertNotNull(loaded);
        assertEquals("testworld", loaded.getName());
        assertEquals(10, loaded.getWidth());
        assertEquals(10, loaded.getHeight());
        assertEquals(1, loaded.getObstacles().size());
    }

    @Test
    void testDeleteWorld() {
        DomainWorld world = new DomainWorld("todelete", 5, 5,
                Arrays.asList(new Obstacle(0, 0, 1, 1)));
        dao.saveWorld(world);
        dao.deleteWorld("todelete");
        assertNull(dao.loadWorld("todelete"));
    }

    @Test
    void testRestoreWorld() {
        DomainWorld original = new DomainWorld("restoreworld", 8, 8,
                Arrays.asList(new Obstacle(2, 2, 3, 3), new Obstacle(4, 4, 5, 5)));
        dao.saveWorld(original);

        DomainWorld restored = dao.loadWorld("restoreworld");
        assertNotNull(restored);
        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getWidth(), restored.getWidth());
        assertEquals(original.getHeight(), restored.getHeight());
        assertEquals(original.getObstacles().size(), restored.getObstacles().size());
        for (int i = 0; i < original.getObstacles().size(); i++) {
            Obstacle o1 = original.getObstacles().get(i);
            Obstacle o2 = restored.getObstacles().get(i);
            assertEquals(o1.getX(), o2.getX());
            assertEquals(o1.getY(), o2.getY());
            assertEquals(o1.getWidth(), o2.getWidth());
            assertEquals(o1.getHeight(), o2.getHeight());
        }
    }

    @Test
    void testSaveAndRestoreManyWorlds() {
        DomainWorld world1 = new DomainWorld("world1", 6, 6,
                Arrays.asList(new Obstacle(1, 1, 2, 2)));
        DomainWorld world2 = new DomainWorld("world2", 7, 7,
                Arrays.asList(new Obstacle(3, 3, 4, 4), new Obstacle(5, 5, 1, 1)));
        DomainWorld world3 = new DomainWorld("world3", 9, 9,
                Arrays.asList());

        dao.saveWorld(world1);
        dao.saveWorld(world2);
        dao.saveWorld(world3);

        DomainWorld loaded1 = dao.loadWorld("world1");
        DomainWorld loaded2 = dao.loadWorld("world2");
        DomainWorld loaded3 = dao.loadWorld("world3");

        assertNotNull(loaded1);
        assertEquals("world1", loaded1.getName());
        assertEquals(1, loaded1.getObstacles().size());

        assertNotNull(loaded2);
        assertEquals("world2", loaded2.getName());
        assertEquals(2, loaded2.getObstacles().size());

        assertNotNull(loaded3);
        assertEquals("world3", loaded3.getName());
        assertEquals(0, loaded3.getObstacles().size());
    }
}
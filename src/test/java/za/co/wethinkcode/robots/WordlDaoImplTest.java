//package za.co.wethinkcode.robots;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.util.Arrays;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import za.co.wethinkcode.robots.domain.Obstacle;
//import za.co.wethinkcode.robots.domain.World;
//import za.co.wethinkcode.robots.persistence.WorldDao;
//import za.co.wethinkcode.robots.persistence.WorldDaoImpl;
//
//class WorldDaoImplTest {
//    private WorldDao dao;
//
//    @BeforeEach
//    void setup() {
//        dao = new WorldDaoImpl("jdbc:sqlite::memory:");
//    }
//
//    @Test
//    void testSaveAndLoadWorld() {
//        World world = new World("testworld", 10, 10,
//                Arrays.asList(new Obstacle(1, 2, 3, 4)));
//        dao.saveWorld(world);
//
//        World loaded = dao.loadWorld("testworld");
//        assertNotNull(loaded);
//        assertEquals("testworld", loaded.getName());
//        assertEquals(10, loaded.getWidth());
//        assertEquals(10, loaded.getHeight());
//        assertEquals(1, loaded.getObstacles().size());
//    }
//
//    @Test
//    void testDeleteWorld() {
//        World world = new World("todelete", 5, 5,
//                Arrays.asList(new Obstacle(0, 0, 1, 1)));
//        dao.saveWorld(world);
//        dao.deleteWorld("todelete");
//        assertNull(dao.loadWorld("todelete"));
//    }
//
//    @Test
//    void testRestoreWorld() {
//        World original = new World("restoreworld", 8, 8,
//                Arrays.asList(new Obstacle(2, 2, 3, 3), new Obstacle(4, 4, 5, 5)));
//        dao.saveWorld(original);
//
//        World restored = dao.loadWorld("restoreworld");
//        assertNotNull(restored);
//        assertEquals(original.getName(), restored.getName());
//        assertEquals(original.getWidth(), restored.getWidth());
//        assertEquals(original.getHeight(), restored.getHeight());
//        assertEquals(original.getObstacles().size(), restored.getObstacles().size());
//        for (int i = 0; i < original.getObstacles().size(); i++) {
//            Obstacle o1 = original.getObstacles().get(i);
//            Obstacle o2 = restored.getObstacles().get(i);
//            assertEquals(o1.getX(), o2.getX());
//            assertEquals(o1.getY(), o2.getY());
//            assertEquals(o1.getWidth(), o2.getWidth());
//            assertEquals(o1.getHeight(), o2.getHeight());
//        }
//    }
//
//    @Test
//    void testSaveAndRestoreManyWorlds() {
//        World world1 = new World("world1", 6, 6,
//                Arrays.asList(new Obstacle(1, 1, 2, 2)));
//        World world2 = new World("world2", 7, 7,
//                Arrays.asList(new Obstacle(3, 3, 4, 4), new Obstacle(5, 5, 1, 1)));
//        World world3 = new World("world3", 9, 9,
//                Arrays.asList());
//
//        dao.saveWorld(world1);
//        dao.saveWorld(world2);
//        dao.saveWorld(world3);
//
//        World loaded1 = dao.loadWorld("world1");
//        World loaded2 = dao.loadWorld("world2");
//        World loaded3 = dao.loadWorld("world3");
//
//        assertNotNull(loaded1);
//        assertEquals("world1", loaded1.getName());
//        assertEquals(1, loaded1.getObstacles().size());
//
//        assertNotNull(loaded2);
//        assertEquals("world2", loaded2.getName());
//        assertEquals(2, loaded2.getObstacles().size());
//
//        assertNotNull(loaded3);
//        assertEquals("world3", loaded3.getName());
//        assertEquals(0, loaded3.getObstacles().size());
//    }
//
//    @Test
//    public void testExecuteSavesWorldAndPrintsMessage() {
//        // Arrange
//        World mockWorld = mock(World.class);
//        ((Object) when(mockWorld.getName())).thenReturn("TestWorld");
//
//        // Capture System.out
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        Object command;
//        // Act
//        command.execute(mockWorld);
//        Object mockDao;
//        // Assert
//        verify(mockDao, times(1)).saveWorld(mockWorld);
//        assertTrue(outContent.toString().contains("World 'TestWorld' saved."));
//
//        // Reset System.out
//        System.setOut(System.out);
//    }
//
//    private WorldDao verify(Object mockDao, Object times) {
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'verify'");
//    }
//
//    private Object times(int i) {
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'times'");
//    }
//
//    private Object when(String name) {
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'when'");
//    }
//
//    private World mock(Class<World> class1) {
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'mock'");
//    }
//
//}
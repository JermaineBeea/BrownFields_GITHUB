package za.co.wethinkcode.robots;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import za.co.wethinkcode.robots.webapi.RobotWorldApiServer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class RobotWorldApiServerTest {
    private static RobotWorldApiServer server;

    @BeforeAll
    public static void startServer() {
        server = new RobotWorldApiServer();
        server.start(7000);
    }

    @AfterAll
    public static void stopServer() {
        server.stop();
    }

    @Test
    public void getWorldReturnsJson() {
        HttpResponse<String> response = Unirest.get("http://localhost:7000/world").asString();
        assertEquals(200, response.getStatus());
        assertTrue(response.getBody().contains("world"));
    }
}
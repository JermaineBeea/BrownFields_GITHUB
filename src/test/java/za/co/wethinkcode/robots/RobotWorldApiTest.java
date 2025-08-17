package za.co.wethinkcode.robots;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.*;
import za.co.wethinkcode.robots.webapi.RobotWorldApiServer;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class RobotWorldApiTest {

    @BeforeAll
    public static void startServer() {
        RobotWorldApiServer.main(new String[]{});
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    @Test
    @Order(1)
    public void testLaunchRobot() {
        String body = "{\"command\":\"launch\"}";
        HttpResponse<String> response = Unirest.post("http://localhost:7000/robot/testbot")
                .header("Content-Type", "application/json")
                .body(body)
                .asString();
        assertEquals(200, response.getStatus());
        assertTrue(response.getBody().contains("launched"));
    }

    @Test
    @Order(2)
    public void testLookCommand() {
        String body = "{\"command\":\"look\"}";
        HttpResponse<String> response = Unirest.post("http://localhost:7000/robot/testbot")
                .header("Content-Type", "application/json")
                .body(body)
                .asString();
        assertEquals(200, response.getStatus());
        assertTrue(response.getBody().contains("Looked"));
    }

    @Test
    @Order(3)
    public void testInvalidCommand() {
        String body = "{\"command\":\"invalid\"}";
        HttpResponse<String> response = Unirest.post("http://localhost:7000/robot/testbot")
                .header("Content-Type", "application/json")
                .body(body)
                .asString();
        assertEquals(400, response.getStatus());
        assertTrue(response.getBody().contains("Unknown command"));
    }
}
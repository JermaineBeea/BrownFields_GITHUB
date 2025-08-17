package za.co.wethinkcode.robots.AcceptanceTests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import za.co.wethinkcode.robots.server.RobotWorldClient;
import za.co.wethinkcode.robots.server.RobotWorldJsonClient;

/**
 * Tests for the "state" command in the Robot World Protocol.
 */
class RobotStateTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    /**
     * Scenario: The robot exists in the world.
     * Should return the robot's state.
     */
    @Test
    void getState_whenRobotExists_returnsRobotState() {
        //TODO [Shannon Keating] - Implement this test
    }

    /**
     * Scenario: The robot is not in the world.
     * Should return an error.
     */
    // @Test
    // void getState_whenRobotDoesNotExist_returnsError() {
    //     assertTrue(serverClient.isConnected());

    //     String request = "{" +
    //             "\"robot\": \"Bot\"," +
    //             "\"command\" : \"state\"," +
    //             "\"arguments\" : []" +
    //             "}";

    //     JsonNode response = serverClient.sendRequest(request);

    //     assertNotNull(response, "Response should not be null");
        

    //     assertEquals("ERROR", response.get("request").asText(), "Should return ERROR for non-existent robot");
    //     assertNotNull(response.get("data").get("message"), "Error message should be present");

    //     assertTrue(response.get("data").get("message").asText().toLowerCase().contains("no such robot"), "Error message should indicate missing robot");
    //     assertNull(response.get("state"), "No state should be returned for invalid robot");


    @Test
    void getStateWhenRobotDoesNotExist(){

        // Attempt to launch a second robot
        String secondLaunch = "{" +
                "\"robot\": \"EVE\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",5,5]" +
                "}";
        JsonNode secondResponse = serverClient.sendRequest(secondLaunch);

        // Must be ERROR with exact message
        assertEquals("ERROR", secondResponse.get("result").asText());
        Assertions.assertNotNull(secondResponse.get("data"));
        assertEquals("No more space in this world", secondResponse.get("data").get("message").asText());
        // No state field expected
        assertNull(secondResponse.get("state"));
    }
    }

package za.co.wethinkcode.robots.AcceptanceTests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.RobotWorldClient;
import za.co.wethinkcode.robots.handlers.VisibilityHandler;
import za.co.wethinkcode.robots.server.RobotWorldClient;
import za.co.wethinkcode.robots.server.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Acceptance test for the "look" command in the Robot World Protocol.
 */
class LookAroundTests {
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



    @Test
    void lookWhenWorldIsEmptyShouldReturnError() {
        //TODO [Khumo Tsoeu] - Implement this test
         // Given I am connected to the Robot World server
         assertTrue(serverClient.isConnected());
        
         // And no robots have been launched (world is empty)
     
         // When I send a "look" command from a robot that doesn't exist
         String request = "{" +
                 "  \"robot\": \"HAL\"," +
                 "  \"command\": \"look\"" +
                 "}";
     
         JsonNode response = serverClient.sendRequest(request);
     
         // Then the server should return an error
         assertNotNull(response.get("result"), "Response missing 'result' field.");
         assertEquals("ERROR", response.get("result").asText(), "Expected ERROR result for non-existent robot.");
     
         // And the error message should mention that the robot does not exist or is not launched
         assertNotNull(response.get("data"), "Response missing 'data' field.");
         assertNotNull(response.get("data").get("message"), "Response missing error message.");
         String message = response.get("data").get("message").asText().toLowerCase();
         assertTrue(message.contains("not launched") || message.contains("does not exist"),
                 "Expected error message about robot not being launched, got: " + message);
     }
     
    @Test
    void lookForNonExistentRobotShouldReturnError() {
        //TODO [Gomolemo Precious Ntwae] - Implement this test

        // Arrange
//        RobotWorldClient robotService = new RobotWorldClient() {
//            @Override
//            public void connect(String ipAddress, int port) {
//
//            }
//
//            @Override
//            public boolean isConnected() {
//                return false;
//            }
//
//            @Override
//            public void disconnect() {
//
//            }
//
//            @Override
//            public JsonNode sendRequest(String requestJsonString) {
//                return null;
//            }
//
//            @Override
//            public String sendRequestAsString(String requestString) {
//                return "";
//            }
//        };
//        String nonExistentRobotId = "robot-123";
//
//        // Act & Assert
//        Exception exception = assertThrows(RobotNotFoundException.class, () -> {
//            robotService.findRobotById(nonExistentRobotId);
//        });
//
//        // Verify the error message
//        String expectedMessage = "Robot with ID robot-123 not found";
//        assertEquals(expectedMessage, exception.getMessage());
//            }
//        }
//
//
//
//
//    }

        
        assertTrue(serverClient.isConnected());

        String request = "{" +
                "\"robot\": \"Phumi\"," +
                "\"command\" : \"look\"," +
                "\"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Should return an error result
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());
        assertNotNull(response.get("data"));
        assertTrue(response.get("data").get("message").asText().toLowerCase().contains("not found")
                || response.get("data").get("message").asText().toLowerCase().contains("does not exist"));
    }

//     @Test
//     void lookForNonExistentRobotShouldReturnError() {
//         String lookRequest = "{" +
//                 "\"robot\": \"DOES_NOT_EXIST\"," +
//                 "\"command\": \"look\"," +
//                 "\"arguments\": []" +
//                 "}";
//         JsonNode response = serverClient.sendRequest(lookRequest);

//         // Should return an error result
//         assertNotNull(response.get("result"));
//         assertEquals("ERROR", response.get("result").asText());
//         assertNotNull(response.get("data"));
//         assertTrue(response.get("data").get("message").asText().toLowerCase().contains("not found")
//                 || response.get("data").get("message").asText().toLowerCase().contains("does not exist"));
//     }
    
    @Test
    void lookAfterLaunchShouldReturnProtocolCompliantResponse() {
        // Launch a robot
        String launchRequest = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",5,5]" +
                "}";
        serverClient.sendRequest(launchRequest);
    
        // Request look
        String lookRequest = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"look\"," +
                "\"arguments\": []" +
                "}";
        JsonNode lookResponse = serverClient.sendRequest(lookRequest);
    
        // Check protocol compliance
        assertNotNull(lookResponse.get("result"));
        assertEquals("OK", lookResponse.get("result").asText());
    
        assertNotNull(lookResponse.get("data"));
        assertTrue(lookResponse.get("data").has("objects"));
        assertTrue(lookResponse.get("data").get("objects").isArray());
    
        assertNotNull(lookResponse.get("state"));
        assertTrue(lookResponse.get("state").has("position"));
        assertTrue(lookResponse.get("state").has("direction"));
        assertTrue(lookResponse.get("state").has("shields"));
        assertTrue(lookResponse.get("state").has("shots"));
        assertTrue(lookResponse.get("state").has("status"));
    }

}
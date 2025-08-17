package za.co.wethinkcode.robots.AcceptanceTests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import za.co.wethinkcode.robots.server.RobotWorldClient;
import za.co.wethinkcode.robots.server.RobotWorldJsonClient;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class LaunchRobotTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }
  
    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }
    @Test
    void validLaunchShouldSucceed(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        // assertEquals(0, response.get("data").get("position").get(0).asInt());
        // assertEquals(0, response.get("data").get("position").get(1).asInt());
        assertTrue(response.get("data").get("position").get(0).isInt());
        assertTrue(response.get("data").get("position").get(1).isInt());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
    }
    @Test
    void invalidLaunchShouldFail(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"HAL\"," + 
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }


    
    @Test
    void launchShouldFailWhenRobotNameExists() {
        // Launch the first robot
        String firstLaunch = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",5,5]" +
                "}";
        JsonNode firstResponse = serverClient.sendRequest(firstLaunch);
        assertEquals("OK", firstResponse.get("result").asText());
    
        // Attempt to launch another robot with the same name
        String duplicateLaunch = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",5,5]" +
                "}";
        JsonNode duplicateResponse = serverClient.sendRequest(duplicateLaunch);
    
        // Must be ERROR with exact message
        assertEquals("ERROR", duplicateResponse.get("result").asText());
        assertNotNull(duplicateResponse.get("data"));
        assertEquals("Too many of you in this world", duplicateResponse.get("data").get("message").asText());
        // No state field expected
        assertNull(duplicateResponse.get("state"));
    }

    @Test
    void LaunchAnotherRobot_shouldSucceed(){
        //Given a world of size 2x2
        //And robot HAL has already been launched into the wrold

        String firstLaunch = "{" +
        "\"robot\": \"HAL\"," +
        "\"command\": \"launch\"," +
        "\"arguments\": [\"Tank\", \"5\", \"5\"]" +
        "}";
        JsonNode firstResponse = serverClient.sendRequest(firstLaunch);
        assertEquals("OK", firstResponse.get("result").asText());

       
        String secondLaunch = "{" +
        "\"robot\": \"R2D2\"," +
        "\"command\": \"launch\"," +
        "\"arguments\": [\"Sniper\", \"5\", \"5\"]" +
        "}";
        JsonNode secondResponse = serverClient.sendRequest(secondLaunch);
        assertEquals("OK", secondResponse.get("result").asText());

         
        assertNotNull(firstResponse.get("data").get("position"));
        assertNotNull(secondResponse.get("data").get("position")); 
        
        assertNotEquals(
            firstResponse.get("data").get("position"),
            secondResponse.get("data").get("position"),
            "Robots should appear at different positions"
        );
 


    }

    @Test
    void launchRobot_WorldWithoutObstaclesIsFull(){
        String[] robotNames = {"HAL", "R2D2", "TERMINATOR", "BOB", "ROBOCOP", "BENDER", "ROM", "CRPO", "ROOMBA"};

        for (String name : robotNames){
            String launchCommand = String.format(
                    "{" +
                            "\"robot\": \"%s\"," +
                            "\"command\": \"launch\"," +
                            "\"arguments\": [\"Tank\", \"5\", \"5\"]" +
                            "}",
                    name

            );
            JsonNode response = serverClient.sendRequest(launchCommand);
            System.out.println("Response for " + name + ": " + response.toString());
            assertEquals("OK", response.get("result").asText());
        }

        String overflowlaunch =  "{" +
                "\"robot\": \"Optimus\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"Sniper\", \"5\", \"5\"]" +
                "}";
        JsonNode errorResponse = serverClient.sendRequest(overflowlaunch);

        assertEquals("ERROR", errorResponse.get("result").asText());
        assertEquals(
                "No more space in this world",
                errorResponse.get("data").get("message").asText()
        );

    }

    @Test 
    void launchRobotsIntoWorldWithObstacle(){
        assertTrue(serverClient.isConnected());

        for(int i = 0; i < 2; i++){
            String robotName = "Robot" + i;
            String request = "{" +
                "  \"robot\": \"" + robotName  + "\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
            JsonNode response = serverClient.sendRequest(request);
            
            assertNotNull(response.get("result"));

            assertEquals("ERROR", response.get("result").asText());
            assertNotNull(response.get("message"));
            assertEquals("Failed to launch" + robotName + " because it hit an obstacle", response.get("message").asText());
        }
    }

// case HitObstacle -> new Response("ERROR", "Failed to launch " + command.robot.getName() + " because it hit an obstacle");

    @Test
    void launchRobotsIntoWorldWithObstacle_noSpaceError(){

        assertTrue(serverClient.isConnected());


        for(int i = 0; i < 2; i++){
            String robotName = "Robot" + i;
            String request = "{" +
                "  \"robot\": \"" + robotName  + "\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

            JsonNode response = serverClient.sendRequest(request);
            assertEquals("OK", response.get("result").asText());
        }


    String ninthRobotRequest = "{" +
            "  \"robot\": \"Robot9\"," +
            "  \"command\": \"launch\"," +
            "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
            "}";
    JsonNode ninthRobotResponse = serverClient.sendRequest(ninthRobotRequest);

    assertNotNull(ninthRobotResponse.get("result"));
    assertEquals("ERROR", ninthRobotResponse.get("result").asText());

    assertNotNull(ninthRobotResponse.get("data"));
    assertNotNull(ninthRobotResponse.get("data").get("message"));
    assertEquals("ERROR: Cannot launch more than 2 robots.", ninthRobotResponse.get("data").get("messsage").asText());

    assertNull(ninthRobotResponse.get("state"));

    }

}


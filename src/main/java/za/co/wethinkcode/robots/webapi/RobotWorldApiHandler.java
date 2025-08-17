package za.co.wethinkcode.robots.webapi;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import za.co.wethinkcode.robots.domain.World;
import za.co.wethinkcode.robots.persistence.WorldDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class RobotWorldApiHandler {
    private static final WorldDaoImpl dao = new WorldDaoImpl( "jdbc:sqlite:robots.db");
    private static final ObjectMapper mapper = new ObjectMapper();

   public static void getCurrentWorld(Context ctx) {
        // Replace with actual logic to get the current world
        World world = dao.restoreWorld("default");
        if (world == null) {
            ctx.status(HttpCode.NOT_FOUND).result("World not found");
        } else {
            ctx.json(world);
        }
    }
    public static void getNamedWorld(Context ctx) {
        String name = ctx.pathParam("name");
        World world = dao.restoreWorld(name);
        if (world == null) {
            ctx.status(HttpCode.NOT_FOUND).result("World not found");
        } else {
            ctx.json(world);
        }
    }

        public void robotCommand(Context ctx) {
        String robotName = ctx.pathParam("name");
        try {
            Map<String, Object> command = mapper.readValue(ctx.body(), Map.class);
            String cmd = (String) command.get("command");
            Map<String, Object> response;

            switch (cmd) {
                case "launch":
                    // TODO: Call your domain logic to launch a robot
                    response = Map.of(
                        "result", "OK",
                        "data", Map.of("message", "Robot " + robotName + " launched")
                    );
                    ctx.status(200).json(response);
                    break;
                case "look":
                    // TODO: Call your domain logic to perform look
                    response = Map.of(
                        "result", "OK",
                        "data", Map.of("message", "Looked around")
                    );
                    ctx.status(200).json(response);
                    break;
                default:
                    ctx.status(400).json(Map.of("error", "Unknown command"));
            }
        } catch (Exception e) {
            ctx.status(400).json(Map.of("error", "Invalid request"));
        }
    }

    public static void launchRobot(Context ctx) {
        String robotName = ctx.pathParam("name");
    }
}
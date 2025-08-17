 package za.co.wethinkcode.robots.commands;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.robots.domain.Robot;

import java.util.Arrays;

/**
 * Abstract representation of a command sent to robots.
 * Defines interface and common behavior for all commands.
 */
public abstract class Command {
    public Robot robot;
    public String[] arguments;

    public Command(Robot robot, String[] arguments) {
        this.robot = robot;
        this.arguments = arguments;
    }

    public static boolean isValidCommand(String command) {
        return switch (command.toLowerCase()) {
            case "forward", "back", "turn", "look", "state", "launch", "dump", "orientation", "shutdown",
                 "disconnect", "fire", "repair", "reload", "help" -> true;
            default -> false;
        };
    }

    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("command", commandName().toLowerCase());
        json.put("arguments", arguments);

        if (robot != null) {
            json.put("robot", robot.getName());
        }

        return json.toString(); 
    }

    public static Command fromJSON(JSONObject json) {
        String command = json.getString("command").toLowerCase();
        if (command.equals("disconnect")) {
            return new DisconnectCommand(); // handle disconnect command separately
        }

        String robotName = json.getString("robot");
        JSONArray jsonArgs = json.getJSONArray("arguments");
        String[] args = toStringArray(jsonArgs);
        Robot robot = new Robot(robotName);
       

        return switch (command) {
            case "repair" -> new RepairCommand(robot, args);
            case "reload" -> new ReloadCommand(robot, args);
            case "turn" -> new TurnCommand(robot, args);
            case "fire" -> new FireCommand(robot, args);


            case "forward" -> new MoveCommand(robot, "forward", args);
            case "back" -> new MoveCommand(robot, "back", args);


            case "help" -> new HelpCommand(robot, new String[]{});
            case "dump" -> new DumpCommand(robot, new String[]{});
            case "look" -> new LookCommand(robot, new String[]{});
            case "state" -> new StateCommand(robot, new String[]{});


            case "shutdown" -> new ShutdownCommand(robot, new String[0]);
            case "off" -> new ShutdownCommand(robot, new String[0]);

             case "Orientation" -> new OrientationCommand(robot);
        
            case "launch" -> {
                if (args.length <1) throw new IllegalArgumentException("Missing robot type");
                yield new LaunchCommand(new Robot(robotName, args[0]), args);
            }

           
            default -> throw new IllegalArgumentException("Unkown command: " + command);
                };
    }

    private static String [] toStringArray(JSONArray jsonArray){
        String[] array = new String[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++){
            array[i] = jsonArray.getString(i);
        }
        return array;

    }
    

    public static Command fromInput(String input, String robotName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid or empty command");
            
        }

        String[] tokens = input.trim().split(" ");
        String command = tokens[0].toLowerCase();
        String robot = robotName;
        String[] args = {};

        switch (command) {
            case "forward":
            case "back":

            robot = tokens.length >= 3 ? tokens[1]:robotName;
            String distance = tokens.length >=3 ? tokens[2]:
                            tokens.length >=2 ? tokens[1] : "0";
            args = new String[]{command, robot, distance};
            break;
              
            case "turn":
                robot = tokens.length >= 3 ? tokens[1] : robotName;
                String direction = tokens.length >= 3 ? tokens[2] :
                                tokens.length >= 2 ? tokens [1] : "";
                args = direction.isEmpty()? new String[]{} : new String[]{direction};
                break;
               
            case "state":
            case "look":
            case "orientation":
            case "fire":
            case "repair":
            case "reload":
            case "off":
                robot = tokens.length > 1 ? tokens[1] : robotName;
                args = new String[]{};
                break;
            case "launch":
                robot = tokens.length >= 3 ? tokens[2] :robotName;
                String robotType = tokens.length >= 2 ? tokens[1] : "";
                args = new String[]{robotType};
                break;
        }
        
        

        return fromJSON(new JSONObject()
                .put("robot", robot)
                .put("command", command)
                .put("arguments", new JSONArray(Arrays.asList(args))));
    }

    public abstract String commandName();
}
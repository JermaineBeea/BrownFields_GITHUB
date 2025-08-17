package za.co.wethinkcode.robots.commands;
import za.co.wethinkcode.robots.server.Status;
import za.co.wethinkcode.robots.domain.Robot;
//import za.co.wethinkcode.robots.domain.World;
import za.co.wethinkcode.robots.server.World;
import za.co.wethinkcode.robots.server.Response;

public class LaunchCommand extends Command {
    public LaunchCommand(Robot robot, String[] arguments) {
        super(robot, arguments);
    }

    @Override
    public String commandName() {
        return "launch";
    }

    @Override
    public Response execute(){
        World world = World.getInstance();
        Status status = world.addRobot(robot);

        switch(status){
            case OK -> {
                return new Response("OK", "Robot " + robot.getName() + "Launched successfully");
            }
            case ExistingName -> {
                return new Response("ERROR", "Robot with name " + robot.getName() + "already exists");
            }
            case NoSpace -> {
                return new Response("ERROR", "No more space in this world");
            }
            default -> {
                return new Response("ERROR", "Could not launch robot due to status: " + status);
            }
        }



    }
}

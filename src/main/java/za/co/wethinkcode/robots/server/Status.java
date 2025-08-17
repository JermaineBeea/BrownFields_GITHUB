package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.client.Position;
import za.co.wethinkcode.robots.handlers.CommandHandler;
import za.co.wethinkcode.robots.handlers.CommandHandler.CompletionHandler;
import za.co.wethinkcode.robots.domain.Robot;

public enum Status {
    ExistingName,
    OutOfBounds,
    HitObstacle,
    HitObstaclePIT,
    OK,
    NoSpace;

    public boolean handleMovementStatus(CommandHandler commandHandler, Robot robot, Position previous, CompletionHandler handler) {
    Response response;
    
    switch (this) {
        case HitObstaclePIT -> {
            robot.status = Robot.RobotStatus.Dead;
            response = new Response("ERROR", robot.getName() + " fell into a pit and died.");
        }
        case HitObstacle -> {
            robot.setPosition(previous.getX(), previous.getY());
            response = new Response("ERROR", robot.getName() + " hit an obstacle.");
        }
        case OutOfBounds -> {
            robot.status = Robot.RobotStatus.Dead;
            response = new Response("ERROR", robot.getName() + " fell off the world.");
        }
        case NoSpace -> {
            response = new Response("ERROR", "No more space in the world");
        }
        case OK, ExistingName -> {
            return true;
        }
        default -> {
            return true;
        }
    }
    
    commandHandler.world.stateForRobot(robot, response);
    handler.onComplete(response);
    return false;
    }
}

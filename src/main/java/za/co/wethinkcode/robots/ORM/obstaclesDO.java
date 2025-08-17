package za.co.wethinkcode.robots.orm;
import net.lemnik.eodsql.ResultColumn;

public class ObstaclesDO {

    @ResultColumn("id")
    public int id;

    @ResultColumn("world_id")
    public int worldId;

    @ResultColumn("x")
    public int x;

    @ResultColumn("y")
    int y;

    @ResultColumn("width")
    public int width;

    @ResultColumn("height")
    public int height;

    @ResultColumn("type")
    public String type;

    public ObstaclesDO(){}
    
}
package za.co.wethinkcode.robots.orm;
import net.lemnik.eodsql.ResultColumn;

public class WorldDO {
    @ResultColumn("id")
    public int id;

    @ResultColumn("name")
    public String name;


    @ResultColumn("width")
    public int width;

    @ResultColumn("height")
    public int height;


    public WorldDO(){}


}
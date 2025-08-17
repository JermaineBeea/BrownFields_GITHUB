package za.co.wethinkcode.robots.persistence.POJO;


/*POJO class for "obstacles" table

CREATE TABLE IF NOT EXISTS obstacles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    world_name TEXT,
    x INTEGER,
    y INTEGER,
    width INTEGER,
    height INTEGER,
    FOREIGN KEY(world_name) REFERENCES worlds(name)
);
 */
public class ObstacleDO {
    private int id;
    private String worldName;
    private int x;
    private int y;
    private int width;
    private int height;

    // Default constructor
    public ObstacleDO(){}

    // Constructor excluding ID
    public ObstacleDO(String worldName, int x, int y, int width, int height){
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Constructor including ID
    public ObstacleDO(int id, String worldName, int x, int y, int width, int height){
        this.id = id;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Getters and Setters
    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id = id;
    }

    public String getWorldName(){
        return worldName;
    }
    public void setWorldName(String worldName){
        this.worldName = worldName;
    }

    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }

    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }
}

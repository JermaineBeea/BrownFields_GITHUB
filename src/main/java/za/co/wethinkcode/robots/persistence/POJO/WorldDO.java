package za.co.wethinkcode.robots.persistence.POJO;

import za.co.wethinkcode.robots.domain.DomainWorld;
import za.co.wethinkcode.robots.domain.Obstacle;
import java.sql.*;
import java.util.*;
/* Pojo class for the World Table 
CREATE TABLE IF NOT EXISTS worlds (
    name TEXT PRIMARY KEY,
    width INTEGER,
    height INTEGER
);
*/
// Getter and setter methods:
// Get: name, width, height
// set: name, width height

public class WorldDO {
    private String name;
    private int width;
    private int height;

    // Default constructor
    public WorldDO() {}

    // Constructor to inititalise the object
    public WorldDO(String name, int width, int height){
        this.name = name;
        this.width = width;
        this.height = height;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
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

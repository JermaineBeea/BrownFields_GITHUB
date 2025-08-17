package za.co.wethinkcode.robots.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "world")
public class WorldEntity {

    @Id
    @Column(name = "id")
    private Integer id; 

    @Column(name = "width", nullable = false)
    private int width;

    @Column(name = "height", nullable = false)
    private int height;

    public WorldEntity() {}
    public WorldEntity(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }
   


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

}

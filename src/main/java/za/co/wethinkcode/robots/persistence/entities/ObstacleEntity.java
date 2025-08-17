package za.co.wethinkcode.robots.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "obstacles")
public class ObstacleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "X", nullable = false) private int x;
    @Column(name = "Y", nullable = false) private int y;
    @Column(name = "width", nullable = false) private int width;
    @Column(name = "height", nullable = false) private int height;

    public ObstacleEntity() { }
    public ObstacleEntity(int x, int y, int width, int height) { 
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getWidth() {return width;}
    public void setWidth (int width) {this.width = width;}
    public int getHeight() {return height;}
    public void setHeight (int height) {this.height = height;}
}







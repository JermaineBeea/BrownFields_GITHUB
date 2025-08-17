package za.co.wethinkcode.robots.orm;

import java.util.List;
import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;

public interface ObstacleDAO extends BaseQuery {

    @Select("SELECT * FROM obstacles WHERE id = ?{1}")
    ObstaclesDO getObstacleById(int id);

    @Select("SELECT * FROM obstacles WHERE world_id = ?{1}")
    List<ObstaclesDO> getObstacleForWorld(int worldId);

    @Select("SELECT * FROM obstacles")
    List<ObstaclesDO> getAllObstacles();

    @Update("INSERT INTO obstacles(id, x, y, size, world_id) VALUES(?{1}, ?{2}, ?{3}, ?{4})")
    void insertObstacle(int id, int x, int y, int size);

    @Update("DELETE FROM obstacles WHERE id = ?{1}")
    void deleteObstacle(int id);



    
    

    
}

package za.co.wethinkcode.robots.orm;

import org.hibernate.query.sqm.spi.BaseSemanticQueryWalker;

import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.robots.domain.DomainWorld;
import za.co.wethinkcode.robots.persistence.POJO.WorldDO;

public class worldDAO extends BaseQuery {
    @Select("SELECT * FROM worlds WHERE id = ?{1}")
    WorldDO getWorldById(int id);

    @Select("SELECT * FROM worlds")
    List<WorldDO> getAllWorlds();

    @Update("INSERT INTO worlds(id, name, width, height) VALUES (?{1}, ?{2}, ?{3}, ?{4})")
    void insertWorld(int id, String name, int width, int height);

    @Update("DELETE FROM worlds WHERE id = ?{1}")
    void deleteWorld(int id);
    
}

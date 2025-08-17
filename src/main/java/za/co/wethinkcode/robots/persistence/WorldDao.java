package za.co.wethinkcode.robots.persistence;

import za.co.wethinkcode.robots.domain.World;
import java.util.List;

public interface WorldDao {
    void saveWorld(World world);
    World loadWorld(String name);
    List<String> listWorldNames();
    void deleteWorld(String name);
}
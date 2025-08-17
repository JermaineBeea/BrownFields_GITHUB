package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.domain.World;
import za.co.wethinkcode.robots.persistence.WorldDao;

public class RestoreWorldCommand {
    private final WorldDao dao;

    public RestoreWorldCommand(WorldDao dao) {
        this.dao = dao;
    }

    public World execute(String name) {
        World world = dao.loadWorld(name);
        if (world == null) {
            System.out.println("World '" + name + "' not found.");
        } else {
            System.out.println("World '" + name + "' restored.");
        }
        return world;
    }
}
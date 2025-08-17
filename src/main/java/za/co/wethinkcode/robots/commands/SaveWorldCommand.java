package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.domain.World;
import za.co.wethinkcode.robots.persistence.WorldDao;

public class SaveWorldCommand {
    private final WorldDao dao;

    public SaveWorldCommand(WorldDao dao) {
        this.dao = dao;
    }

    public void execute(World world) {
        dao.saveWorld(world);
        System.out.println("World '" + world.getName() + "' saved.");
    }
}
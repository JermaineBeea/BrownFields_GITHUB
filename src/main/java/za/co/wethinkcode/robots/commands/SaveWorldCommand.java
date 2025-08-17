package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.domain.DomainWorld;
import za.co.wethinkcode.robots.persistence.WorldDao;

public class SaveWorldCommand extends Command {
    private final WorldDao dao;

    public SaveWorldCommand(WorldDao dao) {
        super(null, null);
        this.dao = dao;
    }

    public void execute(DomainWorld world) {
        dao.saveWorld(world);
        System.out.println("World '" + world.getName() + "' saved.");
    }
    
     @Override
    public String commandName() {
        return "disconnect";
    }
}
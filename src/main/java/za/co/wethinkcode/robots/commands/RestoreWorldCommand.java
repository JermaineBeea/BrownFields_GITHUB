package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.domain.Robot;
import za.co.wethinkcode.robots.domain.DomainWorld;
import za.co.wethinkcode.robots.persistence.WorldDao;

public class RestoreWorldCommand extends Command{
    
    private final WorldDao dao;

    public RestoreWorldCommand(WorldDao dao) {
        super(null, null);
        this.dao = dao;
    }

    public DomainWorld execute(String name) {
        DomainWorld world = dao.loadWorld(name);
        if (world == null) {
            System.out.println("World '" + name + "' not found.");
        } else {
            System.out.println("World '" + name + "' restored.");
        }
        return world;
    }

     @Override
    public String commandName() {
        return "disconnect";
    }
}
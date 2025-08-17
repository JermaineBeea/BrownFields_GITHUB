package za.co.wethinkcode.robots.persistence.mapper;

import za.co.wethinkcode.robots.domain.DomainWorld;
import za.co.wethinkcode.robots.domain.Obstacle;

import za.co.wethinkcode.robots.persistence.POJO.WorldDO;
import za.co.wethinkcode.robots.persistence.POJO.ObstacleDO;

import java.util.List;
import java.util.stream.Collectors;


/* Mapper class to convert between Domain Objects and Data Objects */
public class WorldMapper {

    // Converts a Domain World Objects into a WorldDO
    public static WorldDO toWorldDO(DomainWorld world){
        return new WorldDO(world.getName(), world.getWidth(), world.getHeight());
    }

    // Converts a list of Obstacle Domain Objects into a list of ObstacleDO
    public static List<ObstacleDO> toObstacleDOs(DomainWorld world){
        return world.getObstacles().stream()
            .map(o -> new ObstacleDO(world.getName(), o.getX(), o.getY(), o.getWidth(), o.getHeight()))
            .collect(Collectors.toList());
    }

    // Converts from persisted data (WolrdDO + List<ObstacleDO>) into a domain world object
    // Used when loading data from the database into the application.
    public static DomainWorld fromDO(WorldDO worldDO, List<ObstacleDO> obstacleDOs){
        List<Obstacle> obstacles = obstacleDOs.stream()
            .map(o -> new Obstacle(o.getX(), o.getY(), o.getWidth(), o.getHeight()))
            .collect(Collectors.toList());

        // Construct and return the World domain object    
        return new DomainWorld(worldDO.getName(), worldDO.getWidth(), worldDO.getHeight(), obstacles);
    }


}

package za.co.wethinkcode.robots.persistence.mapper;

import za.co.wethinkcode.robots.domain.Obstacle;
import za.co.wethinkcode.robots.persistence.POJO.ObstacleDO;

import java.util.ArrayList;
import java.util.List;

public class ObstacleMapper {
    
    // Convert from Domain Object to data object
    public static ObstacleDO toDataObject(String worldName, Obstacle obstacle){
        return new ObstacleDO(
            worldName,
            obstacle.getX(),
            obstacle.getY(),
            obstacle.getWidth(),
            obstacle.getHeight()
        );
    }

    // Convert from data object to domain object
    public static Obstacle toDomainObject(ObstacleDO obstacleDO){
        return new Obstacle(
            obstacleDO.getX(),
            obstacleDO.getY(),
            obstacleDO.getWidth(),
            obstacleDO.getHeight()
        );
    }

    // Convert list of domain objects to list of data objects
    public static List<ObstacleDO> toDataObjects(String worldName, List<Obstacle> obstacles){
        List<ObstacleDO> list = new ArrayList<>();
        for (Obstacle o : obstacles){
            list.add(toDataObject(worldName, o));
        }
        return list;
    }

    // Convert list of data objects to list of domain objects
    public static List<Obstacle> toDomainObjects(List<ObstacleDO> obstacleDOs){
        List<Obstacle> list = new ArrayList<>();
        for (ObstacleDO o : obstacleDOs){
            list.add(toDomainObject(o));
        }
        return list;
    }
}

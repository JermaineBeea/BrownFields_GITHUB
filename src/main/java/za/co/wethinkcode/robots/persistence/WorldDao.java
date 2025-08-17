package za.co.wethinkcode.robots.persistence;

import za.co.wethinkcode.robots.domain.DomainWorld;
import java.util.List;

public interface WorldDao {
    void saveWorld(DomainWorld world);
    DomainWorld loadWorld(String name);
    List<String> listWorldNames();
    void deleteWorld(String name);
}
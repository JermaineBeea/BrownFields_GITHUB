package za.co.wethinkcode.robots.persistence;

import za.co.wethinkcode.robots.domain.DomainWorld;
import za.co.wethinkcode.robots.domain.Obstacle;
import java.sql.*;
import java.util.*;

public class WorldDaoImpl implements WorldDao {

    private final String dbUrl;

    public WorldDaoImpl(String dbUrl) {
        this.dbUrl = dbUrl;
        createTables();
    }

        private void createTables() {
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS worlds (" +
                "name TEXT PRIMARY KEY, width INTEGER, height INTEGER)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS obstacles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, world_name TEXT, " +
                "x INTEGER, y INTEGER, width INTEGER, height INTEGER, " +
                "FOREIGN KEY(world_name) REFERENCES worlds(name))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public DomainWorld restoreWorld(String name) {
        return loadWorld(name);
    }


    @Override
    public void saveWorld(DomainWorld world) {
           try (Connection conn = DriverManager.getConnection(dbUrl)) {
            conn.setAutoCommit(false);
            try (PreparedStatement worldStmt = conn.prepareStatement(
                    "INSERT OR REPLACE INTO worlds(name, width, height) VALUES (?, ?, ?)")) {
                worldStmt.setString(1, world.getName());
                worldStmt.setInt(2, world.getWidth());
                worldStmt.setInt(3, world.getHeight());
                worldStmt.executeUpdate();
            }
            try (PreparedStatement delObs = conn.prepareStatement(
                    "DELETE FROM obstacles WHERE world_name = ?")) {
                delObs.setString(1, world.getName());
                delObs.executeUpdate();
            }
            try (PreparedStatement obsStmt = conn.prepareStatement(
                    "INSERT INTO obstacles(world_name, x, y, width, height) VALUES (?, ?, ?, ?, ?)")) {
                for (Obstacle o : world.getObstacles()) {
                    obsStmt.setString(1, world.getName());
                    obsStmt.setInt(2, o.getX());
                    obsStmt.setInt(3, o.getY());
                    obsStmt.setInt(4, o.getWidth());
                    obsStmt.setInt(5, o.getHeight());
                    obsStmt.addBatch();
                }
                obsStmt.executeBatch();
            }
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DomainWorld loadWorld(String name) {
                try (Connection conn = DriverManager.getConnection(dbUrl)) {
            try (PreparedStatement worldStmt = conn.prepareStatement(
                    "SELECT width, height FROM worlds WHERE name = ?")) {
                worldStmt.setString(1, name);
                try (ResultSet rs = worldStmt.executeQuery()) {
                    if (!rs.next()) return null;
                    int width = rs.getInt("width");
                    int height = rs.getInt("height");
                    List<Obstacle> obstacles = new ArrayList<>();
                    try (PreparedStatement obsStmt = conn.prepareStatement(
                            "SELECT x, y, width, height FROM obstacles WHERE world_name = ?")) {
                        obsStmt.setString(1, name);
                        try (ResultSet obsRs = obsStmt.executeQuery()) {
                            while (obsRs.next()) {
                                obstacles.add(new Obstacle(
                                    obsRs.getInt("x"),
                                    obsRs.getInt("y"),
                                    obsRs.getInt("width"),
                                    obsRs.getInt("height")
                                ));
                            }
                        }
                    }
                    return new DomainWorld(name, width, height, obstacles);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     @Override
    public List<String> listWorldNames() {
        List<String> names = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM worlds")) {
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    @Override
    public void deleteWorld(String name) {
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            try (PreparedStatement delObs = conn.prepareStatement(
                    "DELETE FROM obstacles WHERE world_name = ?")) {
                delObs.setString(1, name);
                delObs.executeUpdate();
            }
            try (PreparedStatement delWorld = conn.prepareStatement(
                    "DELETE FROM worlds WHERE name = ?")) {
                delWorld.setString(1, name);
                delWorld.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
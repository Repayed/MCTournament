package me.repayed.mctournament.game.arena;

import me.repayed.mctournament.utility.CubedRegion;
import org.bukkit.Location;

import java.util.List;

public class MapData {

    private String name;
    private CubedRegion mapRegion;
    private List<Location> spawnLocations;

    public MapData(String name, CubedRegion cubedRegion, List<Location> spawnLocations) {
        this.name = name;
        this.mapRegion = cubedRegion;
        this.spawnLocations = spawnLocations;
    }


    public String getName() {
        return name;
    }

    public CubedRegion getMapRegion() {
        return mapRegion;
    }

    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }
}

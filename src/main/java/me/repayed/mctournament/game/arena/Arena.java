package me.repayed.mctournament.game.arena;

import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Arena {

    private MapData mapData;

    public Arena(MapData mapData) {
        this.mapData = mapData;
    }

    public MapData getMapData() {
        return mapData;
    }

    public Location getRandomSpawnLocation() {
        List<Location> locations = mapData.getSpawnLocations();
        return locations.get(ThreadLocalRandom.current().nextInt(locations.size()) - 1);
    }

}

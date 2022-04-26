package me.repayed.mctournament.game.arena;

import me.repayed.mctournament.MCTournament;
import me.repayed.mctournament.game.type.GameType;
import me.repayed.mctournament.utility.CubedRegion;
import me.repayed.mctournament.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class ArenaProvider {

    private MCTournament core;
    private Map<GameType, List<MapData>> maps;

    public ArenaProvider(MCTournament core) {
        this.core = core;
        this.maps = new HashMap<>();
        loadMaps();
    }

    public void loadMaps() {
        List<MapData> spleefMaps = new ArrayList<>();

        Objects.requireNonNull(core.getConfig().getConfigurationSection("spleef.arenas.")).getKeys(false).forEach(key -> {
            System.out.println("spleef.arenas."+key+".name");
            String mapName = null;
            CubedRegion cubedRegion = null;
            List<Location> spawnLocations = new ArrayList<>();

            mapName = core.getConfig().getString("spleef.arenas."+key + ".name");

            Location firstBound = Utility.getDeserializedLocation(core, "spleef.arenas."+key + ".region.first-bound.serialized-location");
            Location secondBound = Utility.getDeserializedLocation(core, "spleef.arenas."+key + ".region.second-bound.serialized-location");

            cubedRegion = new CubedRegion(firstBound, secondBound);

            Objects.requireNonNull(core.getConfig().getConfigurationSection("spleef.arenas." +key+".spawn-locations.")).getKeys(false).forEach(keyTwo -> {
                System.out.println("spleef.arenas." + key+".spawn-locations."+keyTwo);
                System.out.println("spleef.arenas." + key+".spawn-locations."+keyTwo+".serialized-location");
                spawnLocations.add(Utility.getDeserializedLocation(core, "spleef.arenas."+key+".spawn-locations."+keyTwo + ".serialized-location"));
                System.out.println("spleef.arenas." + key+".spawn-locations."+keyTwo+".serialized-location");
            });

            spleefMaps.add(new MapData(mapName, cubedRegion, spawnLocations));
        });

        maps.put(GameType.SPLEEF, spleefMaps);

        Bukkit.getLogger().log(Level.CONFIG, "Loaded all maps.");
    }

    public MapData getRandomMapByGameType(GameType gameType) {
        List<MapData> mapsForGameType = maps.get(gameType);
        return mapsForGameType.get(ThreadLocalRandom.current().nextInt(mapsForGameType.size()));
    }
}

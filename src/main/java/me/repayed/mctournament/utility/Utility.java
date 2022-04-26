package me.repayed.mctournament.utility;

import me.repayed.mctournament.MCTournament;
import me.repayed.mctournament.player.GamePlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;


public class Utility {

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String serializeLocation(Location location) {
        return Objects.requireNonNull(location.getWorld()).getName()+";"+location.getX()+";"+location.getY()+";"+location.getZ()+";"+location.getYaw()+";"+location.getPitch();
    }

    public static Location getDeserializedLocation(MCTournament core, String path) {
        final String unformattedLocation = core.getConfig().getString(path);
        final String parts[] = unformattedLocation.split(";");

        final World world = Bukkit.getWorld(parts[0]);
        final double x = Double.parseDouble(parts[1]);
        final double y = Double.parseDouble(parts[2]);
        final double z = Double.parseDouble(parts[3]);
        final float yaw = Float.parseFloat(parts[4]);
        final float pitch = Float.parseFloat(parts[5]);

        return new Location(world, x, y ,z, yaw, pitch);
    }

    public static GamePlayer[] getTopFivePlayers(List<? extends GamePlayer> list) {
        Collections.sort(list, Comparator.comparing(GamePlayer::getPoints));
        final GamePlayer[] sortedTopFive = new GamePlayer[5];

        for(int i = 0; i < 5; i++) {
            sortedTopFive[i] = list.get(i);
        }

        return sortedTopFive;
    }

    public static void setDefaultPlayer(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20D);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);

    }

}

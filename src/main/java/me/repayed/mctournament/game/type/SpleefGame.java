package me.repayed.mctournament.game.type;

import me.repayed.mctournament.MCTournament;
import me.repayed.mctournament.game.AbstractGame;
import me.repayed.mctournament.game.Countdown;
import me.repayed.mctournament.game.arena.Arena;
import me.repayed.mctournament.player.GamePlayer;
import me.repayed.mctournament.utility.CubedRegion;
import me.repayed.mctournament.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Array;
import java.util.*;

public class SpleefGame extends AbstractGame {

    private final List<Block> blocksBroken;

    public SpleefGame(MCTournament core, Arena arena) {
        super(core, GameType.SPLEEF, arena, 4);
        this.blocksBroken = new ArrayList<>();
    }

    @Override
    public void preStart() {
        new Countdown(getCore(), this, 15).start();
    }

    @Override
    public void onStart() {
        getCore().getTournament().getPlayers().forEach(gamePlayer -> {
            gamePlayer.setStatus(GamePlayer.PlayerStatus.PLAYING);
            Player player = Bukkit.getPlayer(gamePlayer.getUuid());
            Utility.setDefaultPlayer(player);
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_SHOVEL));
            player.teleport(getArena().getRandomSpawnLocation());
            player.sendTitle(Utility.format("&b&lSPLEEF"), Utility.format("&fDestroy all the blocks!"), 20, 60, 20);
        });
    }

    @Override
    public void onEnd() {
        blocksBroken.forEach(block -> {
            World world = block.getWorld();
            world.getBlockAt(block.getLocation()).setType(block.getType());
        });

        GamePlayer winner = getCore().getTournament().getPlayers().stream().filter(gamePlayer -> gamePlayer.getStatus() == GamePlayer.PlayerStatus.PLAYING).findFirst().get();
        winner.setPoints(winner.getPoints() + getPointsPerWin());

        getWinnerDisplay(Objects.requireNonNull(Bukkit.getPlayer(winner.getUuid()))).forEach(message -> {
            Bukkit.broadcastMessage(Utility.format(message));
        });

        getCore().getTournament().getPlayers().forEach(gamePlayer -> {
            Player player = Bukkit.getPlayer(gamePlayer.getUuid());

            if (gamePlayer.getUuid().equals(gamePlayer.getUuid())) {
                Bukkit.getWorld(Objects.requireNonNull(player).getWorld().getName()).spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
                Firework firework = (Firework) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(player.getLocation(), EntityType.FIREWORK);
                firework.getFireworkMeta().addEffect(FireworkEffect.builder().withColor(Color.AQUA).trail(true).withFlicker().build());
            }

            Objects.requireNonNull(player).playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.80F, 0.60F);


        });

    }

    @Override
    public List<String> getWinnerDisplay(Player player) {
        return Arrays.asList("", "", "", "    &b&lMCTournament",
                "    &f" + player.getDisplayName() + "&7 has won this event and received &f+" + getPointsPerWin() + " &7points!", "", "");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        GamePlayer gamePlayer = getCore().getTournament().getGamePlayerByUUID(player.getUniqueId());

        Block block = event.getBlock();

        CubedRegion mapRegion = getArena().getMapData().getMapRegion();

        if (!mapRegion.withinRegion(player.getLocation()) && !player.isOp()) event.setCancelled(true);
        if (mapRegion.withinRegion(player.getLocation()) && gamePlayer.getStatus() == GamePlayer.PlayerStatus.INACTIVE)
            event.setCancelled(true);
        if (mapRegion.withinRegion(player.getLocation()) && gamePlayer.getStatus() == GamePlayer.PlayerStatus.PLAYING && !block.getType().equals(Material.SNOW_BLOCK))
            event.setCancelled(true);

        blocksBroken.add(block);
    }
}

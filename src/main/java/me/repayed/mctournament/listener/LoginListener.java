package me.repayed.mctournament.listener;

import me.repayed.mctournament.MCTournament;
import me.repayed.mctournament.game.AbstractGame;
import me.repayed.mctournament.player.GamePlayer;
import me.repayed.mctournament.tournament.Tournament;
import me.repayed.mctournament.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.List;

public class LoginListener implements Listener {

    private MCTournament core;
    private Tournament tournament;

    private List<String> tournamentLoginMessage;

    public LoginListener(MCTournament core) {
        this.core = core;
        this.tournament = core.getTournament();

        this.tournamentLoginMessage =
                Arrays.asList("", "", "", "    &b&lMCTournament",
                        "    &7Compete in various randomly chosen games to gain points.",
                        "    &7Win by receiving &f" + Tournament.NEEDED_POINTS_TO_WIN + " &7points.",
                        "",
                        "    &e&lBest of luck!",
                        "", "");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean isTournamentActive = tournament.isTournamentActive();

        tournamentLoginMessage.forEach(message -> {
            player.sendMessage(Utility.format(message));
        });

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.sendMessage(Utility.format("&b&l[MCTournament] &3" + player.getDisplayName() + "&b has joined!"));
            onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.90F, 0.90F);
        });

        if(!tournament.containsPlayer(player.getUniqueId())) tournament.addPlayer(new GamePlayer(player.getUniqueId()));

        if(!isTournamentActive) {

            player.teleport(Utility.getDeserializedLocation(core, "hub.serialized-location"));

            tournament.startHandler();
        } else {
            final AbstractGame currentGame = tournament.getCurrentGame();

            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(currentGame.getArena().getRandomSpawnLocation());

        }

    }
}

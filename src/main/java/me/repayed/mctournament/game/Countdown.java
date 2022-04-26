package me.repayed.mctournament.game;

import me.repayed.mctournament.MCTournament;
import me.repayed.mctournament.game.arena.Arena;
import me.repayed.mctournament.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private final MCTournament core;
    private final AbstractGame game;
    private int countdownSeconds;

    public Countdown(final MCTournament core, final AbstractGame game, final int countdownSeconds) {
        this.core = core;
        this.game = game;
        this.countdownSeconds = countdownSeconds;
    }

    public void start() {
        this.game.preStart();
        runTaskTimer(core, 0, 20L);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0) {
            if (core.getTournament().shouldTournamentStart()) {
                game.onStart();
                cancel();
            } else {
                core.getTournament().getPlayers().forEach(gamePlayer -> {
                    Player player = Bukkit.getPlayer(gamePlayer.getUuid());
                    player.sendMessage(Utility.format("&c" + countdownSeconds));
                });
                game.setStatus(AbstractGame.GameStatus.PREGAME);
                cancel();
            }
        } else if (countdownSeconds == 60 || countdownSeconds == 45 || countdownSeconds == 30 || countdownSeconds == 15 || countdownSeconds == 10 || countdownSeconds <= 5) {
            core.getTournament().getPlayers().forEach(gamePlayer -> {
                Player player = Bukkit.getPlayer(gamePlayer.getUuid());
                player.sendMessage(Utility.format("&c" + countdownSeconds));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, 1.0F);
            });
        }
        countdownSeconds--;

    }
}

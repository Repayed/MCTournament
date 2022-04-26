package me.repayed.mctournament.game;

import me.repayed.mctournament.MCTournament;
import me.repayed.mctournament.game.arena.Arena;
import me.repayed.mctournament.game.type.GameType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class AbstractGame implements Listener {

    public enum GameStatus {
        PREGAME, LIVE, ENDED
    }

    private final MCTournament core;
    private final GameType gameType;
    private final Arena arena;

    private int pointsPerWin;

    private GameStatus status;

    public AbstractGame(MCTournament core, GameType gameType, Arena arena, int pointsPerWin) {
        this.core = core;
        this.gameType = gameType;
        this.arena = arena;
        this.pointsPerWin = pointsPerWin;
        this.status = GameStatus.PREGAME;
    }

    public abstract void preStart();
    public abstract void onStart();
    public abstract void onEnd();
    public abstract List<String> getWinnerDisplay(Player player);

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    public GameStatus getStatus() {
        return this.status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Arena getArena() {
        return this.arena;
    }

    public MCTournament getCore() {
        return this.core;
    }

    public int getPointsPerWin() {
        return pointsPerWin;
    }
}

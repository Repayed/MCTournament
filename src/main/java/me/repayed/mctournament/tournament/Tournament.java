package me.repayed.mctournament.tournament;

import me.repayed.mctournament.MCTournament;
import me.repayed.mctournament.game.AbstractGame;
import me.repayed.mctournament.game.arena.Arena;
import me.repayed.mctournament.game.arena.ArenaProvider;
import me.repayed.mctournament.game.type.GameType;
import me.repayed.mctournament.game.type.SpleefGame;
import me.repayed.mctournament.player.GamePlayer;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Tournament {

    private MCTournament core;

    public static final int MINIMUM_STARTING_COUNT = 2;
    public static final int NEEDED_POINTS_TO_WIN = 24;

    private boolean isTournamentActive;

    private AbstractGame currentGame;
    private List<AbstractGame> games;

    private Set<GamePlayer> players;

    public Tournament(MCTournament core) {
        this.core = core;
        this.isTournamentActive = false;
        this.currentGame = null;
        this.games = new ArrayList<>();
        this.players = new HashSet<>();
        loadGames();
    }

    public boolean isTournamentActive() {
        return isTournamentActive;
    }

    public void setTournamentActive(boolean isActive) {
        this.isTournamentActive = isActive;
    }

    public AbstractGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(AbstractGame currentGame) {
        this.currentGame = currentGame;
    }

    public List<AbstractGame> getGames() {
        return games;
    }

    public boolean shouldTournamentStart() {
        return Bukkit.getOnlinePlayers().size() >= MINIMUM_STARTING_COUNT;
    }

    public boolean shouldTournamentEnd() {
        if(players.size() <= MINIMUM_STARTING_COUNT) return true;

       for(GamePlayer gamePlayer : getPlayers()) {
           if(gamePlayer.getPoints() >= NEEDED_POINTS_TO_WIN) return true;
       }

       return false;
    }

    public void startHandler() {
        if (!shouldTournamentStart()) return;

        loadGames();
        final AbstractGame randomlyPickedGame = getRandomGame();
        randomlyPickedGame.preStart();

    }

    private AbstractGame getRandomGame() {
        return games.get(ThreadLocalRandom.current().nextInt(games.size()) - 1);
    }

    public void loadGames() {
        this.games.add(new SpleefGame(core, new Arena(core.getArenaProvider().getRandomMapByGameType(GameType.SPLEEF))));
    }

    public boolean containsPlayer(UUID uuid) {
        return players.stream().anyMatch(gamePlayer -> gamePlayer.getUuid().equals(uuid));
    }

    public Set<GamePlayer> getPlayers() {
        return players;
    }

    public void addPlayer(GamePlayer gamePlayer) {
        this.players.add(gamePlayer);
    }

    public void removePlayer(GamePlayer gamePlayer) {
        this.players.remove(gamePlayer);
    }

    public GamePlayer getGamePlayerByUUID(UUID uuid) {
        return players.stream().filter(gamePlayer -> gamePlayer.getUuid().equals(uuid)).findAny().get();
    }
}

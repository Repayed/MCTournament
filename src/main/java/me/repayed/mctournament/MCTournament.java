package me.repayed.mctournament;

import me.repayed.mctournament.game.arena.Arena;
import me.repayed.mctournament.game.arena.ArenaProvider;
import me.repayed.mctournament.listener.LoginListener;
import me.repayed.mctournament.listener.QuitListener;
import me.repayed.mctournament.tournament.Tournament;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class MCTournament extends JavaPlugin {

    private Tournament tournament;
    private ArenaProvider arenaProvider;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.arenaProvider = new ArenaProvider(this);
        this.tournament = new Tournament(this);

        loadListeners();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadListeners() {
        Arrays.asList(new LoginListener(this), new QuitListener()).forEach(listener -> Bukkit.getServer().getPluginManager().registerEvents(listener, this));
    }

    public Tournament getTournament() {
        return tournament;
    }

    public ArenaProvider getArenaProvider() {
        return arenaProvider;
    }
}

package me.repayed.mctournament.player;

import java.util.UUID;

public class GamePlayer {

    public enum PlayerStatus {
        INACTIVE, PLAYING
    }

    private final UUID uuid;
    private int points;
    private PlayerStatus status;

    public GamePlayer(UUID uuid) {
        this.uuid = uuid;
        this.points = 0;
        this.status = PlayerStatus.INACTIVE;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public PlayerStatus getStatus() {
        return this.status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }
}

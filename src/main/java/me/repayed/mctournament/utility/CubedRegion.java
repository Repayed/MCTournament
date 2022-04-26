package me.repayed.mctournament.utility;

import org.bukkit.Location;
import org.bukkit.World;

public class CubedRegion {
    private World world;

    private double maxX;
    private double maxY;
    private double maxZ;

    private double minX;
    private double minY;
    private double minZ;

    public CubedRegion(Location firstBound, Location secondBound) {
        if (!firstBound.getWorld().equals(secondBound.getWorld())) return;

        this.world = firstBound.getWorld();

        this.maxX = Math.max(firstBound.getX(), secondBound.getX());
        this.maxY = Math.max(firstBound.getY(), secondBound.getY());
        this.maxZ = Math.max(firstBound.getZ(), secondBound.getZ());

        this.minX = Math.min(firstBound.getX(), secondBound.getX());
        this.minY = Math.min(firstBound.getY(), secondBound.getY());
        this.minZ = Math.min(firstBound.getZ(), secondBound.getZ());

    }

    public boolean withinRegion(Location location) {
        if (!world.getName().equals(location.getWorld().getName())) return false;

        if (location.getY() > this.maxY) return false;
        if (location.getY() < this.minY) return false;

        if (location.getY() > this.maxX) return false;
        if (location.getY() < this.minX) return false;

        if (location.getY() > this.maxZ) return false;
        if (location.getY() < this.minZ) return false;
        return true;
    }


}




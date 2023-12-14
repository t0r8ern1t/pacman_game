package com.mygdx.game;

public enum Direction {
    UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0), STOP(0, 0);

    private int vx;
    private int vy;

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    Direction(int vx, int vy) {
        this.vx = vx;
        this.vy = vy;
    }
}

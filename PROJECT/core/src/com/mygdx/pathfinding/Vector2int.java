package com.mygdx.pathfinding;

public class Vector2int {
    public int x, y;

    public Vector2int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2int(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public int getDistanceManhattan(Vector2int point2) {
        return Math.abs(x - point2.x) + Math.abs(y - point2.y);
    }

    public float getDistanceEucl(Vector2int point2) {
        int dx = x - point2.x;
        int dy = y - point2.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public boolean equals(Vector2int point2) {
        return x == point2.x && y == point2.y;
    }
}

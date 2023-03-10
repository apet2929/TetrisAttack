package com.apet2929.tetris_attack;

public class Pos {
    public int x;
    public int y;
    public Pos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Pos add(int dx, int dy){
        return new Pos(x + dx, y + dy);
    }

    public Pos add(Pos pos){
        return this.add(pos.x, pos.y);
    }

    @Override
    public boolean equals(Object obj) {
        Pos p = (Pos) obj;
        return (p.x == x) && (p.y == y);
    }

    @Override
    public int hashCode() {
        return (y * Grid.GRID_WIDTH) + x;
    }

    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

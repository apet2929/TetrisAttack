package com.apet2929.tetris_attack;

public class Pos {
    public int x;
    public int y;
    private float rx;
    private float ry;
    public Pos(int x, int y){
        this.x = x;
        this.y = y;
        this.rx = x;
        this.ry = y;
    }
    private Pos(float x, float y){
        this.x = (int) x;
        this.y = (int) y;
        this.rx = x;
        this.ry = y;
    }

    public Pos add(int dx, int dy) {
        return new Pos(x + dx, y + dy);
    }

    public Pos add(Pos pos){
        return this.add(pos.x, pos.y);
    }

    public Pos lerp(Pos desired, float t){
        float x = lerp(this.rx, desired.x, t);
        float y = lerp(this.ry, desired.y, t);
        return new Pos(x, y);
    }

    private float lerp(float start, float end, float t) {
        return start + t * (end - start);
    }

    public float getPixelsX(){
        return rx;
    }

    public float getPixelsY() {
        return ry;
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

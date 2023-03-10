package com.apet2929.tetris_attack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Cursor {
    private int x;
    private int y;
    public Cursor(){
        x = 0;
        y = 0;
    }

    public void translate(int dx, int dy) {
        if(isInBounds(x + dx, y + dy)){
            this.x += dx;
            this.y += dy;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private boolean isInBounds(int x, int y){
        return x >= 0 && x < Grid.GRID_WIDTH-1 && y >= 0 && y < Grid.GRID_HEIGHT-1;
    }

    public void draw(SpriteBatch sb, TextureAtlas textures){
        int realX = (x * Grid.PANEL_SIZE) + Grid.START_X;
        int realX2 = realX + Grid.PANEL_SIZE;
        int realY = (y * Grid.PANEL_SIZE) + Grid.START_Y;
        sb.draw(textures.findRegion("p1_cursor"), realX, realY, Grid.PANEL_SIZE, Grid.PANEL_SIZE);
        sb.draw(textures.findRegion("p1_cursor"), realX2, realY, Grid.PANEL_SIZE, Grid.PANEL_SIZE);
    }

    @Override
    public String toString() {
        return "Cursor{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

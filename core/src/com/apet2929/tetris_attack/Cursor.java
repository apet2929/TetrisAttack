package com.apet2929.tetris_attack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Cursor {
    private static final float LERP_CONST = 0.5f;

    private int x;
    private int y;
    private Pos posPixels;
    public Cursor(){
        x = 0;
        y = 0;
        posPixels = new Pos(0,0);
    }

    public void translate(int dx, int dy) {
        if(isInBounds(x + dx, y + dy)){
            this.x += dx;
            this.y += dy;
        }
    }

    public void update(float delta) {
        this.posPixels = this.posPixels.lerp(new Pos(this.x, this.y), LERP_CONST);
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

    public void draw(SpriteBatch sb, TextureAtlas textures) {
        float rx = posPixels.getPixelsX();
        float ry = posPixels.getPixelsY();
        int realX = (int) ((rx * Grid.PANEL_SIZE) + Grid.START_X);
        int realX2 = realX + Grid.PANEL_SIZE;
        int realY = (int) ((ry * Grid.PANEL_SIZE) + Grid.START_Y);
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

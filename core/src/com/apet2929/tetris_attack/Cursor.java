package com.apet2929.tetris_attack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Cursor {
    private static final float LERP_CONST = 0.1f;

    private Pos pos;
    private Animation animation;
    public Cursor(){
        pos = new Pos(0,0);
        animation = new Animation(pos, pos, LERP_CONST);
    }

    public void translate(int dx, int dy) {
        if(isInBounds(pos.x + dx, pos.y + dy)){
            this.animation = new Animation(pos.clone(), pos.add(dx, dy), LERP_CONST);
            this.pos = pos.add(dx, dy);
        }
    }

    public void update(float delta) {
        animation.update(delta);
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }

    private boolean isInBounds(int x, int y){
        return x >= 0 && x < Grid.GRID_WIDTH-1 && y >= 0 && y < Grid.GRID_HEIGHT-1;
    }

    public void draw(SpriteBatch sb, TextureAtlas textures) {
        int realX = Grid.getPanelPosX(animation.getX());
        int realX2 = realX + Grid.PANEL_SIZE;
        int realY = Grid.getPanelPosY(animation.getY());
        sb.draw(textures.findRegion("p1_cursor"), realX, realY, Grid.PANEL_SIZE, Grid.PANEL_SIZE);
        sb.draw(textures.findRegion("p1_cursor"), realX2, realY, Grid.PANEL_SIZE, Grid.PANEL_SIZE);
    }


    @Override
    public String toString() {
        return "Cursor{" +
                "x=" + pos.x +
                ", y=" + pos.y +
                '}';
    }
}

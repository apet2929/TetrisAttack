package com.apet2929.tetris_attack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Panel {
    public static float SWAP_TIME = 0.2f;
    public static float FALL_TIME = 0.5f;
    public PanelType pt;
    public Pos pos;
    private Animation animation;
    public float fallTimer;

    public Panel(PanelType pt, Pos pos) {
        this.pt = pt;
        this.pos = pos;
        this.fallTimer = -1;
        animation = new Animation(pos, pos, SWAP_TIME);
    }

    public void fall(int destY) {
        this.animation = new Animation(this.pos, new Pos(this.pos.x, destY), FALL_TIME, () -> {
            this.pos.y = destY;
            this.fallTimer = -1;
        });
    }

    public void swap(Pos other) {
        this.animation = new Animation(this.pos.clone(), other.clone(), SWAP_TIME);
        this.pos = other;
    }

    public void update(float dt) {
        animation.update(dt);
    }

    public void draw(SpriteBatch sb, TextureAtlas textures) {
        sb.draw(textures.findRegion(pt.asset), Grid.getPanelPosX(animation.getX()), Grid.getPanelPosY(animation.getY()), Grid.PANEL_SIZE, Grid.PANEL_SIZE);
    }

}

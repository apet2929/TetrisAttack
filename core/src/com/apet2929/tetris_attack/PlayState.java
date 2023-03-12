package com.apet2929.tetris_attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class PlayState extends State {
    public static final float SECONDS_BETWEEN_TICKS = 0.3f;
    SpriteBatch batch;
    TextureAtlas textures;
    Grid grid;
    Cursor cursor;

    public PlayState() {
        batch = new SpriteBatch();
        textures = new TextureAtlas("sprites/sprites.txt");
        grid = new Grid();
        for (int i = 0; i < 6; i++) {
            grid.initRandomRow(i);
            grid.set(PanelType.HEART, i, 0);
        }
        cursor = new Cursor();

        test();
    }

    private void test(){
        Pos p1 = new Pos(0,0);
        Pos p2 = new Pos(1,0);
        Animation animation = new Animation(p1, p2, 1, () -> {
            System.out.println("Yee");
        });
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);

    }
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        grid.draw(batch, textures);
        cursor.draw(batch, textures);
        batch.end();
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        cursor.update(delta);
        grid.tick(delta);
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) cursor.translate(-1, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) cursor.translate(1, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) cursor.translate(0, 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) cursor.translate(0, -1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            grid.swap(cursor);
            System.out.println("cursor.getX() = " + cursor.getX());
            System.out.println("cursor.getY() = " + cursor.getY());
        }
    }
}

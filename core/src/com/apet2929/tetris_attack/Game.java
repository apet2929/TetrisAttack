package com.apet2929.tetris_attack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	public static final float SECONDS_BETWEEN_TICKS = 0.3f;

	SpriteBatch batch;
	TextureAtlas textures;
	Grid grid;
	Cursor cursor;
	float tickTimer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		textures = new TextureAtlas("sprites/sprites.txt");
		grid = new Grid();
		for (int i = 0; i < 6; i++){
			grid.initRandomRow(i);
			grid.set(PanelType.HEART, i, 0);
		}
		cursor = new Cursor();
		tickTimer = SECONDS_BETWEEN_TICKS;
	}

	public void update(){
		float delta = Gdx.graphics.getDeltaTime();
		tickTimer -= delta;

		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) cursor.translate(-1, 0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) cursor.translate(1, 0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) cursor.translate(0, 1);
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) cursor.translate(0, -1);
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
//			grid.swap(cursor);
			grid.tick();
		} else {
			if(tickTimer < 0) {
//				grid.tick();
				tickTimer = SECONDS_BETWEEN_TICKS;
			}
		}

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);

		update();

		batch.begin();
		grid.draw(batch, textures);
		cursor.draw(batch, textures);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		textures.dispose();
	}
}

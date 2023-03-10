package com.apet2929.tetris_attack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	StateManager stateManager;


	@Override
	public void create () {
		stateManager = new StateManager();
		stateManager.push(new PlayState());
	}

	@Override
	public void render () {
		stateManager.run();
	}
	
	@Override
	public void dispose () {
//		batch.dispose();
//		textures.dispose();
	}
}

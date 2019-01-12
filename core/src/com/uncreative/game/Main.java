//package com.uncreative.game;
//
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//
//public class Main extends ApplicationAdapter {
//	public enum dir{
//		N, NE, E, SE, S, SW, W, NW
//	}
//	public static final int size = 128;
//	public static Location[][] map = new Location[size][size];
//
//
//	SpriteBatch batch;
//	Texture img;
//
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//		setup(128);
//	}
//
//	@Override
//	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}
//
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
//
//	public void setup(Integer size) {
//		for(int i = 0; i < size; i++) {
//			for(int j = 0; j < size; j++) {
//				this.map[i][j] = new Location(i, j);
//			}
//		}
//	}
//}
///*
//
//-If in combat, activeBuffs needs to be reset at the start of each turn to = just the collegeBuff
//
// */

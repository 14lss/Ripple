package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Ripple extends ApplicationAdapter {

	OrthographicCamera camera;
	ShapeRenderer shapeRenderer;

	final int SCREEN_WIDTH = 1920;
	final int SCREEN_HEIGHT = 1080;
	final int SCALE = 5;
	final int GRID_WIDTH = SCREEN_WIDTH / SCALE;
	final int GRID_HEIGHT = SCREEN_HEIGHT / SCALE;
	final int TILE_WIDTH = SCREEN_WIDTH / GRID_WIDTH;
	final int TILE_HEIGHT = SCREEN_HEIGHT / GRID_HEIGHT;

	float[][] grid;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(true);
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);

		System.out.println(TILE_WIDTH);
		grid = new float[GRID_WIDTH][GRID_HEIGHT];
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		Gdx.graphics.setTitle("Ripple [FPS: " + Gdx.graphics.getFramesPerSecond() + "]");

		if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY)) {
			grid = new float[GRID_WIDTH][GRID_HEIGHT];
		}

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			int mouseGridX = (int) Math.floor(Gdx.input.getX() / (SCREEN_WIDTH / GRID_WIDTH));
			int mouseGridY = (int) Math.floor(Gdx.input.getY() / (SCREEN_HEIGHT / GRID_HEIGHT));
			grid[mouseGridX][mouseGridY] = 1;
		}

		for (int x = 1; x < GRID_WIDTH - 1; x++) {
			for (int y = 1; y < GRID_HEIGHT - 1; y++) {
				grid[x - 1][y] += grid[x][y] / 25;
				grid[x + 1][y] += grid[x][y] / 25;
				grid[x][y - 1] += grid[x][y] / 25;
				grid[x][y + 1] += grid[x][y] / 25;

				if (grid[x][y] > (1/4f)) grid[x][y] -= (1/4f);
			}
		}

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (int x = 0; x < GRID_WIDTH; x++) {
			for (int y = 0; y < GRID_HEIGHT; y++) {
				shapeRenderer.setColor(new Color(grid[x][y], grid[x][y], grid[x][y], 0));
				shapeRenderer.rect(x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
			}
		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}

package com.game.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.bullet.Bullets;
import com.game.Connection.Connection;
import com.game.EnemyManager;

import java.io.IOException;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;
    private Ship ship, enemyShip;
    private EnemyManager enemyManager;
    private Connection connect;

    @Override
    public void create() {

        Gdx.graphics.setWindowedMode(600,700);

        Gdx.input.setInputProcessor(this);

        try {
            connect = new Connection("player");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.enemyManager = new EnemyManager();

        this.batch = new SpriteBatch();

        this.ship = new Ship("ally","redfighter0006.png");
        this.enemyShip = new Ship("enemy","bluefighter0006.png");

        this.ship.setSize(90f, 90f);
        this.enemyShip.setSize(90f,90f);
        this.enemyShip.setPosition(500,600);
        this.enemyShip.setFlip(false,true);

        Bullets.setBatch(batch);
        Bullets.addShip(ship,enemyShip);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();

        Bullets.update();

        connect.sendDataShip(ship);
        connect.updateShip(enemyShip);
        this.ship.draw(batch);
        this.enemyShip.draw(batch);
        this.controls();

        this.batch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.ship.dispose();
        this.enemyShip.dispose();
    }

    private void controls() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) ship.translateX(-10f);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ship.translateX(10f);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) ship.translateY(10f);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) ship.translateY(-10f);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            ship.shoot();
            connect.sendDataShoot(ship);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

package com.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.bullet.Bullets;

/**
 * Created by root on 10/04/17.
 */
public class Ship extends Sprite {
    private String id;
    private boolean local;

    public Ship(String id, String texture) {
        super(new Texture(texture));
        this.id = id;
        local = id.equals("ally");
    }

    public void dispose() {
        getTexture().dispose();
    }

    public void shoot() {
        Bullets.shoot(getX() + (getWidth() / 2), getY() + getHeight(), local);
    }

    public String getId() {
        return id;
    }

    @Override
    public void setPosition(float x, float y) {
        if (x > 0 && x < Gdx.graphics.getWidth())
            super.setPosition(x, y);
    }

    @Override
    public void translateX(float xAmount) {
        if ((getX() + xAmount) < Gdx.graphics.getWidth() - this.getWidth() && (getX() + xAmount) > 0)
            super.translateX(xAmount);
    }

    @Override
    public void translateY(float yAmount) {
        if ((getY() + yAmount) < (Gdx.graphics.getHeight() - 2 * this.getHeight()) / 2 && (getY() + yAmount) > 0)
            super.translateY(yAmount);
    }
}

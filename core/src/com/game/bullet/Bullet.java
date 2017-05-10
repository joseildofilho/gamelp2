package com.game.bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.Updatable;
import com.game.game.Ship;

/**
 * Created by root on 30/03/17.
 */
/*
* todo provavelmente isso aqui seria melhor implementado em uma factory
* */
public class Bullet extends Sprite implements Updatable {

    private float x, y;
    private static Texture bullet = new Texture("0.png");
    private Ship ship;
    private boolean ally;

    public Bullet(float x, float y, Ship ship, boolean ally) {
        super(bullet);
        this.ship = ship;
        this.ally = ally;
        //todo trazer todos os calculos relacionados a nave para k
        if (this.ally) {
            this.x = x;
            this.x -= bullet.getWidth() / 2;
            this.y = y;
        } else {
            this.x = x - ship.getWidth() / 2;
            this.x -= bullet.getWidth() / 2;
            this.y = y - ship.getHeight() - bullet.getHeight();
        }
    }

    @Override
    public void update() {
        if (ally) {
            this.setPosition(x, y++);
            if (y > 700) {
                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        else {
            this.setPosition(x, y--);
            if (y - bullet.getHeight() < 0) {
                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

    public boolean checkCollision() {
        if (this.getBoundingRectangle().overlaps(ship.getBoundingRectangle())) {
            System.out.println("eu nave " + ship.getId() + " fui baleada pela outra");
            return true;
        }
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Bullets.removeBullet(this);
    }
}

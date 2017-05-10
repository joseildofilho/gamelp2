package com.game.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.game.Ship;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by root on 30/03/17.
 */
public class Bullets {

    private static volatile ConcurrentLinkedQueue<Bullet> bullets;
    private static SpriteBatch spriteBatch;
    private static Ship ally, enemy;

    static {
        bullets = new ConcurrentLinkedQueue<Bullet>();
    }

    private Bullets() {
    }

    public static void shoot(float x, float y, boolean local) {
        Bullet b;
        if (!local)
            b = new Bullet(600 - x, 700 - y, ally, false);
        else {
            b = new Bullet(x, y, enemy, true);
        }
        bullets.add(b);
    }

    public static void setBatch(SpriteBatch s) {
        spriteBatch = s;
    }


    public static void update() {
        for (Bullet s : bullets) {
            s.update();
            if (s.checkCollision()) System.out.println("e morreu");
            s.draw(spriteBatch);
        }
    }

    public static void addShip(Ship ally, Ship enemy) {
        Bullets.enemy = enemy;
        Bullets.ally = ally;
    }

    protected static void removeBullet(Bullet bullet) {
        Bullets.bullets.remove(bullet);
        System.out.println("numero de balas sendo processadas na corte:"+bullets.size());
    }
}

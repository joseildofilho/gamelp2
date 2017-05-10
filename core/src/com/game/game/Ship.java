package com.game.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.bullet.Bullets;

/**
 * Created by root on 10/04/17.
 */
public class Ship extends Sprite {
    private String id;
    private boolean local;
    public Ship(String id,String texture) {
        super(new Texture(texture));
        this.id = id;
        System.out.println("The Ship: "+id+" is created with: "+texture+" texture"+local);
        local = id.equals("ally");
    }

    public void dispose() {
        getTexture().dispose();
    }

    public void shoot() {
        Bullets.shoot(getX()+(getWidth()/2),getY()+getHeight(),local);
    }

    public String getId() {
        return id;
    }
}

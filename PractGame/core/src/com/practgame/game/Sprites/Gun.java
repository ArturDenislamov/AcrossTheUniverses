package com.practgame.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Gun extends Sprite {
    public String name;
    public float bulletVelocity;

    public int bulletsAmount;
    private boolean locked;
    // int damage; // under construction
    public Gun(String name){
        this.name = name;
        locked = true;
        bulletsAmount = 100; // TODO is this needed? (think that maybe yes)
        switch (Guns.valueOf(name.toUpperCase())){ // each gun has its unique features
            case ACR130:
                bulletsAmount = 5;
                bulletVelocity = 200;
                break;
            case REDLINE:
                bulletsAmount = 15;
                bulletVelocity = 250;
                break;
            case INFINITY:
                bulletsAmount = 1;
                bulletVelocity = 245;
        }
    }

    public void unlock(){
        locked = false;
    }

    public void lock(){ locked = true; }

    public boolean isLocked(){ return locked; }

    public enum Guns{
        ACR130, REDLINE, INFINITY
    }
}

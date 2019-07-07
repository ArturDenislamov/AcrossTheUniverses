package com.practgame.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Gun extends Sprite {

    public int bulletsAmount;
    private boolean locked;
    int damage;
    public String name;
    public float bulletVelocity;

    public Gun(String name){
        this.name = name;
        locked = true;
        switch (Guns.valueOf(name.toUpperCase())){ // each gun has its unique features
            case ACR130:
                bulletsAmount = 5;
                bulletVelocity = 200;
                damage = 1;
                break;
            case REDLINE:
                bulletsAmount = 15;
                bulletVelocity = 250;
                damage = 1;
                break;
            case INFINITY:
                bulletsAmount = 1;
                bulletVelocity = 245;
                damage = 1;
                break;
            case PLATFORMGUN:
                bulletsAmount = 1000;
                bulletVelocity = 30;
                damage = 2;
        }
    }

    public void unlock(){
        locked = false;
    }

    public void lock(){ locked = true; }

    public boolean isLocked(){ return locked; }

    public enum Guns{
        ACR130, REDLINE, INFINITY, PLATFORMGUN
    }
}

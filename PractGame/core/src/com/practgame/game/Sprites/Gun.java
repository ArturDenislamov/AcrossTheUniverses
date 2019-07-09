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
            case ACR130: // default gun
                bulletsAmount = 5;
                bulletVelocity = 200;
                damage = 1;
                break;
            case REDLINE:
                bulletsAmount = 15;
                bulletVelocity = 250;
                damage = 1;
                break;
            case INFINITY: // it has infinite number of bullets
                bulletsAmount = 1;
                bulletVelocity = 245;
                damage = 1;
                break;
            case ACCELERATOR: // it accelerates the player, doesn't shoot bullets
                bulletsAmount = 4;
                break;
            case PLATFORMGUN:  // under construction, player can stand on its large and slow bullets
                bulletsAmount = 1;
                bulletVelocity = 30;
                damage = 2;
                break;
            default:
                bulletsAmount = 0;
                bulletVelocity = 0;
                damage = 0;
                break;
        }
    }

    public void unlock(){
        locked = false;
    }

    public void lock(){ locked = true; }

    public boolean isLocked(){ return locked; }

    public enum Guns{
        ACR130, REDLINE, INFINITY, PLATFORMGUN, ACCELERATOR, TPSL2
    }
}

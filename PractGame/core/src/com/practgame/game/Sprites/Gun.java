package com.practgame.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Gun extends Sprite {
    public String name;
    public float bulletVelocity;

    public int bulletsAmount;
    private boolean locked;
    // int damage;
    public Gun(String name){
        this.name = name;
        locked = true;
        bulletsAmount = 100;
        switch (Guns.valueOf(name.toUpperCase())){
            case ACR130:
                bulletsAmount = 5;
                bulletVelocity = 200;
                break;
            case REDLINE:
                bulletsAmount = 15;
                bulletVelocity = 250;
                break;
        }
    }

    public void unlock(){
        locked = false;
    }
    public void lock(){ locked = true; }

    public boolean isLocked(){ return locked; }

    public enum Guns{
        ACR130, REDLINE
    };
}

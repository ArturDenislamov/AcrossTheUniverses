package com.practgame.game.Tween;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import aurelienribon.tweenengine.TweenAccessor;


public class ImageAccessor implements TweenAccessor<Image> {
    public static final int ALPHA = 0;
    
    @Override
    public int getValues(Image image, int i, float[] floats) {
        switch(i){
            case ALPHA:
                floats[0] = image.getColor().a;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    // changing alpha value of an image, (fade in, fade out)
    @Override
    public void setValues(Image image, int i, float[] floats) {
            switch(i){
                case ALPHA:
                    image.setColor(image.getColor().r, image.getColor().g, image.getColor().b, floats[0]);
                    break;
                default:
                    assert false;
            }
    }
}

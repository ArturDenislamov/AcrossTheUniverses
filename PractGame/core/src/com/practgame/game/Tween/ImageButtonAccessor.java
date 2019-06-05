package com.practgame.game.Tween;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import aurelienribon.tweenengine.TweenAccessor;


public class ImageButtonAccessor implements TweenAccessor<ImageButton> {
    public static final int ALPHA = 0;

    @Override
    public int getValues(ImageButton imageButton, int i, float[] floats) {
        switch(i){
            case ALPHA:
                floats[0] = imageButton.getColor().a;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    // changing alpha value of an image, (in imageButton), (fade in, fade out)
    @Override
    public void setValues(ImageButton imageButton, int i, float[] floats) {
        switch(i){
            case ALPHA:
                imageButton.setColor(imageButton.getColor().r, imageButton.getColor().g, imageButton.getColor().b, floats[0]);
                break;
            default:
                assert false;
        }
    }
}
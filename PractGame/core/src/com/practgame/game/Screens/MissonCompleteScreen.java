package com.practgame.game.Screens;

import com.badlogic.gdx.Screen;
import com.practgame.game.PractGame;

public class MissonCompleteScreen implements Screen {
    private PractGame maingame;



    public MissonCompleteScreen(PractGame game){
        maingame = game;
    }

    @Override
    public void show() {
            switch (maingame.worldType){
                case 1:

                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

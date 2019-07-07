package com.practgame.game.Ai;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.utils.Array;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Sprites.Enemy;
import com.practgame.game.Sprites.Player;
import com.practgame.game.Sprites.Soldier;


// class for behavior trees
public class PlayerVisibleCondition extends LeafTask<PlayScreen> {

    private PlayScreen playScreen = null;
    private Soldier soldier = null;

    public PlayerVisibleCondition() {}

    public PlayerVisibleCondition(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    @Override
    public Status execute() {
        if(playScreen == null){
            playScreen = super.getObject();
        }

        if(soldier == null){
            soldier = playScreen.creator.getSoldier();
        }


        if(playScreen != null && soldier != null){
            Player player = playScreen.player;

                    if (Math.abs(soldier.b2body.getPosition().y - player.b2body.getPosition().y) <= 0.1 &&
                   Math.abs(soldier.b2body.getPosition().x - player.b2body.getPosition().x) <= 1){ // 0.1 is 10 pixels
                        return Status.SUCCEEDED;
                    }else{
                        return Status.FAILED;
                    }
                }
             return Status.FAILED;
        }

    @Override
    protected Task<PlayScreen> copyTo(Task<PlayScreen> task) {
        return new PlayerVisibleCondition(playScreen);
    }
}

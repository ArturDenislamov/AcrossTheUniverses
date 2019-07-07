package com.practgame.game.Ai;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Sprites.Player;
import com.practgame.game.Sprites.Soldier;

public class EnemyAttack extends LeafTask <PlayScreen> {

    private PlayScreen playScreen = null;

    private Soldier soldier = null;

    public EnemyAttack() {}

    public EnemyAttack(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    @Override
    public Status execute() {
        if(playScreen == null) {
            playScreen = super.getObject();
        }
        if(soldier == null && playScreen != null) {
            soldier = playScreen.creator.getSoldier();
        }

            if (playScreen != null && soldier != null) {
                Player player = playScreen.player;

                if(Math.abs(player.b2body.getPosition().x - soldier.b2body.getPosition().x) >= 0.1) { // 0.1 is 10 pixels
                    soldier.b2body.setLinearVelocity(new Vector2
                            (-0.75f * (soldier.b2body.getPosition().x - player.b2body.getPosition().x), 0));
                } else {
                    soldier.b2body.setLinearVelocity(new Vector2(0,0));
                    return Status.SUCCEEDED;
                }
                return Status.RUNNING;
            }

        return Status.FAILED;
    }

    @Override
    protected Task<PlayScreen> copyTo(Task<PlayScreen> task) {
        return new EnemyAttack(playScreen);
    }
}

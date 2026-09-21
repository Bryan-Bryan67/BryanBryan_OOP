package com.Bryan.frontend.objects.enemies;

import com.Bryan.frontend.objects.Player;
import com.Bryan.frontend.objects.Collidable;
import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy{
//Pre-CS2 asnwer: I think the type of inheritence is multilevel inheritence since it inherits from GameObject to Enemy and then to Boss
    public Boss(String name, int hp) {
        super(380, 400, 48, 48,Color.BLUE,name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48, 48,Color.BLUE, name,hp,5000L);
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if(other.getClass()== Player.class){
            System.out.println("Player touches boss");
        }
        // TODO: Print "Player touches boss"
    }
}

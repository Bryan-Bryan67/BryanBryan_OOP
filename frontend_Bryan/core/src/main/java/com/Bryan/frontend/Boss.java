package com.Bryan.frontend;

import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy{
//Pre-CS2 asnwer: I think the type of inheritence is multilevel inheritence since it inherits from GameObject to Enemy and then to Boss
    public Boss(String name, int hp) {
        super(380, 400, 48, 48,0,name, hp, Color.BLUE);
        this.name = name;
        this.hp = hp;
        this.maxHp= hp;
        this.scoreValue=5000L;
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48, 48,Color.BLUE, name,hp,5000L);
        this.name = name;
        this.hp = hp;
        this.maxHp= hp;
        this.scoreValue=5000L;
    }
}

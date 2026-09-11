package com.Bryan.frontend;

import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy{
//Pre-CS2 asnwer: I think the type of inheritence is multilevel inheritence since it inherits from GameObject to Enemy and then to Boss
    public Boss(String name, int hp) {
        super(380, 400, 48, 48,0, Color.BLUE);
        this.name = name;
        this.hp = hp;
        this.maxHp= hp;
        this.scoreValue=100;
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48, 48,0,Color.BLUE);
        this.name = name;
        this.hp = hp;
        this.maxHp= hp;
        this.scoreValue=100;
    }
}

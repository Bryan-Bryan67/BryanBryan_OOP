package com.Bryan.frontend;

import com.badlogic.gdx.graphics.Color;

public class Fairy extends Enemy {
    //Pre-CS2 asnwer: I think the type of inheritence is multilevel inheritence since it inherits from GameObject to Enemy and then to Fairy
    public Fairy(String name, int hp) {
        super(150, 380, 24, 24,Color.PINK, "Fairy", ,500L);
        this.name = name;
        this.hp = hp;
        this.maxHp= hp;
        this.scoreValue=500L;
    }

    public Fairy(float x, float y, String name, int hp) {
        super(x, y, 24, 24,0,Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp= hp;
        this.scoreValue=500L;
    }
}

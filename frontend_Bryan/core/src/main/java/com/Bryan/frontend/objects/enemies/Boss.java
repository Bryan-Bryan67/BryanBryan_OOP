package com.Bryan.frontend.objects.enemies;

import com.Bryan.frontend.objects.patterns.entityStrategy.FixedMovement;
import com.Bryan.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy{
    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
        // TODO (2 points): Inject default Boss strategies via setters
        // 1. Set Shooting Pattern as SpreadShot with speed 200f, 3 bullets, and 30f spreadAngleDegrees.
        // 2. Set Movement Pattern as FixedMovement.
        shootingPattern=new SpreadShot(200f, 3, 30f);
        movementPattern=new FixedMovement();
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
        // TODO (2 points): Perform the same strategy injection in the custom coordinate constructor
        shootingPattern=new SpreadShot(200f, 3, 30f);
        movementPattern=new FixedMovement();
    }
}

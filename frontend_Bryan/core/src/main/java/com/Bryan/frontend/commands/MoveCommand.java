package com.Bryan.frontend.commands;

import com.Bryan.frontend.objects.Player;
import com.Bryan.frontend.systems.BulletManager;

public class MoveCommand implements Command {
    private float dx;
    private float dy;
    private Player player;
    public MoveCommand(float dx, float dy) {
        // TODO: Initialize dx and dy attributes from constructor parameters
        this.dx=dx;
        this.dy=dy;
    }

    public void set(float dx, float dy) {
        // TODO: Set dx & dy according to parameters
        this.dx=dx;
        this.dy=dy;
    }

    // TODO: Override `execute` with the following business logic:
    // - Call the player's move(dx, dy) method
    // - Update the player's animation state
    @Override
    public void execute(Player player, BulletManager bulletManager){
        player.move(dx, dy);
        player.updateAnimationState(dx);
    }
}

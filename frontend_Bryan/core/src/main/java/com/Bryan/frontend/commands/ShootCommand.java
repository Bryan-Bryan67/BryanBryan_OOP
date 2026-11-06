package com.Bryan.frontend.commands;

import com.Bryan.frontend.objects.Player;
import com.Bryan.frontend.systems.BulletManager;

public class ShootCommand implements Command {
    private Player player;
    // TODO: Override `execute` with the following business logic:
    // - Check if player exists (is not null)
    // - If so, call the player's shootBullet(bulletManager) method
    @Override
    public void execute(Player player, BulletManager bulletManager){
        if (player!=null && bulletManager!=null){
            player.shootBullet(bulletManager);
        }
    }
}

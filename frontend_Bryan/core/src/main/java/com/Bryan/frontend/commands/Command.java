package com.Bryan.frontend.commands;

import com.Bryan.frontend.objects.Player;
import com.Bryan.frontend.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}

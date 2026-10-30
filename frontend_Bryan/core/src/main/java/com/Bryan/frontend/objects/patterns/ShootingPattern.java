package com.Bryan.frontend.objects.patterns;

import com.Bryan.frontend.systems.BulletManager;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}

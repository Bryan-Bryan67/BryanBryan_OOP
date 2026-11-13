package com.Bryan.frontend.objects.patterns.shootingStrategy;

import com.Bryan.frontend.systems.BulletManager;

public interface ShootingPattern {
    // TODO (6 points): Declare the `execute` method with the contract and parameters described above.
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}

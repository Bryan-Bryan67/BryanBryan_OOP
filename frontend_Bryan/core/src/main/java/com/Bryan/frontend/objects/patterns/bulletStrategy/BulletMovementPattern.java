package com.Bryan.frontend.objects.patterns.bulletStrategy;

import com.Bryan.frontend.objects.bullets.Bullet;

public interface BulletMovementPattern {
    // TODO (4 points): Declare the `move` method with the contract and parameters described above.
    void move(Bullet bullet, float delta);
}

package com.Bryan.frontend.objects.patterns.entityStrategy;

import com.Bryan.frontend.objects.GameObject;

public interface EntityMovementPattern {
    // TODO (4 points): Declare the `move` method with the contract and parameters described above.
    void move(GameObject entity, float delta);
}

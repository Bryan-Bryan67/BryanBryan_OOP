package com.Bryan.frontend.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.Bryan.frontend.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;

public class InputHandler {

    private int keyShoot = Input.Keys.Z;
    private int keyBomb = Input.Keys.X;
    private int keyFocus = Input.Keys.SHIFT_LEFT;
    private Command shootCommand = new ShootCommand();
    private BombCommand bombCommand = new BombCommand();
    private Command focusOn = new FocusCommand(true);
    private Command focusOff = new FocusCommand(false);
    private final MoveCommand moveCommand = new MoveCommand(0, 0);
    private final List<Command> activeCommands = new ArrayList<>();
    public InputHandler() {}
    public InputHandler(List<GameObject> entities) {
        this.bombCommand.setEntities(entities);
    }

    public void setKeyShoot(int keyShoot) { this.keyShoot = keyShoot; }
    public void setKeyBomb(int keyBomb) { this.keyBomb = keyBomb; }
    public void setKeyFocus(int keyFocus) { this.keyFocus = keyFocus; }
    public int getKeyShoot() { return keyShoot; }
    public int getKeyBomb() { return keyBomb; }
    public int getKeyFocus() { return keyFocus; }

    public void bindShootCommand(Command command) { this.shootCommand = command; }
    public void bindBombCommand(BombCommand command) { this.bombCommand = command; }
    public void bindFocusCommands(Command on, Command off) {
        this.focusOn = on;
        this.focusOff = off;
    }

    public void setEntities(List<GameObject> entities) {
        this.bombCommand.setEntities(entities);
    }

    public List<Command> handleInput(float delta) {
        activeCommands.clear();
        if (Gdx.input == null) return activeCommands;
        boolean isFocusHeld = Gdx.input.isKeyPressed(keyFocus) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
        activeCommands.add(isFocusHeld ? focusOn : focusOff);
        float dx = 0;
        float dy = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx += 1;
        float speedMult = isFocusHeld ? 0.45f : 1.0f;
        moveCommand.set(dx * delta * speedMult, dy * delta * speedMult);
        activeCommands.add(moveCommand);
        if (Gdx.input.isKeyPressed(keyShoot)) {
            activeCommands.add(shootCommand);
        }
        if (Gdx.input.isKeyJustPressed(keyBomb)) {
            activeCommands.add(bombCommand);
        }
        return activeCommands;
    }
}

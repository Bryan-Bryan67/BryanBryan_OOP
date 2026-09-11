package com.Bryan.frontend;

import com.badlogic.gdx.graphics.Color;

public class Enemy extends GameObject {
    public String name;
    public int hp;
    public int maxHp;

    protected long scoreValue;
    public Enemy(String name, int hp) {
        super(280, 40, 24, 24,0,Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp= hp;
        this.scoreValue=100;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue) {
        super(x, y, 24, 24,0,Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp= hp;
    }


    public boolean takeDamage(int damage) {
        setHp(getHp() - damage);
        return true;
    }

    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        System.out.println(name + " unleashes bullet barrage on " + player.name + "!");
        // 2. Call the Player's takeDamage() method using the given damage.
        player.takeDamage(damage);
    }

    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
      return this.hp > 0;
    }

    public String getName(){
        return name;
    }

    public void setName(){
        this.name=name;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public long getScoreValue(){
        return scoreValue;
    }

    public void setScoreValue(){
        this.scoreValue=scoreValue;
    }
}

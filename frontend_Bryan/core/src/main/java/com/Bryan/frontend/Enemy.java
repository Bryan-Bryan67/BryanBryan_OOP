package com.Bryan.frontend;

public class Enemy {
    public String name;
    public int hp;
    public int maxHp;

    public Enemy(String name, int hp) {
        this.name = name;
        this.hp = hp;
    }


    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        this.hp = this.hp - damage;
        // 2. HP must not go below 0.
        if (this.hp < 0) {
            this.hp = 0;
        }
        // 3. Display the current HP in the format: [EnemyName] took [damage] damage! HP: [currentHP]/[maxHP]
        System.out.println(this.name + " took " + damage + "damage! HP:" + this.hp + "/" + this.maxHp);
        // 4. If HP reaches 0, display that the Enemy has been defeated, in the format: [EnemyName] was defeated!
        if (this.hp = 0) {
            System.out.println(this.name + " has been defeated!");
        }
    }

    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        System.out.println(this.name + " unleashes bullet barrage on " + player + "!");
        // 2. Call the Player's takeDamage() method using the given damage.
        takeDamage(damage);
    }

    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if (this.hp > 0) {
            true;
        } else if (this.hp <= 0) {
            false;
        }
    }
}

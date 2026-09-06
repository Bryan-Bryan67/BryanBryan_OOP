package com.Bryan.frontend;

public class Player {
    public String name;
    public int hp;
    public int power;
    public int spellCards;

    public Player(String name, int hp, int power, int spellCards) {
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }


    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        this.hp -= damage;
        // 2. HP must not become negative.
        if (this.hp < 0) {
            this.hp = 0;
        }
        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        System.out.println(name + " took " + damage + " damage! remaining HP: " + this.hp);

        // 4. If HP reaches 0, display a message that the Player has been defeated.
        if (this.hp==0){
            System.out.println(name + " has been defeated");
        }
    }

    public void shoot(Enemy target) {
        // 1. Create an int named damage, calculated by adding 10 to power.
        int damage = 10 + power;
        // 2. Display information that the Player is shooting the Enemy, in the format: [name] shoots [TargetName] dealing [damage] DMG!
        System.out.println(name + " shoots " + target.name + " deals " + damage + " DMG ");
        // 3. Call the Enemy object's takeDamage() method.
        target.takeDamage(damage);
    }

    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
      return this.hp>0;
    }
}

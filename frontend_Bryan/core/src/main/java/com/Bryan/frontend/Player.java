package com.Bryan.frontend;

import com.badlogic.gdx.graphics.Color;

public class Player extends GameObject {
    public String name;
    public int hp;
    public int power;
    public int spellCards;

    public long score;
    public Player(String name, int hp, int power, int spellCards) {
        super(280,40,32,32,0,Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score=0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x,y,32,32,0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score=0;
    }

    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        this.hp -= damage;
        setHp(getHp() - damage);
        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        System.out.println(name + " took " + damage + " damage! remaining HP: " + this.hp);

        // 4. If HP reaches 0, display a message that the Player has been defeated.
        if (this.hp==0){
            System.out.println(name + " has been defeated");
        }
    }

    public void shoot(Enemy target) {
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
    }

    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
      return this.hp>0;
    }

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total" + this.score);
        }
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = Math.max(0, hp);
    }

    public String getName(){
        return name;
    }

    public void setPower(String name){
        this.name=name;
    }

    public int getPower(){
        return power;
    }

    public void setPower(int power){
        this.power=power;
    }

    public int getSpellCards(){
        return spellCards;
    }

    public void setSpellCards(int spellCards){
        this.spellCards=spellCards;
    }

    public long getScore(){
        return score;
    }

    public void collectItem(Item item) {
        System.out.println(getName() + " collected " + item.getItemType() + "!");
        if (item.getScoreValue() > 0) {
            addScore(item.getScoreValue());
        }
    }
}

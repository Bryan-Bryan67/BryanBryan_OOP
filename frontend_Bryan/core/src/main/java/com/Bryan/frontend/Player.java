package com.Bryan.frontend;

import com.Bryan.frontend.objects.GameObject;
import com.Bryan.frontend.objects.enemies.Enemy;
import com.Bryan.frontend.objects.items.Item;
import com.Bryan.frontend.objects.items.ItemType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.Bryan.frontend.objects.Collidable;
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


    @Override
    public void update(float delta) {
        if (Gdx.input != null) {
            // TODO: Check W / UP input   → y += speed * delta
            if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
                y+=speed*delta;
            }
            // TODO: Check S / DOWN input → y -= speed * delta
            if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                y -= speed * delta;
            }
            // TODO: Check A / LEFT input → x -= speed * delta
            if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                x -= speed * delta;
            }
            // TODO: Check D / RIGHT input → x += speed * delta
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                x += speed * delta;
            }
        }
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is an Item
        if (other instanceof Item){
            System.out.println("Player touches item");
            collectItem((Item) other);
        }
        // TODO: Print "Player touches items" then call collectItem((Item) other)
    }

    public void collectItem(Item item) {
        ItemType type = item.getItemTypeEnum();
        if (type != null) {
            switch (type) {
                case POWER -> {
                    // 1. Increase power by type.getPowerBonus() via this.power
                    this.power+=type.getPowerBonus();
                    // 2. Add score by item.getScoreValue() via addScore() (addScore() already automatically prints "gained X pts!")
                    item.getScoreValue(addscore());
                    // 3. Print: [name] collected POWER item! Power increased to [power]
                    System.out.println(name+" collected POWER item! Power increased to "+power);
                }
                case POINT -> {
                    // 1. Add score by item.getScoreValue() via addScore()
                    item.getScoreValue(addscore());
                    // 2. Print: [name] collected POINT item!
                    System.out.println(name+" collected POINT item!");
                }
                case BOMB -> {
                    // 1. Increase spellCards by 1
                    spellCards+=1;
                    // 2. Add score by item.getScoreValue() via addScore()
                    item.getScoreValue(addscore());
                    // 3. Print: [name] collected BOMB item! SpellCards: [spellCards]
                    System.out.println(name+"collected BOMB item! Spell cards: "+spellCards);
                }
                case LIFE -> {
                    // 1. Increase hp by 20
                    hp+=20;
                    // 2. Add score by item.getScoreValue() via addScore()'
                    item.getScoreValue(addScore());
                    // 3. Print: [name] collected LIFE item! HP: [hp]
                    System.out.println(name+" collected LIFE item! HP: " +hp);
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }
    }
}

package com.Bryan.frontend.objects;

import com.Bryan.frontend.objects.bullets.Bullet;
import com.Bryan.frontend.objects.enemies.Enemy;
import com.Bryan.frontend.objects.items.Item;
import com.Bryan.frontend.objects.items.ItemType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

public class Player extends GameObject {
    public String name;
    public int hp;
    public int power;
    public int spellCards;
    public long score;
    public Player(String name, int hp, int power, int spellCards) {
        super(280,40,32,32,200f,Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score=0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x,y,32,32,200f, Color.RED);
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
        boolean defeated= target.takeDamage(damage);
        if(defeated){
            addScore(target.getScoreValue());
        }
    }

    public Bullet shootBullet() {
        int damage = 10 + power;
        System.out.println(name + " shoots bullet dealing " + damage + " DMG!");
        // TODO: return a new Bullet positioned at the top-center of the Player
        // (x + width/2 - 4, y + height), with BulletType.AMULET as its type,
        // and the damage calculated above
        return new Bullet(x+width/2-4, y+height, BulletType.AMULET, damage);
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

    public long getScoreValue(){
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

    public void moveUp(float delta) {
        this.y+=speed*delta;
    }
    public void moveDown(float delta){
        this.y-=speed*delta;
    }
    public void moveLeft(float delta){
        this.x-=speed*delta;
    }
    public void moveRight(float delta){
        this.x+=speed*delta;
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
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected POWER item! Power increased to [power]
                    System.out.println(name+" collected POWER item! Power increased to "+power);
                }
                case POINT -> {
                    // 1. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 2. Print: [name] collected POINT item!
                    System.out.println(name+" collected POINT item!");
                }
                case BOMB -> {
                    // 1. Increase spellCards by 1
                    spellCards+=1;
                    // 2. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected BOMB item! SpellCards: [spellCards]
                    System.out.println(name+"collected BOMB item! Spell cards: "+spellCards);
                }
                case LIFE -> {
                    // 1. Increase hp by 20
                    hp+=20;
                    // 2. Add score by item.getScoreValue() via addScore()'
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected LIFE item! HP: [hp]
                    System.out.println(name+" collected LIFE item! HP: " +hp);
                }
            }
            item.destroy();
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }

        if (item.isDestroyed()) return; // Prevent the item from being collected twice in the same frame
        // ... switch-case for the item type that you created previously ...
        // TODO: Mark this item as destroyed so it can later be removed by the Iterator
        // Call the item's destroy() method here
    }
}

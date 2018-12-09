package com.ceng453.frontend;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {

    private double pos_x, pos_y;
    private double velocity_x, velocity_y;
    private int id;
    private int hitPointsLeft;
    private int damage;
    private int width, height;
    private boolean isCleared;

    private Image sprite;

    public GameObject(Image sprite, int width, int height) {
        this.sprite = sprite;
        this.pos_x = 0;
        this.pos_y = 0;
        this.velocity_x = 0;
        this.velocity_y = 0;
        this.id = 0;
        this.hitPointsLeft = 0;
        this.damage = 0;
        this.width = width;
        this.height = height;
        this.isCleared = false;
    }

    public void initialize( int hitPointsLeft, int damage ) {
        this.hitPointsLeft = hitPointsLeft;
        this.damage = damage;
    }

    public GameObject hitBy(GameObject hitter )
    {
        hitPointsLeft -= hitter.getDamage();
        hitter.setCleared(true);
        if( hitPointsLeft <= 0 ) {
            isCleared = true;
            return EffectFactory.create(this instanceof Bullet?Effect.BulletExplosion:Effect.ShipExplosion, getPositionX()+getWidth()/2.0,getPositionY()+getWidth()/2.0);
        }
        return null;
    }

    public void setPosition(double x, double y) {
        this.pos_x = x;
        this.pos_y = y;
    }

    public void render(GraphicsContext context) {
        context.drawImage( sprite, pos_x, pos_y, width, height );
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract GameObject update(double elapsedTime, long currentCycleNumber);

    public double getPositionX() {
        return pos_x;
    }

    public void setPositionX(double pos_x) {
        this.pos_x = pos_x;
    }

    public double getPositionY() {
        return pos_y;
    }

    public void setPositionY(double pos_y) {
        this.pos_y = pos_y;
    }

    public double getVelocityX() {
        return velocity_x;
    }

    public void setVelocityX(double velocity_x) {
        this.velocity_x = velocity_x;
    }

    public double getVelocityY() {
        return velocity_y;
    }

    public void setVelocityY(double velocity_y) {
        this.velocity_y = velocity_y;
    }

    public int getHitPointsLeft() {
        return hitPointsLeft;
    }

    public void setHitPointsLeft(int hitPointsLeft) {
        this.hitPointsLeft = hitPointsLeft;
    }

    public boolean isCleared() {
        return isCleared;
    }

    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }

    public int getDamage() {
        return damage;
    }
}

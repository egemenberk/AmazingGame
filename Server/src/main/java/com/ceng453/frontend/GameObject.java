package com.ceng453.frontend;



public abstract class GameObject extends Object{
    private int pos_x, pos_y;
    private int velocity_x, velocity_y;
    private int id;
    private int hitPointsLeft;
    private int damage;

    // Returns a game object in case it generates a thing, like a bullet or another ship
    public abstract GameObject update( long currentCycleNumber );

    public abstract String serialize();
}

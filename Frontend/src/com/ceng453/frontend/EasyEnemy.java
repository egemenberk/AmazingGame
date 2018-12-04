package com.ceng453.frontend;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EasyEnemy implements GameObject {

    private double pos_x, pos_y;
    private double velocity_x, velocity_y;
    private int id;
    private int hitPointsLeft;
    private int damage;
    private int width, height;

    private Image sprite;

    public EasyEnemy(Image sprite, int width, int height) {
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
    }

    @Override
    public void initialize( int id, int hitPointsLeft, int damage ) {
        this.id = id;
        this.hitPointsLeft = hitPointsLeft;
        this.damage = damage;
    }

    @Override
    public void setPosition(int x, int y) {
        this.pos_x = x;
        this.pos_y = y;
    }

    @Override
    public void render(GraphicsContext context) {
        context.drawImage( sprite, pos_x, pos_y, width, height );
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        if( currentCycleNumber%100 >= 50 )
            velocity_x = 30.0;
        else
            velocity_x = -30.0;

        pos_x += velocity_x * elapsedTime;
        pos_y += velocity_y * elapsedTime;

        return null;
    }


}

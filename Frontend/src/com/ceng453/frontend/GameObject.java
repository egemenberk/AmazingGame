package com.ceng453.frontend;


import javafx.scene.canvas.GraphicsContext;

public interface GameObject{

    // Init method
    void initialize( int id, int hitPointsLeft, int damage );

    // Returns a game object in case it generates a thing, like a bullet or another ship
    GameObject update(double elapsedTime, long currentCycleNumber );

    void setPosition( int x, int y );

    void render( GraphicsContext context );

}

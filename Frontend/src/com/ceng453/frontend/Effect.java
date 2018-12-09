package com.ceng453.frontend;

import javafx.scene.image.Image;

public class Effect extends GameObject{

    public static final int ShipExplosion = 0;
    public static final int BulletExplosion = 1;


    private long creationCycle = -1;

    public Effect(Image sprite, int width, int height) {
        super(sprite, width, height);


    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        if(creationCycle == -1)
            creationCycle = currentCycleNumber;
        if( currentCycleNumber - creationCycle > 30 )
            setCleared(true);
        return null;
    }
}

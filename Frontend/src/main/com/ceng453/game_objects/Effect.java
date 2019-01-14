package main.com.ceng453.game_objects;

import javafx.scene.image.Image;

import java.util.List;


/*
An effect class, extending GameObject. Purpose is to create effects like explosions etc
 */
public class Effect extends GameObject {

    public static final int ShipExplosion = 0; // Types of explosions as statics
    public static final int BulletExplosion = 1;
    private static final int EffectDuration = 35;

    private long creationCycle = -1;

    public Effect(Image sprite, int width, int height) {
        super(sprite, width, height);
    }

    @Override
    public List<GameObject> update(double elapsedTime, long currentCycleNumber) {
        if(creationCycle == -1)
            creationCycle = currentCycleNumber; // When first created, update creation time to count
                                                // EffectDuration cycles until cleared

        if( currentCycleNumber - creationCycle > EffectDuration ) // After EffectDuration cycles, clear
            setCleared();
        else if( currentCycleNumber - creationCycle > 5 ){ // Calling scale method to shring the explosion effect
            scale(0.95);
        }
        return null; // Effects does not create any new GameObjects
    }
}

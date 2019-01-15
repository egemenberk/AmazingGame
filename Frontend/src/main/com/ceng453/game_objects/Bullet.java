package main.com.ceng453.game_objects;

import javafx.scene.image.Image;
import main.com.ceng453.ApplicationConstants;

import java.util.List;


/*
 * Bullet class, extending GameObject
 */
public class Bullet extends GameObject {

    public static final int RegularUserBullet = 0;
    public static final int AlienBullet = 1;
    public static final int HardAlienBullet = 2;
    public static final int ServerTickDrivenUserBullet = 3;
    public static final int ServerTickDrivenRivalBullet = 4;


    public Bullet(Image sprite, int width, int height) {
        super(sprite, width, height);
        setVelocityX(0);
    }

    // Just change positions using x = v * t
    // If we quit the screen, remove the bullet
    public List<GameObject> update(double elapsedTime, long currentCycleNumber) {
        setPositionY( getPositionY() + getVelocityY()*elapsedTime );
        setPositionX( getPositionX() + getVelocityX()*elapsedTime );
        if( getPositionY()+getHeight() < 0 || getPositionY() > ApplicationConstants.SCREEN_HEIGHT)
            setCleared();
        return null;
    }
}

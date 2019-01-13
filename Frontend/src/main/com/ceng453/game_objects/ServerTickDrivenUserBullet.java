package main.com.ceng453.game_objects;

import javafx.scene.image.Image;
import main.com.ceng453.ApplicationConstants;

public class ServerTickDrivenUserBullet extends Bullet {

    private long lastUpdatedCycle = -1;

    public ServerTickDrivenUserBullet(Image image, int width, int height) {
        super(image, width, height);
        setVelocityX(0);
        setVelocityY(-1);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        if( lastUpdatedCycle == -1 )
            lastUpdatedCycle = currentCycleNumber;

        long missingUpdates = currentCycleNumber - lastUpdatedCycle;
        setPositionY( getPositionY() + missingUpdates*(ApplicationConstants.UserBulletVelocity/1000.0*15.0) ); // TODO change to a variable

        lastUpdatedCycle = currentCycleNumber;
        return null;
    }
}

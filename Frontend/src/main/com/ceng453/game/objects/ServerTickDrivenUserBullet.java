package main.com.ceng453.game.objects;

import javafx.scene.image.Image;
import main.com.ceng453.ApplicationConstants;

import java.util.List;

public class ServerTickDrivenUserBullet extends Bullet {

    private long lastUpdatedCycle = -1;

    public ServerTickDrivenUserBullet(Image image, int width, int height) {
        super(image, width, height);
    }

    @Override
    public List<GameObject> update(double elapsedTime, long currentCycleNumber) {
        if( lastUpdatedCycle == -1 )
            lastUpdatedCycle = currentCycleNumber;

        long missingUpdates = currentCycleNumber - lastUpdatedCycle;
        setPositionY( getPositionY() + missingUpdates*(getVelocityY()/1000.0*ApplicationConstants.TICK_MS) );

        lastUpdatedCycle = currentCycleNumber;
        return null;
    }
}

package main.com.ceng453.game_objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.com.ceng453.ApplicationConstants;

public class UserShip extends GameObject {

    private static final double speedConstantOfUserShip = 4;
    private double flyingPositionX, flyingPositionY;

    public UserShip(Image sprite, int width, int height) {
        super(sprite, width, height);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        // Set velocity using differance between mouse x,y and pos_x,pos_y
        setVelocityX( -speedConstantOfUserShip*(getPositionX() - flyingPositionX+getWidth()/2.0 ) );
        setVelocityY( -speedConstantOfUserShip*(getPositionY() - flyingPositionY+getHeight()/2.0 ) );

        // calculate new position using x=v*t
        setPositionX( getVelocityX()*elapsedTime + getPositionX() );
        setPositionY( getVelocityY()*elapsedTime + getPositionY() );
        return null;
    }

    // This will be called on mouse click
    public GameObject shoot(){
        Bullet bullet = BulletFactory.create(Bullet.RegularUserBullet, getDamage());
        bullet.setPosition(getPositionX() + getWidth() / 2.0, getPositionY());
        return bullet;
    }

    public GameObject shoot( int bulletType ){
        Bullet bullet = BulletFactory.create(bulletType, getDamage());
        if( bulletType == Bullet.ServerTickDrivenUserBullet )
            bullet.setPosition(getPositionX() + getWidth() / 2.0, getPositionY() - ApplicationConstants.UserBulletHeight);
        else
            bullet.setPosition(getPositionX() + getWidth() / 2.0, getPositionY() + getHeight());
        return bullet;
    }

    public void setFlyingPositionX(double flyingPositionX) {
        this.flyingPositionX = flyingPositionX;
    }

    public void setFlyingPositionY(double flyingPositionY) {
        this.flyingPositionY = flyingPositionY;
    }

    // Overriding GameObject's render method since usership will not be rotated by its velocity.
    @Override
    public void render(GraphicsContext context) {
        context.drawImage( getSprite(), getPositionX(), getPositionY(), getWidth(), getHeight() );
        if ( ApplicationConstants.UserShipHealth > getHitPointsLeft() ) // Draw health bar
        {
            double health_ratio = getHitPointsLeft() / (double)ApplicationConstants.UserShipHealth; // Calculate health percentage
            double offsetX = ((1 - ApplicationConstants.HealthBarWidthCoefficient) * getWidth())/2; // Offset of rectangle to centralize
            // Draw Bounding Box
            context.strokeRect( getPositionX() + offsetX, getPositionY() + getHeight(),
                    ApplicationConstants.HealthBarWidthCoefficient * getWidth(), ApplicationConstants.HealthBarHeight);
            context.save(); // Save the context, we will change fill color
            context.setFill(Color.DARKOLIVEGREEN );
            // Fill the Bounding box with health_ratio percentage
            context.fillRect(getPositionX() + offsetX + 1, getPositionY() + getHeight() + 1,
                    ApplicationConstants.HealthBarWidthCoefficient * getWidth() * health_ratio, ApplicationConstants.HealthBarHeight - 1 );
            context.restore(); // Restore it back
        }
    }
}

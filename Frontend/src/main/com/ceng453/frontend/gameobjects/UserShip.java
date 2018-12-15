package main.com.ceng453.frontend.gameobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
        Bullet bullet = BulletFactory.create(Bullet.UserBullet, getDamage());
        bullet.setPosition(getPositionX() + getWidth() / 2.0, getPositionY());
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
    }
}

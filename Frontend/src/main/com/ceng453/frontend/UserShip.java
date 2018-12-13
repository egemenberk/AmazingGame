package main.com.ceng453.frontend;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class UserShip extends GameObject {

    private double flyingPositionX, flyingPositionY;

    public UserShip(Image sprite, int width, int height) {
        super(sprite, width, height);
    }

    @Override
    public GameObject update(double elapsedTime, long currentCycleNumber) {
        setVelocityX( -4*(getPositionX() - flyingPositionX+getWidth()/2.0 ) );
        setVelocityY( -4*(getPositionY() - flyingPositionY+getHeight()/2.0 ) );

        setPositionX( getVelocityX()*elapsedTime + getPositionX() );
        setPositionY( getVelocityY()*elapsedTime + getPositionY() );
        return null;
    }

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

    @Override
    public void render(GraphicsContext context) {
        context.drawImage( getSprite(), getPositionX(), getPositionY(), getWidth(), getHeight() );
    }
}

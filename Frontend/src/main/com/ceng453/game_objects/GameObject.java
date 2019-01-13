package main.com.ceng453.game_objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import main.com.ceng453.ApplicationConstants;

import java.io.Serializable;

/*
 * Every Object that is drawn and interractable is a GameObject
 * Namely, this class is base class of our objects in game world
 */
public abstract class GameObject implements Serializable {

    // Every object in our game will have
    // Position, Velocity, hp, dmg, width and height, bounty(for score calc.) and an image
    private double pos_x, pos_y;
    private double velocity_x, velocity_y;
    private int originalHitPoints;
    private int hitPointsLeft;
    private int damage;
    private int width, height;
    private boolean isCleared;
    private int bounty;

    private final transient Image sprite;

    protected GameObject(Image sprite, int width, int height) {
        this.sprite = sprite;
        this.pos_x = 0;
        this.pos_y = 0;
        this.velocity_x = 0;
        this.velocity_y = 0;
        this.hitPointsLeft = 0;
        this.damage = 0;
        this.width = width;
        this.height = height;
        this.isCleared = false;
    }

    public void setHitpointsAndDamage(int hitPointsLeft, int damage ) {
        this.hitPointsLeft = hitPointsLeft;
        this.originalHitPoints = hitPointsLeft;
        this.damage = damage;
    }

    // This method is called when an intersection is found, by game level
    public GameObject hitBy(GameObject hitter )
    {
        hitPointsLeft -= hitter.getDamage(); // reduce self's hp
        hitter.setCleared(); // Destroy hitter
        if( hitPointsLeft <= 0 ) { // If self.hp reduces to 0, destroy this one too
            isCleared = true;
            // Also, create Explosion effect
            return EffectFactory.create(this instanceof Bullet? Effect.BulletExplosion:Effect.ShipExplosion, getPositionX()+getWidth()/2.0,getPositionY()+getWidth()/2.0);
        }
        else
            return EffectFactory.create(this instanceof Bullet?Effect.BulletExplosion:Effect.ShipExplosion, hitter.getPositionX()+hitter.getWidth()/2.0,hitter.getPositionY()+hitter.getWidth()/2.0);
    }

    public void setPosition(double x, double y) {
        this.pos_x = x;
        this.pos_y = y;
    }

    // This is the lowest level render function in game
    // Game object will draw its image to the canvas passed from GameService
    public void render(GraphicsContext context) {
        // We will rotate images of GameObjects in their movement direction
        context.save();

        double middleX = getPositionX() + getWidth() / 2.0;
        double middleY = getPositionY() + getHeight() / 2.0;
        double angle = Math.atan2(-getVelocityX(), getVelocityY()); // Calculate the rotation angle in radians
        // Rotate the canvas centering the given X and Y
        Rotate r = new Rotate(Math.toDegrees(angle), middleX, middleY);
        context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

        context.drawImage(sprite, pos_x, pos_y, width, height); // Drawing self image

        context.restore(); // back to original state (before rotation)

        if (hitPointsLeft < originalHitPoints && originalHitPoints > ApplicationConstants.UserShipDamage ) // Draw health bar
        {
            double health_ratio = hitPointsLeft / (double)originalHitPoints; // Calculate health percentage
            double offsetX = ((1 - ApplicationConstants.HealtBarWidthCoefficent) * getWidth())/2; // Offset of rectangle to centralize
            // Draw Bounding Box
            context.strokeRect( pos_x + offsetX, pos_y + height, ApplicationConstants.HealtBarWidthCoefficent * width, ApplicationConstants.HealtBarHeight );
            context.save(); // Save the context, we will change fill color
            context.setFill(Color.RED );
            // Fill the Bounding box with health_ratio percentage
            context.fillRect(pos_x + offsetX + 1, pos_y + height + 1, ApplicationConstants.HealtBarWidthCoefficent * width * health_ratio, ApplicationConstants.HealtBarHeight -1);
            context.restore(); // Restore it back
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract GameObject update(double elapsedTime, long currentCycleNumber);

    public double getPositionX() {
        return pos_x;
    }

    void setPositionX(double pos_x) {
        this.pos_x = pos_x;
    }

    public double getPositionY() {
        return pos_y;
    }

    void setPositionY(double pos_y) {
        this.pos_y = pos_y;
    }

    double getVelocityX() {
        return velocity_x;
    }

    void setVelocityX(double velocity_x) {
        this.velocity_x = velocity_x;
    }

    double getVelocityY() {
        return velocity_y;
    }

    void setVelocityY(double velocity_y) {
        this.velocity_y = velocity_y;
    }

    public int getHitPointsLeft() {
        return hitPointsLeft;
    }

    public boolean isCleared() {
        return isCleared;
    }

    protected void setCleared() {
        isCleared = true;
    }

    int getDamage() {
        return damage;
    }

    public int getBounty() {
        return bounty;
    }

    void setBounty(int bounty) {
        this.bounty = bounty;
    }

    // Scale is used to shrink the GameObject
    protected void scale( double ratio ){
        pos_x += width*(1-ratio)/2.0;
        pos_y += height*(1-ratio)/2.0;
        width*=ratio;
        height*=ratio;
    }

    Image getSprite(){
        return sprite;
    }
}

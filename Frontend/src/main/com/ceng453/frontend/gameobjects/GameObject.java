package main.com.ceng453.frontend.gameobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import main.com.ceng453.frontend.gameeffects.Effect;
import main.com.ceng453.frontend.gameeffects.EffectFactory;

/*
 * Every Object that is drawn and interractable is a GameObject
 * Namely, this class is base class of our objects in game world
 */
public abstract class GameObject {

    // Every object in our game will have
    // Position, Velocity, hp, dmg, width and height, bounty(for score calc.) and an image
    private double pos_x, pos_y;
    private double velocity_x, velocity_y;
    private int hitPointsLeft;
    private int damage;
    private int width, height;
    private boolean isCleared;
    private int bounty;

    private Image sprite;

    public GameObject(Image sprite, int width, int height) {
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
        this.damage = damage;
    }

    // This method is called when an intersection is found, by game level
    public GameObject hitBy(GameObject hitter )
    {
        hitPointsLeft -= hitter.getDamage(); // reduce self's hp
        hitter.setCleared(true); // Destroy hitter
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

        double middleX = getPositionX() + getWidth()/2.0;
        double middleY = getPositionY() + getHeight()/2.0;
        double angle = Math.atan2(-getVelocityX(),getVelocityY()); // Calculate the rotation angle in radians
        // Rotate the canvas centering the given X and Y
        Rotate r = new Rotate(Math.toDegrees(angle), middleX, middleY);
        context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

        context.drawImage( sprite, pos_x, pos_y, width, height ); // Drawing self image

        context.restore(); // back to original state (before rotation)
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

    public void setPositionX(double pos_x) {
        this.pos_x = pos_x;
    }

    public double getPositionY() {
        return pos_y;
    }

    public void setPositionY(double pos_y) {
        this.pos_y = pos_y;
    }

    public double getVelocityX() {
        return velocity_x;
    }

    public void setVelocityX(double velocity_x) {
        this.velocity_x = velocity_x;
    }

    public double getVelocityY() {
        return velocity_y;
    }

    public void setVelocityY(double velocity_y) {
        this.velocity_y = velocity_y;
    }

    public int getHitPointsLeft() {
        return hitPointsLeft;
    }

    public void setHitPointsLeft(int hitPointsLeft) {
        this.hitPointsLeft = hitPointsLeft;
    }

    public boolean isCleared() {
        return isCleared;
    }

    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }

    public int getDamage() {
        return damage;
    }

    public int getBounty() {
        return bounty;
    }

    protected void setBounty(int bounty) {
        this.bounty = bounty;
    }

    // Scale is used to shrink the GameObject
    protected void scale( double ratio ){
        pos_x += width*(1-ratio)/2.0;
        pos_y += height*(1-ratio)/2.0;
        width*=ratio;
        height*=ratio;
    }

    protected Image getSprite(){
        return sprite;
    }
}

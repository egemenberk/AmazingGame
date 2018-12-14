package main.com.ceng453.frontend.gameeffects;

import main.com.ceng453.frontend.main.ApplicationConstants;

public class EffectFactory {

    private static final int ShipExplosionHeight = 50;
    private static final int ShipExplosionWidth = 50;
    private static final int BulletExplosionHeight = 50;
    private static final int BulletExplosionWidth = 50;

    public static Effect create( int effectType, double posX, double posY ){
        Effect effect = null;
        switch (effectType) {
            case Effect.ShipExplosion:
                effect = new Effect(ApplicationConstants.ExplosionImage, ShipExplosionWidth, ShipExplosionHeight);
                effect.setPosition(posX-ShipExplosionWidth/2.0, posY-ShipExplosionHeight/2.0);
                break;
            case Effect.BulletExplosion:
                effect = new Effect(ApplicationConstants.ExplosionImage, BulletExplosionWidth, BulletExplosionHeight);
                effect.setPosition(posX-BulletExplosionWidth/2.0, posY-BulletExplosionHeight/2.0);
                break;
        }
        return effect;
    }
}

package objs.pickups;

import game.DrawEngine;
import objs.pickups.Pickup;
import objs.pickups.AddAmmoEffect;

public class AmmoPickup extends Pickup {

    public AmmoPickup(float xPos, float yPos, float radius, int lifespan) {
        super(xPos, yPos, radius, lifespan);
        this.effect = new AddAmmoEffect();
    }

    @Override
    public void display(DrawEngine drawEngine) {
        float size = radius * 2;
        drawEngine.drawEllipse(drawEngine.parent.color(0,0,255), position.x, position.y, size, size);
    }


}
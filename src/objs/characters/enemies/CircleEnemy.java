package objs.characters.enemies;

import java.util.ArrayList;
import java.util.Random;

import game.Config;
import game.DrawEngine;
import game.GameContext;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import processing.core.PVector;

public class CircleEnemy extends Enemy {

    public PVector velocity;

    private float linearMag;

    public CircleEnemy(float xPos, float yPos, float radius, int health,float detectRadius, GameContext targets) {
        super(xPos, yPos, radius, health, detectRadius,targets);
        this.velocity = new PVector(0, 0);

        Random r = new Random();
        this.linearMag = r.nextFloat() * 0.08f + 0.01f;
    }

    @Override
    public void display(DrawEngine drawEngine) {
        drawEngine.drawC(super.position.x,super.position.y,super.radius,super.radius);
    }

    @Override
    public void integrate() {


        PVector targetPosition = getClosestTargetPosition();
        PVector linear = PVector.sub(targetPosition, position);
        linear.normalize().mult(linearMag);
        if (chase) {
            speedMultiplier = (float) (BASE_SPEED * 1.3);
            if (inBounds()) {
                velocity.add(linear);

                if (velocity.mag() > 2f) {
                    velocity.normalize().mult(2f);
                }

                position.add(velocity);
            }
        }
        else {
            if (detectPlayer(getClosestTarget())) {
                chase = true;
            } else {
                speedMultiplier /= 3;
                if (inBounds()) {
                    velocity.add(linear);

                    if (velocity.mag() > 2f) {
                        velocity.normalize().mult(2f);
                    }

                    position.add(velocity);
                }
            }
        }
    }

}
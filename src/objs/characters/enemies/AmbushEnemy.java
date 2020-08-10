package objs.characters.enemies;

import java.util.ArrayList;
import java.util.Random;

import game.DrawEngine;
import game.GameContext;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import processing.core.PVector;

/**
 * Enemy that moves towards where it guesses the player will be.
 * On every update step, there is a chance it will update its target position.
 *
 */
public class AmbushEnemy extends Enemy {

    public static final float DISTANCE = 200f;
    public static final int DELAY = 75;


    public PVector targetPosition;


    public AmbushEnemy(float xPos, float yPos, float radius, int health, float detectRadius, GameContext targets) {
        super(xPos, yPos, radius, health, detectRadius, targets);


        this.targetPosition = getClosestTargetPosition();
    }

    @Override
    public void display(DrawEngine drawEngine) {
        drawEngine.drawFly(super.position.x,super.position.y,super.radius,super.radius);
        //draw target
//		drawEngine.drawEllipse(drawEngine.parent.color(255), targetPosition.x, targetPosition.y, 5, 5);
    }

    @Override
    public void integrate() {
        PVector velocity = getVelocityToTarget(targetPosition);

        /* Random chance to update target position*/
        Random r = new Random();
        if (r.nextInt(DELAY) == 0 || velocity.mag() < 1f) {
            PlayerCharacter targetPlayer = getClosestTarget();

            /* Target position is based on the direction the player is currently heading */

            float targetX = getX(targetPlayer.position.x + (targetPlayer.right - targetPlayer.left) * DISTANCE);
            float targetY = getY(targetPlayer.position.y + (targetPlayer.down - targetPlayer.up) * DISTANCE);


            targetPosition = new PVector(targetX, targetY);

        }
            move(velocity);
    }

}
package objs.characters.enemies;

import java.util.ArrayList;
import java.util.Random;

import game.Config;
import game.DrawEngine;
import game.GameContext;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import processing.core.PVector;

/**
 * Basic enemy that just follows the player, always trying to reach the player's position.
 *
 */
public class BasicChaseEnemy extends Enemy {


    public BasicChaseEnemy(float xPos, float yPos, float radius, int health, float detectRadius, GameContext targets) {
        super(xPos, yPos, radius, health, detectRadius, targets);
    }

    @Override
    public void display(DrawEngine drawEngine) {
        drawEngine.drawB(super.position.x, super.position.y, super.radius, super.radius);
    }

    @Override
    public void integrate() {
        PVector targetPosition = getClosestTargetPosition();
        detectRadius += 5f;
        if (chase) {
            speedMultiplier = (float) (BASE_SPEED * 1.7);
            PVector velocity = getVelocityToTarget(targetPosition);
            if (inBounds()) {
                move(velocity);
            }
            if (tooFarAway() && !detectPlayer(super.getClosestTarget())) chase = false;
        } else {
            if (detectPlayer(getClosestTarget())) {
                chase = true;
            } else {
                speedMultiplier /= 2;
                PVector velocity = getVelocityToTarget(targetPosition);
                if (inBounds()) {
                    move(velocity);
                }
            }

        }
    }

}
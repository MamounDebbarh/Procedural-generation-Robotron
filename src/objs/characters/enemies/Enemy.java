package objs.characters.enemies;

import java.util.ArrayList;

import game.GameContext;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import processing.core.PVector;

public abstract class Enemy extends Character {

    public float detectRadius;
    public boolean chase;

    public Enemy(float xPos, float yPos, float radius, int health, float detectRadius, GameContext context) {
        super(xPos, yPos, radius, health, context);
        this.detectRadius = detectRadius;
        this.chase = false;

    }

    /**
     * Get the closest target player from the list of player targets.
     *
     * @return the closest player character.
     */
    protected PlayerCharacter getClosestTarget() {
        return super.context.player;
    }

    /**
     * @return
     */
    protected PVector getClosestTargetPosition() {
        return getClosestTarget().position.copy();
    }

    public boolean tooFarAway() {
        return PVector.dist(position, position) > detectRadius * 1.5;
    }

    public boolean detectPlayer(PlayerCharacter target) {
        float collide = target.radius + detectRadius;
        float distance = PVector.dist(target.position, position);

        return distance < collide;
    }

}
package objs.characters;

import java.util.ArrayList;

import game.DrawEngine;
import game.GameContext;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import processing.core.PVector;

public class Human extends Character {

    public ArrayList<PVector> patrolPositions;
    public PVector startingPosition;
    public float detectRadius;
    public boolean chase;
    public int currentPatrol;

    public Human(float xPos, float yPos, float radius, int health, float patrolDistance, float detectRadius, GameContext targets) {
        super(xPos, yPos, radius, health, targets);
        this.speedMultiplier *= 1.5f;
        this.detectRadius = detectRadius;
        this.chase = false;
        this.startingPosition = position.copy();

        this.patrolPositions = new ArrayList<>();
        addPatrolPositions(patrolDistance);
        this.currentPatrol = 0;
    }

    /**
     * Get the closest target player from the list of player targets.
     * @return the closest player character.
     */
    protected PlayerCharacter getClosestTarget() {
        return context.player;
    }

    /**
     *
     * @return
     */
    protected PVector getClosestTargetPosition() {
        return getClosestTarget().position.copy();
    }


    @Override
    public void display(DrawEngine drawEngine) {
        drawEngine.drawHuman(super.position.x,super.position.y,super.radius,super.radius);
    }

    @Override
    public void integrate() {
        PlayerCharacter target = context.player;
        speedMultiplier = BASE_SPEED/3;
        /* Run directly towards the player */
        if (chase) {
            if (inBounds()) {
                PVector velocity = getVelocityToTarget(target.position);
                move(velocity);
            }

            /* Stop chasing if we've gone too far or lost the player */
            if (tooFarAway() && !detectPlayer(target)) chase = false;
        }

        /* Patrol */
        else {
            if (detectPlayer(target)) {
                chase = true;
            }
            else {
                PVector targetPosition = patrolPositions.get(currentPatrol);
                PVector velocity = getVelocityToTarget(targetPosition);

                /* Update to next patrol location */
                if (velocity.mag() < 2f) {
                    currentPatrol = (currentPatrol + 1) % patrolPositions.size();
                    integrate();
                }
                else {
                    move(velocity);
                }
            }
        }
    }

    private boolean tooFarAway() {
        return PVector.dist(position, startingPosition) > detectRadius * 1.5;
    }

    private boolean detectPlayer(PlayerCharacter target) {
        float collide = target.radius + detectRadius;
        float distance = PVector.dist(target.position, position);

        return distance < collide;
    }


    private void addPatrolPositions(float patrolDistance) {
        patrolPositions.add(new PVector(getX(position.x + patrolDistance), getY(position.y + patrolDistance)));
        patrolPositions.add(new PVector(getX(position.x - patrolDistance), getY(position.y + patrolDistance)));
        patrolPositions.add(new PVector(getX(position.x - patrolDistance), getY(position.y - patrolDistance)));
        patrolPositions.add(new PVector(getX(position.x + patrolDistance), getY(position.y - patrolDistance)));
    }


}
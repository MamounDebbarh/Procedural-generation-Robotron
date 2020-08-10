package objs.characters.enemies;

import java.util.ArrayList;

import game.DrawEngine;
import game.GameContext;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import processing.core.PVector;

public class PatrolEnemy extends Enemy {

    public ArrayList<PVector> patrolPositions;
    public PVector startingPosition;


    public int currentPatrol;

    public PatrolEnemy(float xPos, float yPos, float radius, int health, float patrolDistance, float detectRadius, GameContext targets) {
        super(xPos, yPos, radius, health, detectRadius, targets);
        this.speedMultiplier *= 1.5f;
        this.detectRadius = detectRadius;

        this.startingPosition = position.copy();

        this.patrolPositions = new ArrayList<>();
        addPatrolPositions(patrolDistance);

        this.currentPatrol = 0;
    }

    @Override
    public void display(DrawEngine drawEngine) {
        drawEngine.drawGhost(super.position.x,super.position.y,super.radius,super.radius);
    }

    @Override
    public void integrate() {
        PlayerCharacter target = getClosestTarget();

        /* Run directly towards the player */
        if (chase) {

            PVector velocity = getVelocityToTarget(target.position);
            move(velocity);


            /* Stop chasing if we've gone too far or lost the player */
            if (tooFarAway() && !detectPlayer(target)) chase = false;
        }

        /* Patrol */
        else {
            if (detectPlayer(target)) {
                chase = true;
            } else {
                PVector targetPosition = patrolPositions.get(currentPatrol);
                PVector velocity = getVelocityToTarget(targetPosition);

                /* Update to next patrol location */
                if (velocity.mag() < 2f) {
                    currentPatrol = (currentPatrol + 1) % patrolPositions.size();
                    integrate();
                } else {
                    move(velocity);
                }
            }
        }
    }


    private void addPatrolPositions(float patrolDistance) {
        patrolPositions.add(new PVector(getX(position.x + patrolDistance), getY(position.y + patrolDistance)));
        patrolPositions.add(new PVector(getX(position.x - patrolDistance), getY(position.y + patrolDistance)));
        patrolPositions.add(new PVector(getX(position.x - patrolDistance), getY(position.y - patrolDistance)));
        patrolPositions.add(new PVector(getX(position.x + patrolDistance), getY(position.y - patrolDistance)));
    }


}
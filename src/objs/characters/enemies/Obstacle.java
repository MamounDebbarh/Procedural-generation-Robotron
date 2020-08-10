package objs.characters.enemies;

import java.util.ArrayList;

import game.DrawEngine;
import game.GameContext;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import processing.core.PVector;

/**
 * Basic enemy that just follows the player, always trying to reach the player's position.
 *
 */
public class Obstacle extends Enemy {


    public Obstacle(float xPos, float yPos, float radius, int health, float detectRadius,  GameContext targets) {
        super(xPos, yPos, radius, health, detectRadius, targets);
    }

    @Override
    public void display(DrawEngine drawEngine) {
        drawCircularObject(drawEngine.parent.color(0, 0, 0), drawEngine);
    }

    @Override
    public void integrate() {
    }

}
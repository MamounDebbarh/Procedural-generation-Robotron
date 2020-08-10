package game.states;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import game.*;
import objs.characters.Character;
import objs.particles.Explosion;
import objs.particles.Missile;
import objs.particles.Particle;
import objs.pickups.Effect;
import objs.pickups.Pickup;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

public abstract class GameState {

    Random random;
    public PApplet parent;
    GameUI ui;
    public GameContext context;
    public DrawEngine drawEngine;
    public AudioPlayer player;
    private AudioPlayer p2;


    GameState(GameContext context, DrawEngine drawEngine) {
        this.random = new Random();
        this.parent = drawEngine.parent;
        this.ui = new GameUI(context);
        this.context = context;
        this.drawEngine = drawEngine;
        //	public SoundFile pew;
        Minim minim = new Minim(parent);
        this.player = minim.loadFile("data/Photon.wav");
        this.p2 = minim.loadFile("data/Death.wav");

    }

    public abstract void display();

    public abstract GameState update(int mouseX, int mouseY);

    public abstract GameState handleInput(GameInput input);

    /**
     * Display all drawable game objects in the game.
     */
    void displayGame() {
        drawEngine.displayGame();
        context.player.display(drawEngine);
        drawEngine.displayDrawables(context.enemies, context.particles, context.pickups, context.explosions, context.humans, context.walls);
    }


    /**
     * Main update step of the game loop. Goes through all GameObjects and updates their properties such as position and health.
     * The position of the mouse is used to draw the direction the player is facing.
     *
     * @param mouseX - x position of the mouse
     * @param mouseY - y position of the mouse
     */
    void updateStep(int mouseX, int mouseY) {
        updatePlayerStep(mouseX, mouseY);
        updateEnemyStep();
        updateHumanStep();

        /* Integrate step for all other game objects */
        updateGameObjects(context.particles, context.explosions, context.player.weapons);
    }

    /**
     * Update step for the player. Updates the player's position, direction and checks for pickup collisions.
     *
     * @param mouseX - x position of the mouse
     * @param mouseY - y position of the mouse
     */
    private void updatePlayerStep(int mouseX, int mouseY) {
        context.player.integrate();
        context.player.facingDirection(mouseX, mouseY);

        /* Player pickups */
        context.player.collideResult(context.pickups.iterator(), new Function<Pickup, Boolean>() {

            @Override
            public Boolean apply(Pickup p) {
                p.effect.apply(context.player);
                context.player.powerups.add(p.effect);
                return true;
            }
        });

        /* Count down and remove any powerup effects */
        Iterator<Effect> effectIt = context.player.powerups.iterator();
        while (effectIt.hasNext()) {
            Effect effect = effectIt.next();
            effect.lifespan--;

            if (effect.lifespan <= 0) {
                effect.cease(context.player);
                effectIt.remove();
            }
        }
        context.player.currentWeapon.integrate();

    }

    /**
     * Update step for all enemies in the game.
     * Updates their movements and interaction with other objects in the game.
     */
    private void updateEnemyStep() {
        Iterator<Character> enemyIt = context.enemies.iterator();

        while (enemyIt.hasNext()) {
            Character enemy = enemyIt.next();


            /* Individual enemy update step */
            enemy.integrate();

            /* Remove enemies with no health */
            if (enemy.health <= 0) {
                try {
                    p2.rewind();
                    p2.play();
                } catch (Exception e) {
                    System.out.println("OKAY");
                }
                context.score += 1;
                enemyIt.remove();
                context.enemies.remove(enemy);
            }

            /* Collision with players to deal damage to them */
            if (enemy.hasCollided(context.player)) {
                context.player.takeDamage(1);
            }


            /* Collision with other enemies to resolve overlapping */
            enemy.collideResult(context.enemies.iterator(), new Function<Character, Boolean>() {

                @Override
                public Boolean apply(Character c) {
                    PVector normal = PVector.sub(enemy.position, c.position).normalize();
                    enemy.position.add(normal.mult(1));

                    return false;
                }

            });

            /* Collision with particles for damage */
            enemy.collideResult(context.particles.iterator(), new Function<Particle, Boolean>() {

                @Override
                public Boolean apply(Particle p) {
                    enemy.health -= p.damage;
                    if (p instanceof Missile) context.explosions.add(((Missile) p).explode());

                    return true;
                }

            });

            /* Collision with explosions for damage */
            enemy.collideResult(context.explosions.iterator(), new Function<Explosion, Boolean>() {

                @Override
                public Boolean apply(Explosion e) {
                    enemy.health -= e.damage;

                    return e.lifespan <= 0;
                }

            });
        }
    }

    /**
     * Update step for all humans in the game.
     * Updates their movements and interaction with other objects in the game.
     */
    private void updateHumanStep() {
        Iterator<Character> humanIt = context.humans.iterator();

        while (humanIt.hasNext()) {
            Character human = humanIt.next();

            /* Individual human update step */
            human.integrate();

            /* Remove enemies with no health */
            if (human.health <= 0) {
                context.score += 1;
                humanIt.remove();
                context.humans.remove(human);
            }

            /* Collision with players to deal damage to them */
            if (human.hasCollided(context.player)) {
                context.score += 5;
                humanIt.remove();
                context.humans.remove(human);
            }


            /* Collision with other enemies to resolve overlapping */
            human.collideResult(context.enemies.iterator(), new Function<Character, Boolean>() {

                @Override
                public Boolean apply(Character c) {
                    humanIt.remove();
                    context.humans.remove(human);
                    return false;
                }

            });
        }
    }

    /**
     * Updates all game objects that do not require additional interaction with other objects.
     *
     * @param objectLists - List of game object lists.
     */
    @SafeVarargs
    private void updateGameObjects(ArrayList<? extends GameObject>... objectLists) {
        for (ArrayList<? extends GameObject> gameObjects : objectLists) {
            for (GameObject object : gameObjects) {
                object.integrate();
            }
        }
    }

    GameState checkGameOver() {
        if (context.player.health == 0) {
            return new GameOverState(context, drawEngine);
        } else return this;

    }

    StartState newGame() {
        GameContext newGame = new GameContext();
        return new StartState(newGame, drawEngine);
    }

}
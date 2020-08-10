package game.states;

import com.sun.glass.events.KeyEvent;
import game.Config;
import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import objs.characters.Human;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.*;
import objs.particles.Particle;
import objs.particles.Wall;
import objs.pickups.AmmoPickup;
import objs.pickups.HealthPickup;
import objs.pickups.SpeedPickup;
import processing.core.PConstants;

import java.util.Iterator;
import java.util.Random;

public class PlayingState extends GameState {

    private boolean player = true;

    PlayingState(GameContext context, DrawEngine drawEngine) {
        super(context, drawEngine);
        spawnMap();
    }

    @Override
    public void display() {
        displayGame();
        ui.display(drawEngine, context.player);
    }


    private void spawnMap() {
        int x = 0;
        int y = 0;
        int length = Config.DIGGER + 1;

        while (x <= Config.SCREEN_X) {
            context.walls.add(new Wall(x, y, 50));

            if (y >= Config.SCREEN_Y) {
                x += 100;
                y = 0;
            } else {
                y += 100;
            }
        }

        // digger
        int digX = Config.DIGX;
        int digY = Config.DIGY;
        int startx = digX;
        int starty = digY;
        int rand = 4;

        while (length >= 0) {

            Iterator<Wall> wallIt = context.walls.iterator();
            rand = (int) parent.random(4);

            while (wallIt.hasNext()) {
                Wall wall = wallIt.next();
                if (wall.position.x == digX && wall.position.y == digY) {
                    wallIt.remove();
                    context.walls.remove(wall);
                    length--;
                    break;
                }
            }
            length--;
            switch (rand) {
                case 0:
                    //up
                    if (digY != 0) digY -= 100;
                    else digY = starty;
                    break;

                case 1:
                    //down
                    if (digY != Config.SCREEN_Y) digY += 100;
                    else digY = starty;
                    break;

                case 2:
                    //left
                    if (digX != 0) digX -= 100;
                    else digX = startx;
                    break;

                case 3:
                    //right
                    if (digX != Config.SCREEN_X) digX += 100;
                    else digX = startx;
                    break;
            }
        }


    }

    private void spawnEnemies() {

        // Randomly spawn a new enemy at each level.
        Random random = new Random();

        int en = random.nextInt(4);

        switch (en) {
            case 0:
                // Circle Enemies
                context.wave.spawnEnemies();
                CircleEnemy circleEnemy = new CircleEnemy(parent.random(Config.SCREEN_X),
                        parent.random(Config.SCREEN_Y),
                        15, 5, 200f,
                        context);
                while (!circleEnemy.inMap()) {
                    circleEnemy = new CircleEnemy(parent.random(Config.SCREEN_X),
                            parent.random(Config.SCREEN_Y),
                            15, 5, 200f,
                            context);
                }
                context.enemies.add(circleEnemy);
                break;
            case 1:
                // Ambush Enemies
                context.wave.spawnEnemies();

                AmbushEnemy ambushEnemy = new AmbushEnemy(parent.random(Config.SCREEN_X),
                        parent.random(Config.SCREEN_Y),
                        15, 10, 200f,
                        context);
                while (!ambushEnemy.inMap()) {
                    ambushEnemy = new AmbushEnemy(parent.random(Config.SCREEN_X),
                            parent.random(Config.SCREEN_Y),
                            15, 10, 200f,
                            context);
                }
                context.enemies.add(ambushEnemy);

                break;
            case 2:
                // Basic Chase Enemy
                context.wave.spawnEnemies();

                BasicChaseEnemy basicChaseEnemy = new BasicChaseEnemy(parent.random(Config.SCREEN_X),
                        parent.random(Config.SCREEN_Y),
                        15, 10, 200f,
                        context);
                while (!basicChaseEnemy.inMap()) {
                    basicChaseEnemy = new BasicChaseEnemy(parent.random(Config.SCREEN_X),
                            parent.random(Config.SCREEN_Y),
                            15, 10, 200f,
                            context);
                }
                context.enemies.add(basicChaseEnemy);

                break;
            case 3:
                // Patrol Enemy
                context.wave.spawnEnemies();

                PatrolEnemy patrolEnemy = new PatrolEnemy(parent.random(Config.SCREEN_X), parent.random(Config.SCREEN_Y),
                        15, 15, 100f, 200f, context);
                while (!patrolEnemy.inMap()) {
                    patrolEnemy = new PatrolEnemy(parent.random(Config.SCREEN_X), parent.random(Config.SCREEN_Y),
                            15, 15, 100f, 200f, context);
                }

                context.enemies.add(patrolEnemy);
                break;
        }


    }

    private void spawnObstacles() {
        // Obstacle
        if (context.wave.numObstacles > 0) {
            context.wave.spawnObstacles();

            Obstacle obstacle = new Obstacle(parent.random(Config.SCREEN_X),
                    parent.random(Config.SCREEN_Y),
                    15, 1, 200f,
                    context);
            while (!obstacle.inMap()) {
                obstacle = new Obstacle(parent.random(Config.SCREEN_X),
                        parent.random(Config.SCREEN_Y),
                        15, 1, 200f,
                        context);
            }
            context.enemies.add(obstacle);

        }

    }

    private void spawnHumans() {
        if (context.wave.numHumans > 0) {
            context.wave.spawnHumans();
            // Human
            context.wave.spawnHumans();

            Human human = new Human(parent.random(Config.SCREEN_X), parent.random(Config.SCREEN_Y),
                    15, 15, 10f, 200f, context);
            while (!human.inMap()) {
                human = new Human(parent.random(Config.SCREEN_X), parent.random(Config.SCREEN_Y),
                        15, 15, 10f, 200f, context);
            }
            context.humans.add(human);
        }

    }

    private void spawnPickups() {
        // Speed Pickup
        if (random.nextInt(Config.SPAWN_RATE_SPEED) == 0) {
            SpeedPickup pickup = new SpeedPickup(parent.random(Config.SCREEN_X), parent.random(Config.SCREEN_Y), 10, 10);
            context.pickups.add(pickup);
        }
        // Ammo Pickup
        if (random.nextInt(Config.SPAWN_RATE_AMMO) == 0) {
            AmmoPickup pickup = new AmmoPickup(parent.random(Config.SCREEN_X), parent.random(Config.SCREEN_Y), 10, 10);
            context.pickups.add(pickup);
        }
        // Health Pickup
        if (random.nextInt(Config.SPAWN_RATE_AMMO) == 0) {
            HealthPickup pickup = new HealthPickup(parent.random(Config.SCREEN_X), parent.random(Config.SCREEN_Y), 10, 10);
            context.pickups.add(pickup);
        }

    }

    private void spawnPlayer() {
        while (!context.player.inMap()) {
            context.player = new PlayerCharacter("Player", (float) Math.random() * Config.SCREEN_X, (float) Math.random() * Config.SCREEN_Y, 15, context);
        }
    }

    @Override
    public GameState update(int mouseX, int mouseY) {
        updateStep(mouseX, mouseY);
        if (player) {
            spawnPlayer();
            player = false;
        }
        parent.stroke(255, 100);
        parent.line(mouseX, mouseY, context.player.position.x, context.player.position.y);
        spawnPickups();
        if (context.wave.finished && context.enemies.size() == 0) {
            int bonus = (context.humans.size() * 5) + (context.player.health / 10);
            context.humans.clear();
            context.pickups.clear();
            return new EndWave(context, drawEngine, bonus);
        }
        if (!context.wave.finished) {
            spawnEnemies();
            spawnHumans();
            spawnObstacles();
        }
        return checkGameOver();
    }

    @Override
    public GameState handleInput(GameInput input) {
        if (input.keyDown) {
            context.player.directionPress(input.keyCode);

            if (input.keyCode == KeyEvent.VK_R) {
                context.player.currentWeapon.reload();
            }

            if (input.keyCode == KeyEvent.VK_1) {
                context.player.currentWeapon = context.player.weapons.get(0);
            }
            if (input.keyCode == KeyEvent.VK_2) {
                context.player.currentWeapon = context.player.weapons.get(1);
            }
            if (input.keyCode == KeyEvent.VK_3) {
                context.player.currentWeapon = context.player.weapons.get(2);
            }
        } else context.player.directionRelease(input.keyCode);

        if (input.mouseButton == PConstants.LEFT) {
            Particle bullet = context.player.attack(input.mouseX, input.mouseY);
            if (bullet != null) {
                try {
                    super.player.rewind();
                    super.player.play();
                } catch (Exception e) {
                    System.out.println("OKAY");
                }
                context.particles.add(bullet);
            }
        }


        return this;
    }


}
package game;

import java.util.ArrayList;

import objs.characters.Character;
import objs.characters.PlayerCharacter;
import objs.particles.Explosion;
import objs.particles.Particle;
import objs.particles.Wall;
import objs.pickups.Pickup;

public class GameContext {

    public PlayerCharacter player;
    public ArrayList<Character> humans;
    public ArrayList<Character> enemies;
    public ArrayList<Particle> particles;
    public ArrayList<Wall> walls;
    public ArrayList<Pickup> pickups;
    public ArrayList<Explosion> explosions;

    public Wave wave;
    public int score;

    public GameContext() {
        PlayerCharacter p = new PlayerCharacter("Player",(float) Math.random() * Config.SCREEN_X, (float) Math.random() * Config.SCREEN_Y, 15, this);;
        player = p;
        this.score = 0;
        humans = new ArrayList<>();
        enemies = new ArrayList<>();
        particles = new ArrayList<>();
        walls = new ArrayList<>();
        pickups = new ArrayList<>();
        explosions = new ArrayList<>();
        this.wave = new Wave();

//		enemies.add(new PatrolCharacter(500, 500, 15, 100f, 150f, player));
    }
}
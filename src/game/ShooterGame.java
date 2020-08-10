package game;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PConstants;

public class ShooterGame extends PApplet{

    float xPos = 100;
    float yPos = 100;
    float left, right, up, down;
    float speed = 8.0f;
    public AudioPlayer player;

    public GameController gameController;

    public void settings() {
        noSmooth();
        size(Config.SCREEN_X, Config.SCREEN_Y);
    }

    public void setup() {
        Minim minim = new Minim(this);
        this.player = minim.loadFile("data/Birds.wav");
        gameController = new GameController(this);
    }


    public void draw() {
        if (!player.isPlaying()) {
            player.rewind();
            player.play();
        }
        cursor(PConstants.CROSS);
        gameController.step(mouseX, mouseY);
    }


    public void mousePressed() {
        gameController.handleInput(mouseX, mouseY, mouseButton, 0, false);
    }

    public void keyPressed() {
        gameController.handleInput(mouseX, mouseY, 0, keyCode, true);
    }

    public void keyReleased() {
        gameController.handleInput(mouseX, mouseY, 0, keyCode, false);
    }

    public static void main(String[] args) {
        PApplet.main("game.ShooterGame");
    }

}
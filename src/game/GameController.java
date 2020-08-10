package game;

import game.states.GameState;
import game.states.StartState;
import processing.core.PApplet;

public class GameController {

    public PApplet parent;

    public DrawEngine drawEngine;

    private GameContext context;
    private GameState state;


    public GameController(PApplet parent) {
        this.parent = parent;
        this.drawEngine = new DrawEngine(parent);
        this.context = new GameContext();

        this.state = new StartState(context, drawEngine);

    }

    public void step(int mouseX, int mouseY) {
        state.display();
        state = state.update(mouseX, mouseY);

    }

    public void handleInput(int mouseX, int mouseY, int mouseButton, int keyCode, boolean keyDown) {
        GameInput input = new GameInput(mouseX, mouseY, mouseButton, keyCode, keyDown);
        state = state.handleInput(input);
    }
}
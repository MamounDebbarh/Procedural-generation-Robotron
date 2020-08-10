package game.states;

import game.Config;
import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import processing.core.PConstants;

public class StartState extends GameState {

    public StartState(GameContext context, DrawEngine drawEngine) {
        super(context, drawEngine); }



    @Override
    public void display() {
        drawEngine.displayStart();
        parent.noStroke();
        drawEngine.drawText(64, "Start game", Config.SCREEN_X/2, Config.SCREEN_Y/2, 0);
        drawEngine.drawText(32, "Press Enter to start.", Config.SCREEN_X/2, Config.SCREEN_Y/2+75, 0);
    }

    @Override
    public GameState update(int mouseX, int mouseY) {
        return this;
    }

    @Override
    public GameState handleInput(GameInput input) {
        if (input.keyCode == PConstants.ENTER || input.keyCode == PConstants.RETURN) {
            parent.stroke(240);
            return new PlayingState(context, drawEngine);
        }
        else return this;
    }





}

package game.states;

import game.DrawEngine;
import game.Config;

import game.GameContext;
import game.GameInput;
import processing.core.PConstants;

public class GameOverState extends GameState {

    GameOverState(GameContext context, DrawEngine engine) {
        super(context, engine);
    }


    @Override
    public GameState handleInput(GameInput input) {
        if (input.keyCode == PConstants.ENTER || input.keyCode == PConstants.RETURN) {
            return newGame();
        }
        return this;
    }

    @Override
    public GameState update(int mouseX, int mouseY) {
        return this;
    }

    @Override
    public void display() {
        displayGame();
        int textX = Config.SCREEN_X / 2;
        int textY = Config.SCREEN_Y / 3;
        drawEngine.drawText(40, "Game Over", textX, textY, 0);
        drawEngine.drawText(40, "Final score: " + context.score, textX, textY + 50, 0);
        drawEngine.drawText(40, "Press Enter to play again.", textX, textY + 100, 0);
    }

}

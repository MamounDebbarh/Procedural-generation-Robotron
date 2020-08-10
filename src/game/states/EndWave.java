package game.states;

import game.Config;
import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import processing.core.PConstants;

import java.awt.event.KeyEvent;

public class EndWave extends GameState {

    private int bonusScore;

    EndWave(GameContext context, DrawEngine drawEngine, int bonusScore) {
        super(context, drawEngine);
        this.bonusScore = bonusScore;
        context.score += bonusScore;
    }


    @Override
    public void display() {
        drawEngine.displayStart();
        ui.display(drawEngine, context.player);

        int textX = Config.SCREEN_X / 2;
        int textY = Config.SCREEN_Y / 4;
        drawEngine.drawText(40, "Wave " + context.wave.waveNumber + " finished.", textX, textY, 0);
        drawEngine.drawText(32, bonusScore + " bonus score for remaining health and humans.", textX, textY + 75, 0);
        drawEngine.drawText(40, "Score: " + context.score, textX, textY + 115, 0);
        drawEngine.drawText(40, "Press Enter to start next wave.", textX, textY + 175, 0);
    }

    @Override
    public GameState update(int mouseX, int mouseY) {
        context.player.directionRelease(KeyEvent.VK_W);
        context.player.directionRelease(KeyEvent.VK_S);
        context.player.directionRelease(KeyEvent.VK_A);
        context.player.directionRelease(KeyEvent.VK_D);
        return checkGameOver();
    }

    @Override
    public GameState handleInput(GameInput input) {
        switch (input.keyCode) {
            case PConstants.ENTER:
            case PConstants.RETURN:
                return nextWave();

            case KeyEvent.VK_F:
                return new ShopState(context, drawEngine, this);

            default:
                return this;
        }
    }

    private PlayingState nextWave() {
        context.wave.next();
        return new PlayingState(context, drawEngine);
    }


}
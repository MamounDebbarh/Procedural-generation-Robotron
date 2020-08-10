package game.states;

import game.Config;
import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import objs.weapons.Weapon;

import java.awt.event.KeyEvent;

public class ShopState extends GameState {

    private EndWave prevState;

    ShopState(GameContext context, DrawEngine drawEngine, EndWave prevState) {
        super(context, drawEngine);
        this.prevState = prevState;
    }

    @Override
    public void display() {
        drawEngine.displayStart();
        drawShopScreen();
    }

    public GameState update(int mouseX, int mouseY) {
        return this;
    }

    @Override
    public GameState handleInput(GameInput input) {
        switch (input.keyCode) {
            case KeyEvent.VK_R:
                return prevState;
            // HP bonus
            case KeyEvent.VK_1:
                if (context.score >= Config.HP_COST) {
                    context.score -= Config.HP_COST;
                    context.player.health += 15;

                }
                break;
            // AMMO bonus
            case KeyEvent.VK_2:
                if (context.score >= Config.AMMO_COST) {
                    context.score -= Config.AMMO_COST;
                    for (Weapon weapon : context.player.weapons) {
                        weapon.ammo += weapon.clipSize;
                    }
                }
                break;
            // DMG bonus for pistol
            case KeyEvent.VK_3:
                if (context.score >= Config.DMG_COST) {
                    context.score -= Config.DMG_COST;
                    context.player.weapons.get(0).damage += 5;
                }
                break;
            // Dmg for machine gun
            case KeyEvent.VK_4:
                if (context.score >= Config.DMG_COST) {
                    context.score -= Config.DMG_COST;
                    context.player.weapons.get(2).damage += 5;
                }
                break;
        }
        return this;
    }

    private void drawShopScreen() {
        int textX = Config.SCREEN_X / 2;
        int textY = Config.SCREEN_Y / 4;
        drawEngine.drawText(40, "Score: " + context.score, textX, textY, 0);
        drawEngine.drawText(45, "Welcome to the shop, press R to Return.", textX, textY + 50, 0);
        drawEngine.parent.stroke(255);
        int shopX = textX / 2;
        int shopY = Config.SCREEN_Y / 2 - 75;

        /* HP pack */
        drawEngine.drawEllipse(drawEngine.parent.color(255, 0, 0), shopX, shopY, 25, 25);
        drawEngine.drawText(32, "[1] Buy  HP pack for " + Config.HP_COST, shopX + 250, shopY, 0);

        /* AMMO pack */
        drawEngine.drawEllipse(drawEngine.parent.color(0, 0, 255), shopX, shopY + 50, 25, 25);
        drawEngine.drawText(32, "[2] Buy  AMMO pack for " + Config.AMMO_COST, shopX + 250, shopY + 50, 0);

        /* DMG */
        drawEngine.drawEllipse(drawEngine.parent.color(0), shopX, shopY + 100, 25, 25);
        drawEngine.drawText(32, "[3] Buy  GUN DMG boost for " + Config.DMG_COST, shopX + 300, shopY + 100, 0);

        /* DMG */
        drawEngine.drawEllipse(drawEngine.parent.color(0), shopX, shopY + 150, 25, 25);
        drawEngine.drawText(32, "[3] Buy  MACHINE GUN DMG boost for " + Config.DMG_COST, shopX + 350, shopY + 150, 0);


    }


}

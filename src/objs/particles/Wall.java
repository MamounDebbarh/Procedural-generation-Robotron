package objs.particles;

import game.DrawEngine;
import game.GameObject;

public class Wall extends GameObject {

    int color;

    public Wall(float xPos, float yPos, float radius) {
        super(xPos, yPos, radius);
        this.color = 100;
    }

    @Override
    public void display(DrawEngine drawEngine) {
        drawEngine.parent.noStroke();
        // Draw rectangles. rect(x, y, cote)
//        drawCircularObject(color, drawEngine);
        // Draw wall
            drawEngine.drawWall(super.position.x,super.position.y,super.radius,super.radius);
    }

    public void integrate() {
    }

}

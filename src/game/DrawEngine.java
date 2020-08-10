package game;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

public class DrawEngine {

    public PApplet parent;
    private PImage backGround;
    private PImage startGround;
    private PImage dwall;
    private PImage dfly;
    private PImage dghost;
    private PImage dC;
    private PImage dB;
    private PImage dhuman;



    public DrawEngine(PApplet parent) {
        this.parent = parent;
        this.backGround = parent.loadImage("design/Background.png");
        this.startGround = parent.loadImage("design/Start.png");
        this.dwall = parent.requestImage("design/wallTree.png");
        this.dfly = parent.requestImage("design/Fly.png");
        this.dghost = parent.requestImage("design/Ghost.png");
        this.dC = parent.requestImage("design/C.png");
        this.dB = parent.requestImage("design/B.png");
        this.dhuman = parent.requestImage("design/Human.png");

    }

    public void displayStart() {
            startGround.resize(Config.SCREEN_X, Config.SCREEN_Y);
            parent.background(startGround);


    }

    public void displayGame() {
            backGround.resize(Config.SCREEN_X, Config.SCREEN_Y);
            parent.background(backGround);
    }
    /**
     * Display all drawable objects.
     * @param drawables - List of drawable objects.
     */
    @SafeVarargs
    public final void displayDrawables(ArrayList<? extends GameObject>... drawables) {
        for (ArrayList<? extends GameObject> drawList : drawables) {
            ArrayList<? extends GameObject> drawListCopy = new ArrayList<>(drawList);

            for (GameObject drawable : drawListCopy) {
                drawable.display(this);
            }
        }
    }

    /**
     * Generic draw text function for other classes to draw text to the screen
     * @param textSize - size of the text
     * @param text - text to be drawn
     * @param posX - x position of the text
     * @param posY - y position of the text
     * @param col - colour of the text
     */
    public void drawText(int textSize, String text, float posX, float posY, int col) {
        PFont font = parent.createFont("Arial", textSize, true);
        parent.textFont(font, textSize);
        parent.fill(col);
        parent.text(text, posX, posY);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
    }

    /**
     * Draw text with opacity
     */
    public void drawText(int textSize, String text, float posX, float posY, int col, int opacity) {
        PFont font = parent.createFont("Arial", textSize, true);

        parent.textFont(font, textSize);
        parent.fill(col, opacity);
        parent.text(text, posX, posY);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
    }

    /**
     * Draw an ellipse based on given parameters.
     * @param col - colour of the circle
     * @param xPos - x coordinate of the circle
     * @param yPos - y coordinate of the circle
     * @param width - width of the circle
     * @param height - height of the circle
//     * @param i
     */
    public void drawEllipse(int col, float xPos, float yPos, float width, float height) {
        parent.ellipseMode(PConstants.CENTER);
        parent.fill(col);
        parent.ellipse(xPos, yPos, width, height);
    }

    /**
     * Draw an ellipse with opacity.
     */
    public void drawEllipse(int col, float xPos, float yPos, float width, float height, int opacity) {
        parent.stroke(255);
        parent.ellipseMode(PConstants.CENTER);
        parent.fill(col, opacity);
        parent.ellipse(xPos, yPos, width, height);
    }


    /**
     * Draw a rectangle based on given parameters.
     * @param col - colour of the rectangle
     * @param xPos - x coordinate of the circle
     * @param yPos - y coordinate of the circle
     * @param width - width of the rectangle
     * @param height - height of the rectangle
     */
    public void drawRectangle(int col, float xPos, float yPos, float width, float height) {
        parent.rectMode(PConstants.CORNER);
        parent.fill(col);
        parent.rect(xPos, yPos, width, height);
    }

    /**
     * Draw a rectangle with opacity.
     */
    public void drawRectangle(int col, float xPos, float yPos, float width, float height, int opacity) {
        parent.rectMode(PConstants.CORNER);
        parent.fill(col, opacity);
        parent.rect(xPos, yPos, width, height);
    }


    /**
     * Draw an arc based on given parameters.
     * @param col - colour of the arc.
     * @param xPos - x coordinate
     * @param yPos - y coordinate
     * @param width - width of the arc
     * @param height - height of the arc
     * @param startValue - angle to start the arc in radians
     * @param value - angle to end the arc in radians
     */
    public void drawArc(int col, float xPos, float yPos, float width, float height, float startValue, float value) {
        parent.stroke(col);
        parent.strokeWeight(20);
        parent.noFill();
        parent.arc(xPos, yPos, width, height, startValue, value);

        /* Reset stroke weight to default. */
        parent.strokeWeight(1);
    }

    /**
     * Draw an arc with opacity.
     */
    void drawArc(int col, float xPos, float yPos, float width, float height, float startValue, float value, float opacity) {
        parent.stroke(col, opacity);
        parent.strokeWeight(20);
        parent.noFill();
        parent.arc(xPos, yPos, width, height, startValue, value);

        /* Reset stroke weight to default. */
        parent.strokeWeight(1);
    }



    public void drawMap(int col){
        parent.stroke(col);
        parent.fill(col);
        // Draw rectangles. rect(x, y, cote)
        parent.square(0,0, 50);
    }

    public void drawWall(float xPos, float yPos, float width, float height){
//        parent.ellipseMode(PConstants.CENTER);
//        parent.fill(125,58,11);
//        parent.ellipse(xPos, yPos, width*2, height*2);
        parent.noStroke();

        if (dwall.width == 0) {
            // Image is not yet loaded
        } else if (dwall.width == -1) {
            // This means an error occurred during image loading
        } else {
            // Image is ready to go, draw it
            parent.image(dwall, (float) (xPos- (width)*1.9), yPos - (height)*2, width*4, height*3);
        }
    }

    public void drawFly(float xPos, float yPos, float width, float height){
//        parent.ellipseMode(PConstants.CENTER);
//        parent.fill(125,58,11);
//        parent.ellipse(xPos, yPos, width*2, height*2);
        parent.noStroke();

        if (dfly.width == 0) {
            // Image is not yet loaded
        } else if (dfly.width == -1) {
            // This means an error occurred during image loading
        } else {
            // Image is ready to go, draw it
            parent.image(dfly, (float) (xPos- (width)*2), (float) (yPos - (height)*1.8), width*4, height*4);
        }
    }

    public void drawGhost(float xPos, float yPos, float width, float height){
//        parent.ellipseMode(PConstants.CENTER);
//        parent.fill(125,58,11);
//        parent.ellipse(xPos, yPos, width*2, height*2);
        parent.noStroke();

        if (dghost.width == 0) {
            // Image is not yet loaded
        } else if (dghost.width == -1) {
            // This means an error occurred during image loading
        } else {
            // Image is ready to go, draw it
            parent.image(dghost, (float) (xPos- (width)*1.8), (float) (yPos - (height)*1.8), width*4, height*4);
        }
    }

    public void drawB(float xPos, float yPos, float width, float height){
//        parent.ellipseMode(PConstants.CENTER);
//        parent.fill(125,58,11);
//        parent.ellipse(xPos, yPos, width*2, height*2);
        parent.noStroke();

        if (dC.width == 0) {
            // Image is not yet loaded
        } else if (dC.width == -1) {
            // This means an error occurred during image loading
        } else {
            // Image is ready to go, draw it
            parent.image(dC, (float) (xPos- (width)*1.8), (float) (yPos - (height)*1.8), width*4, height*4);
        }
    }
    public void drawC(float xPos, float yPos, float width, float height){
//        parent.ellipseMode(PConstants.CENTER);
//        parent.fill(125,58,11);
//        parent.ellipse(xPos, yPos, width*2, height*2);
        parent.noStroke();

        if (dB.width == 0) {
            // Image is not yet loaded
        } else if (dB.width == -1) {
            // This means an error occurred during image loading
        } else {
            // Image is ready to go, draw it
            parent.image(dB, (float) (xPos- (width)*1.8), (float) (yPos - (height)*1.8), width*4, height*4);
        }
    }

    public void drawHuman(float xPos, float yPos, float width, float height){
//        parent.ellipseMode(PConstants.CENTER);
//        parent.fill(125,58,11);
//        parent.ellipse(xPos, yPos, width*2, height*2);
        parent.noStroke();

        if (dhuman.width == 0) {
            // Image is not yet loaded
        } else if (dhuman.width == -1) {
            // This means an error occurred during image loading
        } else {
            // Image is ready to go, draw it
            parent.image(dhuman, (float) (xPos- (width)*2.3), (float) (yPos - (height)*2.3), width*5, height*5);
        }
    }


}
import java.util.ArrayList;
import processing.core.PApplet;

/**
 * Pong is a simple recreating of my childhood favourite game, Atari Pong
 * Two players control paddles to hit a ball back and forth, with the first player to score 9 points winning the game.
 * 
 * @author: Savio Theo
 */
public class Pong extends PApplet {

    // Initial paddle positions
    private float x1 = 25, x2 = 765, y1 = 250, y2 = 250;
    private boolean up1 = false, up2 = false, down1 = false, down2 = false;

    // Ball properties
    private float xspeed = 2.5f, yspeed = 1.5f;
    private float x = 400, y = 300;

    // Scores
    private int score1 = 0, score2 = 0;

    // Game states
    private boolean start = false, win = false;

    // Colors for the ball
    private ArrayList<Integer> coloursR = new ArrayList<>();
    private ArrayList<Integer> coloursG = new ArrayList<>();
    private ArrayList<Integer> coloursB = new ArrayList<>();
    private int i, j, k;

    /**
     * Sets up the canvas size.
     */
    public void settings() {
        size(800, 600);
    }

    /**
     * Initializes the game by setting the background color and loading color values.
     */
    public void setup() {
        background(0);

        int[] values = {255, 240, 225, 210, 195, 180, 165, 150, 135, 120, 105, 90, 75, 60, 45};
        for (int color : values) {
            coloursR.add(color);
            coloursG.add(color);
            coloursB.add(color);
        }
    }

    /**
     * Main game loop. Handles drawing the game state, including the start screen, gameplay, and win screen.
     */
    public void draw() {
        if (!start && !win) {
            drawStartScreen();
        } else if (start && score1 < 9 && score2 < 9) {
            drawGameplay();
        } else if (score1 > 8) {
            drawWinScreen("Player 1 Wins!");
        } else if (score2 > 8) {
            drawWinScreen("Player 2 Wins!");
        }
    }

    /**
     * Draws the start screen.
     */
    private void drawStartScreen() {
        background(0);

        textSize(75);
        fill(255);
        textAlign(CENTER, CENTER);
        text("PONG", 400, 50);

        textSize(25);
        text("P1: up is 'Q' and down is 'A'", 400, 200);
        text("P2: up is 'P' and down is 'L'", 400, 250);
        text("First to 9 wins!", 400, 300);
        text("Good luck and have fun!", 400, 350);
        text("Press ENTER to start", 400, 550);
    }

    /**
     * Handles the gameplay drawing logic.
     */
    private void drawGameplay() {
        background(0);

        updateScore();
        drawPoints(coloursR.get(i), coloursG.get(j), coloursB.get(k));

        stroke(coloursR.get(i), coloursG.get(j), coloursB.get(k));
        fill(coloursR.get(i), coloursG.get(j), coloursB.get(k));

        line(400, 0, 400, 600);

        x += xspeed;
        y += yspeed;

        ellipse(x, y, 20, 20);
        ballPaddleCollision();
        screenCollision();
        paddleMovement();

        rect(x1, y1, 10, 100);
        rect(x2, y2, 10, 100);
    }

    /**
     * Draws the win screen with the given message.
     *
     * @param message The message to display (e.g., "Player 1 Wins!").
     */
    private void drawWinScreen(String message) {
        win = true;
        start = false;

        background(0);

        drawPoints(255, 255, 255);

        textSize(75);
        fill(255);
        textAlign(CENTER, CENTER);
        text(message, 400, 300);
        textSize(25);
        text("Press ENTER to play again", 400, 550);
    }

    /**
     * Draws the scores on the screen.
     *
     * @param colour1 Red component of the text color.
     * @param colour2 Green component of the text color.
     * @param colour3 Blue component of the text color.
     */
    private void drawPoints(int colour1, int colour2, int colour3) {
        textSize(75);
        fill(colour1, colour2, colour3);
        textAlign(CENTER, CENTER);
        text(score1 + " " + score2, 400, 30);
    }

    /**
     * Updates the scores when the ball passes the paddles.
     */
    private void updateScore() {
        if (x < 25) {
            score2 += 1;
            resetBall();
            xspeed = -3;
        } else if (x > 775) {
            score1 += 1;
            resetBall();
            xspeed = 3;
        }
    }

    /**
     * Resets the ball to the center of the screen.
     */
    private void resetBall() {
        x = 400;
        y = 300;
    }

    /**
     * Handles collision detection between the ball and the paddles.
     */
    private void ballPaddleCollision() {
        if (x - 10 < x1 + 10 && y + 10 > y1 && y - 10 < y1 + 100) {
            xspeed *= -1.1;
            randomColours();
        }

        if (x + 10 > x2 && y + 10 > y2 && y - 10 < y2 + 100) {
            xspeed *= -1.1;
            randomColours();
        }
    }

    /**
     * Generates random colors for the ball.
     */
    private void randomColours() {
        i = (int) random(0, 15);
        j = (int) random(0, 15);
        k = (int) random(0, 15);
    }

    /**
     * Handles collision detection between the ball and the screen edges.
     */
    private void screenCollision() {
        if (y < 10) {
            y = 10;
            yspeed *= -1;
        } else if (y > 590) {
            y = 590;
            yspeed *= -1;
        }

        if (y1 < 0) {
            y1 = 0;
        } else if (y1 + 100 > 600) {
            y1 = 500;
        }

        if (y2 < 0) {
            y2 = 0;
        } else if (y2 + 100 > 600) {
            y2 = 500;
        }
    }

    /**
     * Handles paddle movement based on key presses.
     */
    private void paddleMovement() {
        if (up1 && !down1) {
            y1 -= 3;
        } else if (down1 && !up1) {
            y1 += 3;
        }

        if (up2 && !down2) {
            y2 -= 3;
        } else if (down2 && !up2) {
            y2 += 3;
        }
    }

    /**
     * Handles key press events for paddle movement and game start/restart.
     */
    public void keyPressed() {
        if (key == 'q' || key == 'Q') {
            up1 = true;
        }
        if (key == 'a' || key == 'A') {
            down1 = true;
        }
        if (key == 'p' || key == 'P') {
            up2 = true;
        }
        if (key == 'l' || key == 'L') {
            down2 = true;
        }
        if (keyCode == ENTER) {
            start = true;
            win = false;
            score1 = 0;
            score2 = 0;
            i = 0;
            j = 0;
            k = 0;
        }
    }

    /**
     * Handles key release events to stop paddle movement.
     */
    public void keyReleased() {
        if (key == 'q' || key == 'Q') {
            up1 = false;
        }
        if (key == 'a' || key == 'A') {
            down1 = false;
        }
        if (key == 'p' || key == 'P') {
            up2 = false;
        }
        if (key == 'l' || key == 'L') {
            down2 = false;
        }
    }

    /**
     * Main method to run the Pong.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        PApplet.main("PongGame");
    }
}

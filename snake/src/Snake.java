import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Snake implements ActionListener, KeyListener {
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, THICKNESS = 10;
    public int time, heading, score, highScore, snakeLength, snakeSpeed = 80;
    public static Snake snake;
    public JFrame jframe = new JFrame("Snake");

    private boolean paused = false, gameOver = false;
    private int width = 500, height = 500;
    private ArrayList<Point> snakeSegments = new ArrayList<>();
    private Point snakeHead, food;
    private Timer snakeTimer = new Timer(snakeSpeed, this);

    private JPanel window = new JPanel();
    private Random random = new Random();
    private TopPanel topPanel;
    private Board board;
    private Dimension dim;


    public Snake() {
        snake = this;
        board = new Board(width, height);
        topPanel = new TopPanel();
        window.setLayout(new BoxLayout(window, BoxLayout.PAGE_AXIS));
        window.add(topPanel.SnakeGame);
        window.add(board);

        jframe.setContentPane(window);
        jframe.setSize(width, height);
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addKeyListener(this);
        jframe.setVisible(true);
        dim = board.getSize();
        board.height = dim.height;
        board.repaint();

        startGame();
    }

    public Point getHeadPosition() {
        return snakeHead;
    }

    public Point getFoodPosition() {
        return food;
    }

    public ArrayList<Point> getSnakeSegments() {
        return snakeSegments;
    }

    public void setSnakeSpeed(int speed) {
        snakeSpeed = speed;
        snakeTimer.setDelay(speed);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void startGame() {
        time = 0;
        score = 0;
        paused = false;
        gameOver = false;
        heading = RIGHT;
        snakeLength = 6;
        snakeSegments.clear();
        snakeHead = new Point(0, 0);
        food = new Point(random.nextInt(dim.width / THICKNESS), random.nextInt(dim.height / THICKNESS));

        snakeTimer.start();
        jframe.setFocusable(true);
        jframe.requestFocus();
    }

    public void pauseGame() {
        if (paused)
            paused = false;
        else
            paused = true;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (snakeHead != null && !paused && !gameOver) {
            snakeSegments.add(new Point(snakeHead.x, snakeHead.y));

            if (heading == UP) {
                if (snakeHead.y - 1 >= 0 && !checkCollision(snakeHead.x, snakeHead.y - 1))
                    snakeHead = new Point(snakeHead.x, snakeHead.y - 1);
                else
                    snakeHead = new Point(snakeHead.x, dim.height / THICKNESS - 1);
            }
            if (heading == DOWN) {
                if (snakeHead.y + 1 < dim.height / THICKNESS && !checkCollision(snakeHead.x, snakeHead.y + 1))
                    snakeHead = new Point(snakeHead.x, snakeHead.y + 1);
                else
                    snakeHead = new Point(snakeHead.x, 0);
            }
            if (heading == RIGHT) {
                if (snakeHead.x + 1 < dim.width / THICKNESS && !checkCollision(snakeHead.x + 1, snakeHead.y))
                    snakeHead = new Point(snakeHead.x + 1, snakeHead.y);
                else
                    snakeHead = new Point(0, snakeHead.y);
            }
            if (heading == LEFT) {
                if (snakeHead.x - 1 >= 0 && !checkCollision(snakeHead.x - 1, snakeHead.y))
                    snakeHead = new Point(snakeHead.x - 1, snakeHead.y);
                else
                    snakeHead = new Point(dim.width / THICKNESS - 1, snakeHead.y);
            }

            if (snakeSegments.size() > snakeLength)
                snakeSegments.remove(0);

            if (food != null) {
                if (snakeHead.equals(food)) {
                    score += 10;
                    snakeLength++;
                    food.setLocation(random.nextInt(dim.width / THICKNESS), random.nextInt(dim.height / THICKNESS));
                }
            }
        }
        board.repaint();
    }

    public boolean checkCollision(int x, int y) {
        Point currentPoint = new Point(x, y);
        for (Point p : snakeSegments) {
            if (currentPoint.equals(p)) {
                gameOver = true;
                if (score > highScore)
                    highScore = score;
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (heading != DOWN && !paused)
                    heading = UP;
                break;
            case KeyEvent.VK_DOWN:
                if (heading != UP && !paused)
                    heading = DOWN;
                break;
            case KeyEvent.VK_LEFT:
                if (heading != RIGHT && !paused)
                    heading = LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                if (heading != LEFT  && !paused)
                    heading = RIGHT;
                break;
            case KeyEvent.VK_ENTER:
                startGame();
                break;
            case KeyEvent.VK_SPACE:
                pauseGame();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public static void main(String[] args) {
        snake = new Snake();
    }
}

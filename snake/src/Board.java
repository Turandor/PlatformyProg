import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel {

    private Snake snake;
    public int width;
    public int height;

    public Board(int width,int height){
        this.width = width;
        this.height = height;
        snake = Snake.snake;
    }

    private void initializeBoard(Graphics graphics){
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0,0, width, height);
    }

    private void paintSnake(Graphics graphics, Snake snake){
        graphics.setColor(Color.RED);
        for(Point segment : snake.getSnakeSegments()){
            graphics.fillRect(segment.x * snake.THICKNESS, segment.y * snake.THICKNESS, snake.THICKNESS, snake.THICKNESS);
        }
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(snake.getHeadPosition().x * snake.THICKNESS, snake.getHeadPosition().y * snake.THICKNESS, Snake.THICKNESS, Snake.THICKNESS);
    }

    private void paintFood(Graphics graphics, Snake snake){
        graphics.setColor(Color.GREEN);
        graphics.fillRect(snake.getFoodPosition().x * snake.THICKNESS, snake.getFoodPosition().y * snake.THICKNESS, Snake.THICKNESS, Snake.THICKNESS);
    }

    private void drawScore(Graphics graphics, Snake snake){
        String scoreString = "Score: " + snake.score;
        graphics.setColor(Color.BLACK);
        graphics.drawString(scoreString, getWidth()/2 - scoreString.length(), 10);
    }

    private void drawGameOver(Graphics graphics, Snake snake){
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0, width, height);

        graphics.setColor(Color.RED);
        String gameOverString = "Game over!";
        graphics.drawString(gameOverString, getWidth()/2-gameOverString.length(), getHeight()/2);

        graphics.setColor(Color.WHITE);
        String scoreString = "Score: " + snake.score;
        graphics.drawString(scoreString, getWidth()/2-scoreString.length(), getHeight()/2+20);

        graphics.setColor(Color.WHITE);
        String highScoreString = "High score: " + snake.highScore;
        graphics.drawString(highScoreString, getWidth()/2-scoreString.length(), getHeight()/2+40);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if(!snake.isGameOver()) {
            initializeBoard(graphics);
            paintSnake(graphics, snake);
            paintFood(graphics, snake);
            drawScore(graphics, snake);
            Toolkit.getDefaultToolkit().sync();
        }
        else
            drawGameOver(graphics, snake);

    }

}

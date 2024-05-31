import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
    private static final int UNIT_SIZE = 25;
    private static final int GRID_SIZE = 20;
    private static final int SCREEN_SIZE = UNIT_SIZE * GRID_SIZE;
    private ArrayList<Point> snake;
    private Point fruit;
    private char direction;
    private boolean running;
    private Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        initGame();
    }

    private void initGame() {
        snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        direction = 'R';
        spawnFruit();
        running = true;
        timer = new Timer(100, this);
        timer.start();
    }

    private void spawnFruit() {
        Random random = new Random();
        int x = random.nextInt(GRID_SIZE);
        int y = random.nextInt(GRID_SIZE);
        fruit = new Point(x, y);
    }

    private void move() {
        Point head = new Point(snake.get(0));
        switch (direction) {
            case 'U':
                head.y--;
                break;
            case 'D':
                head.y++;
                break;
            case 'L':
                head.x--;
                break;
            case 'R':
                head.x++;
                break;
        }
        if (head.equals(fruit)) {
            snake.add(0, head);
            spawnFruit();
        } else {
            snake.remove(snake.size() - 1);
            if (snake.contains(head) || head.x < 0 || head.y < 0 || head.x >= GRID_SIZE || head.y >= GRID_SIZE) {
                running = false;
                timer.stop();
            } else {
                snake.add(0, head);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            g.setColor(Color.red);
            g.fillRect(fruit.x * UNIT_SIZE, fruit.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.green);
            for (Point p : snake) {
                g.fillRect(p.x * UNIT_SIZE, p.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
        } else {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", SCREEN_SIZE / 2 - 120, SCREEN_SIZE / 2 - 20);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction != 'D')
                    direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U')
                    direction = 'D';
                break;
            case KeyEvent.VK_LEFT:
                if (direction != 'R')
                    direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L')
                    direction = 'R';
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new SnakeGame(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
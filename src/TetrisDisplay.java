/*
 * This is the class that creates the game board and
 * and provides the animation of a brick falling continously
 */
/**
 * Tetris Game class containing the display of the game
 * for each unique brick
 * @version 1.000
 * @author Tyron Corpuz & Vanessa Ezenduka
 * 11/19/2020
 */

import javax.swing.*;
 import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel
{
    private TetrisGame game;
    Timer timer;
    int timerSpeed;
    private int start_x  = 180;
    private int start_y  = 85;
    private int cellSize = 12;
    
    int score_x = 1;
    int score_y = 1;
    int scoreXSize = 130;
    int scoreYSize = 35;
    Font scoreFont = new Font("Arial", 1, 22);
    
    int endGameX = 150;
    int endGameY = 150;
    int endGameXSize = 250;
    int endGameYSize = 100;
    boolean endGame = false;
    
    Color[] colors = {Color.BLACK, Color.RED, Color.BLUE, new Color(188, 61, 239),
                                    Color.YELLOW, new Color(160,235,255), Color.GREEN,
                                    Color.ORANGE};
    
    public TetrisDisplay(TetrisGame gam)
    {
        game = gam;
        timerSpeed = 100;
        //Declared variables for the animation of each individual
        //brick falling down
        timer = new Timer(timerSpeed, new ActionListener(){                                  
            public void actionPerformed(ActionEvent ae)
            {
                game.getCurrentPiece().moveDown();                            //Calls the moveY method in the TetrisGame with the 
                if(!game.validateMove())
                {
                    boolean lineTest = true;
                    game.getCurrentPiece().moveUp();
                    game.transferColor();
                    boolean endTest = game.gamOverTest();
                    if(endTest == true)
                    {
                        pauseGame();
                        setEndGame();
                    }
                    else
                    {
                        while(lineTest == true)
                        {
                            lineTest = game.lineDetection();
                        }
                        game.spawnBrick();
                    }
                }
                repaint();                                                             
            }
        });
        timer.start();
        
        
        this.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke)
            {
                game.makeMove(ke);
                if(game.state == 1)
                    timer.stop();
                else
                    timer.start();
            }
        });
        
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
    }
    
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int x;
        int y = start_y;
        
        for(int row = 0; row < game.fetchNumberRows(); row++)                   //builts the gameboard of size 12x20 (standard Tetris board)
        {
            x = start_x;
            for(int col = 0; col < game.fetchNumberCols(); col++)
            {
                g.setColor(Color.white);
                g.fillRect(x, y, cellSize, cellSize);
                x += cellSize;
            }
            y += cellSize;
        }
        
        g.setColor(Color.BLACK);
        game.drawFalling(g, start_x, start_y, cellSize);
        
        g.setColor(Color.black);                                                                                                           //draws the upper line of the well
        g.drawLine(start_x, start_y, (start_x+(game.fetchNumberCols()*cellSize)), start_y);
        
        x = start_x;
        y = start_y;
        
        g.fillRect(start_x - cellSize, start_y, cellSize, game.fetchNumberRows()*cellSize);                                                //draws the walls of the well
        g.fillRect(start_x + game.fetchNumberCols() * cellSize, start_y, cellSize, game.fetchNumberRows()*cellSize);
        g.fillRect(start_x - cellSize, start_y + game.fetchNumberRows()*cellSize, (game.fetchNumberCols()+2)*(cellSize), cellSize);
        
        
        for(int row = 0; row < game.fetchNumberRows(); row++)                                                                              //draws the bricks onto the background of the well     
        {
            for(int col = 0; col < game.fetchNumberCols(); col++)
            {
                int cellColor = game.dropBrickColor(row, col);
                if(cellColor > 0)
                {
                    g.setColor(colors[cellColor]);
                    g.fillRect(start_x + col*cellSize, start_y + row*cellSize, cellSize, cellSize);
                    g.setColor(colors[0]);
                    g.drawRect(start_x + col*cellSize, start_y + row*cellSize, cellSize, cellSize);
                }
            }
        }
        
        scoreBoard(g);
        if(endGame == true)
        {
            endGameDisplay(g);
        }
    }
    
    public void pauseGame()
    {
        timer.stop();
    }
    
    public void scoreBoard(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        float thickness = 3;
        
        g2.setColor(Color.BLACK);
        Stroke stroke = g2.getStroke();
        String score = "Score: " + game.getScore();
        
        g2.setFont(scoreFont);
        
        g2.setStroke(new BasicStroke(thickness));
        g2.setBackground(Color.WHITE);
        
        g2.drawRect(score_x, score_y, scoreXSize, scoreYSize);
        g2.setStroke(stroke);
        
        g2.drawString(score, score_x, 30);
    }
    
    public void setEndGame()
    {
        endGame = true;
    }
    
    public void endGameDisplay(Graphics g)
    {     
        int endFontX = 215;
        int endFontY = 210;
        
        Graphics2D g2 = (Graphics2D)g;
        float thickness = 4;
        
        g2.setColor(Color.BLACK);
        Stroke stroke = g2.getStroke();
        String end = "Game Over!";
        
        g2.setFont(scoreFont);
        g2.setStroke(new BasicStroke(thickness));
        
        g2.drawRect(endGameX, endGameY, endGameXSize, endGameYSize);
        g2.setColor( Color.BLACK);
        
        g2.fillRect(endGameX, endGameY, endGameXSize, endGameYSize);
        g2.setColor(Color.RED);
        
        g2.setStroke(stroke);
        g2.drawString(end, endFontX, endFontY);
    }
}
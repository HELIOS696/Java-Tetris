/*
 * This is the class that houses the game board
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

public class TetrisWindow extends JFrame
{
    private TetrisGame game;
    private TetrisDisplay display;
    int numScores = 0;
    int[] scoreBoard = new int[0];
    
    private int win_width  = 500;
    private int win_height = 450; 
    
    //Creates the window that houses
    //the game and all of its controls and components
    public TetrisWindow()
    {
        this.setTitle("My Tetris Game");
        this.setSize(win_width, win_height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.WHITE);
        
        game = new TetrisGame();
        display = new TetrisDisplay(game);
        this.add(display);
        
        initMenus();
        
        this.setVisible(true);
    }
    
    public void initMenus()
    {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        
        JMenu men1 = new JMenu("Game"); 
        menuBar.add(men1);
        
        JMenu men2 = new JMenu("Colors"); 
        menuBar.add(men2);
        
        JMenu men3 = new JMenu("Size");
        menuBar.add(men3);
        
        //JMenu men4 = new JMenu("Leaderboard");
        
        JMenuItem item1 = new JMenuItem("Save Game"); 
        men1.addSeparator();
        item1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent me)
            {
                display.pauseGame();
                saveGame();
            }
        });
        men1.add(item1);
        
        JMenuItem item2 = new JMenuItem("Retrieve Game"); 
        men1.add(item2);
        item1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent me)
            {
                display.pauseGame();
                retrieveGame();
            }
        });
        men1.add(item2);
        
        men1.addSeparator();
        JMenuItem item3 = new JMenuItem("New Game"); 
        men1.add(item3);
        item3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent me)
           {
               game.newGame();
           }
        });
        men1.add(item3);
        
        JMenuItem item4 = new JMenuItem("LeaderBoard");
        item4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent me)
            {
                dispose();
                LeaderBoards();
            }
        });
        men1.add(item4);
    }
    
    public void saveGame()
    {
        game.saveToFile("Tetris");
    }
    
    public void retrieveGame()
    {
        game.initFromFile();
        repaint();
    }   
    
    public void LeaderBoards()
    {
        JFrame frame = new JFrame("LeaderBoards");
        frame.setSize(win_width, win_height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        frame.setBackground(Color.WHITE);
        frame.setVisible(true);
        
        int currentScore = game.score;
        int lastScore = numScores - 1;
       
        for(int score = 0; score < numScores; score++)
        {
            int testScore = scoreBoard[score];
            if(currentScore > testScore)
            {
                for(int changeScore = lastScore; changeScore > score; changeScore--)
                {
                    int replaceScore = changeScore - 1;
                    scoreBoard[changeScore] = scoreBoard[replaceScore];
                }
               
                testScore = currentScore;
            }
        }
        repaint();
    }
    
    public static void main(String[] args)
    {
        new TetrisWindow();
    }
}

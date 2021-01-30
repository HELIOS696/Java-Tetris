/*
 * This is the class that provides the logic for directing the game,
 * to respond to the moves reported by the Tetris display class and to
 * upadate the game board and status after each move
 */
/**
 * Tetris Game class containing the logic of the game
 * for each unique brick
 * @version 3.000
 * @author Tyron Corpuz & Vanessa Ezenduka
 * 11/19/2020
 */

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TetrisGame {
    
    TetrisBrick currentPiece;
    TetrisDisplay disp;
    int[][] board;
    int numberRows = 20;
    int numberCols = 12;
    int score;
    int state;
    String fileName;

    int numSegments = 4;
    int randPiece;
    
    public TetrisGame()
    {
        board = new int[numberCols][numberRows]; 
        spawnBrick();
    }
    
    //transferColor() allows the bricks' color to be transferred
    //to the background of the well, making only one active
    public void transferColor()
    {
        int[][] pos = currentPiece.getPositon();
        
        int redCol = 1;
        int blueCol = 2;
        int tealCol = 3;
        int yellowCol = 4;
        int purpleCol = 5;
        int greenCol = 6;
        int orangeCol = 7;
        
        for(int seg = 0; seg < numSegments; seg++)
        {
            int col = pos[seg][0];
            int row = pos[seg][1];
                    
            switch(randPiece)
            {
                case 0:
                    board[col][row] = greenCol;
                    break;
                case 1:
                    board[col][row] = yellowCol;
                    break;
                case 2:
                    board[col][row] = blueCol;
                    break;
                case 3:
                    board[col][row] = redCol;
                    break;
                case 4:
                    board[col][row] = tealCol;
                    break;
                case 5:
                    board[col][row] = orangeCol;
                    break;
                case 6:
                    board[col][row] = purpleCol;
                    break;
            }
            
        }
    }
     
    public void newGame()
    {
        score = 0;
        for(int col = 0; col < numberCols; col++)
        {
            for(int row = 0; row < numberRows; row++)
            {
                board[col][row] = 0;
            }
        }
        spawnBrick();
    }
    
    public int dropBrickColor(int rows, int cols)
    {
        int color = 0;
        color = board[cols][rows];
        return color;
    }
    
    //drawFalling will draw the bricks onto the well
    public void drawFalling(Graphics g, int start_x, int start_y, int cellSize)
    {
        for(int row = 0; row < getCurrentPiece().position.length; row++)
        {  
            int brickStartX = (currentPiece.position[row][0]*cellSize)+start_x;
            int brickStartY = (currentPiece.position[row][1]*cellSize)+start_y;
            
            g.setColor(getCurrentPiece().getColor()); 
            g.fillRect(brickStartX , brickStartY, cellSize, cellSize);
            g.setColor(Color.BLACK);
            g.drawRect(brickStartX , brickStartY, cellSize, cellSize);    
        }
    }
    
    //makeMove decides on which method to call based 
    //on which key the user has pressed
    public void makeMove(KeyEvent ke)
    {
        int mov = ke.getKeyCode();
        switch(mov)
        {
            case KeyEvent.VK_LEFT:
                currentPiece.moveLeft();
                if(!validateMove())
                    currentPiece.moveRight();
                break;
            case KeyEvent.VK_RIGHT:
                currentPiece.moveRight();
                if(!validateMove())
                        currentPiece.moveLeft();
                break;
            case KeyEvent.VK_UP:
                currentPiece.rotate();
                if(!validateMove())
                    currentPiece.unrotate();
                break;
            case KeyEvent.VK_ESCAPE:
                togglePause();
                break;
            default:
                System.out.println("Invalid key is pressed!");
        }
    }

    
    /*
    validateMove() will make sure that each brick stays
    within the well through a series of boundary checks and 
    also checks whether past bricks are on the well so the new bricks 
    don't overlap 
    */
    public boolean validateMove()
    {
        int[][] brickPosition = currentPiece.getPositon();
        
        //check down boundary
        if(currentPiece.getBottomRow() >= numberRows)
            return false;
        
        //check left boundary
        for(int seg = 0; seg < numSegments; seg++)
        {
            if(brickPosition[seg][0] < 0)
                return false;
        }
        
        //check right boundary
        for(int seg = 0; seg < numSegments; seg++)
        {
            if(brickPosition[seg][0] == numberCols)
                return false;
        }
        
        //check color bricks
        for(int seg = 0; seg < numSegments; seg++)
        {
            int row = brickPosition[seg][0];
            int col = brickPosition[seg][1];
            
            if(board[row][col] > 0)
                return false;
        }
        return true;
    }
        
    //Generates a random Tetris Brick
    public void spawnBrick()
    {
        Random rand = new Random();
        int randBound = 7;
        randPiece = rand.nextInt(randBound);
        
        int orient = 0;
        int numSegment = 4;
        
        Color essColor = new Color(188, 61, 239);
        Color zeeColor = new Color(160, 235, 255);
        
        switch(randPiece)
        {
            case 0:
                currentPiece = new ElBrick(Color.GREEN, orient, numSegment, numberCols);
                return;
            case 1:
                currentPiece = new StackBrick(Color.YELLOW, orient, numSegment, numberCols);
                return;
            case 2:
                currentPiece = new JayBrick(Color.BLUE, orient, numSegment, numberCols);
                return;
            case 3:
                currentPiece = new SquareBrick(Color.RED, orient, numSegment, numberCols);
                return;
            case 4:
                currentPiece = new EssBrick(essColor, orient, numSegment, numberCols);
                return;
            case 5:
                currentPiece = new LongBrick(Color.ORANGE, orient, numSegment, numberCols);
                return;
            case 6:
                currentPiece = new ZeeBrick(zeeColor, orient, numSegment, numberCols);
                return;
        }
    }
    
    
    //Dectates for a black or filled line
    public boolean lineDetection()
    {
        boolean checkLine = true;
        int lastRow = numberRows - 1;
        
        for(int row = 0; row < numberRows; row++)
        {
            for(int col = 0; col < numberCols; col++)
            {
                    if(board[col][lastRow] == 0)
                    {
                        checkLine = false;
                    }
            }
        }
        
        if(checkLine == true)
        {
            lineDeletion();
            scoreIncrease();
            dropDownBricks();
        }
        
        return checkLine;
    }
    
    //Deletes the filled line
    public void lineDeletion()
    {
        int lastRow = numberRows - 1;
        for(int col = 0; col < numberCols; col++)
        {
           board[col][lastRow] = 0;
        }
    }
    
    //Tests whether the game is over 
    public boolean gamOverTest()
    {
        boolean endTest = false;
        int endRow = 1;
        for(int col = 0; col < numberCols; col++)
        {
            if(board[col][endRow] > 0)
            {
                endTest = true;
            }
        }
        return endTest;
    }
    
    //increases the game score
    public void scoreIncrease()
    {
        score += 100;
    }
    
    //drops the bricks by one layer once the line has been deleted
    public void dropDownBricks()
    {
        int lastRow = numberRows - 1;
        int lastCol = numberCols - 1;
        for(int col = lastCol; col >= 0; col--)
        {
            for(int row = lastRow; row >= 0; row--)
            {
                int updateRow = row + 1;
                if(board[col][row] > 0)
                {
                    board[col][updateRow] = board[col][row];
                    board[col][row] = 0;
                }
            }
        }
    }
    
    //pauses the game
    public void togglePause()
    {
        state = (state+1)%2;
    }
    
    //Saves the current game
    public void saveToFile(String fileName)
    {
        File fileConnection = new File (fileName);
        try
        {
            FileWriter outWriter = new FileWriter(fileConnection);
            outWriter.write(this.toString());
            outWriter.close();
        }
        catch(IOException ioe)
        {
            String message = "An error has occurred saving the game";
            JOptionPane.showMessageDialog(null, message, "Error!", 0);
        }
    }
    
    //loads the previous game
    public void initFromFile()
    {
        String fileName = new String ("TetrisGame.dat");
        JFileChooser chooser = new JFileChooser("");
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) // checking to make sure a non-null value was returned or that you did not just hit cancel
        {
            fileName = chooser.getSelectedFile().getAbsolutePath();  // This will return the full path to the file
        }
        else
        {
            String message = "An error has occurred loading the game";
            JOptionPane.showMessageDialog(null, message, "Error!", 0);
        }
        
        File fileConnection = new File(fileName);
        
        try
        {
            Scanner inScan = new Scanner(fileConnection);
            
            for(int col = 0; col < numberCols; col++)
            {
                for(int row = 0; row < numberRows; row++)
                {
                    board[col][row] = 0;
                }
            }
            
            score = inScan.nextInt();
            numberCols = inScan.nextInt();
            numberRows = inScan.nextInt();
            board = new int[numberCols][numberRows];
            for(int row = 0; row < numberRows; row++)
            {
                for(int col = 0; col < numberCols; col++)
                {
                    board[col][row] = inScan.nextInt();
                }
            }
            spawnBrick();
        }
        catch(Exception e)
        {
            System.err.println("An error has occurred loading the game");
            System.exit(0);
        }
    }
    
    public void setNumberRows(int rows)
    {
        numberRows = rows;
    }
    
    public void setNumberCols(int cols)
    {
        numberCols = cols;
    }
    
    public void setScore(int sc)
    {
        score = sc;
    }
    
    public void setState(int st)
    {
        state = st;
    }
    
    public TetrisBrick getCurrentPiece()
    {
        return currentPiece;
    }
    
    public int fetchNumberRows()
    {
        return numberRows;
    }
    
    public int fetchNumberCols()
    {
        return numberCols;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public int getState()
    {
        return state;
    }
    
    public String toString()
    {
        String stuff = ""+board.length+" "+board[0].length+"\n";
        
        for(int row = 0; row < board.length; row++)
        {
            for(int col = 0; col < board[0].length; col++)
            {
                stuff += board[row][col]+ " "; 
            }
            stuff += "\n";
        }
        
        stuff = stuff.substring(0,stuff.length()-1);
        return stuff;
    }
}

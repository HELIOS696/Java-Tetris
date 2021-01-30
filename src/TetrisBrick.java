/*
 * This is the class that provide the attributes and methods that each
 * Tetris Brick will be able to use.
 * This will enhance the interaction between the Tetris Game and the user
 */
/**
 * Tetris Brick containing attributes and methods
 * for each unique brick
 * @version 3.000
 * @author Tyron Corpuz & Vanessa Ezenduka
 * 11/19/2020
 */

import java.awt.*;
import java.awt.event.*;

public abstract class TetrisBrick {
    
    int[][] position = new int[4][2];
    Color color;
    int orientation;
    int numSegments = 4;
    
    public TetrisBrick(Color clr, int orient, int numSeg, int col){
    
        position = initPosition(col);
        color = clr;
        orientation = orient;
        numSegments = numSeg;
    }
    
    public void setPosition(int col)
    {
        initPosition(col);
    }
    
    public void setColor(Color clr)
    {
        color = clr;
    }

    public void setOrientation(int orient)
    {
        orientation = orient;
    }
    
    public void setNumberSegment(int numSeg)
    {
        numSegments = numSeg;
    }
    
    public int[][] getPositon()
    {
        return position;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public int getOreintation()
    {
        return orientation;
    }
    
    public int getNumSegments()
    {
        return numSegments;
    }
    
    public abstract int[][] initPosition(int cols);                 //initPostion(int cols) will create the bricks' initial position
    
    public abstract void rotate();                                  //rotate() will allow the user to rotate the orientation of the brick
    
    public abstract void unrotate();                                //unrotate() will keep the bricks' orientation when it has reached the bottom of the well
    
    //moveLeft will adjust the column of the position array
    //each time the appropriate key has been pressed
    public void moveLeft()
    {
        for(int row = 0; row < numSegments; row++)                          
        {
            position[row][0] -= 1;                                 //Each column is decremented by 1 to make the piece move left
        }
    }
    
    //moveLeft will adjust the column of the position array
    //each time the appropriate key has been pressed
    public void moveRight()
    {
        for(int row = 0; row < numSegments; row++)                          
        {
            position[row][0] += 1;                                 //Each column is incremented by 1 to make the piece move right
        }
    }
    
    //moveDown will adjust the position to help
    //animate it to fall the brick down
    public boolean moveDown()
    {
        for(int row = 0; row < numSegments; row++)
        {
            position[row][1] += 1;                                 //Each row is incremented by 1 to make the piece fall down
        }
        return true;
    }
    
    public int getUpperRow()
    {
        int upperRow = 0;
        
        for(int row = 0; row < numSegments; row++)
        {
            if(position[row][1] < upperRow)
                upperRow = position[row][1];
        }
        return upperRow;
    }
    //getBrickRow will help the program to check
    //if the piece has reached the bottom of the well
    public int getBottomRow()
    {
        int bottomRow = 0;
        
        for(int row = 0; row < numSegments; row++)
        {
            if(position[row][1] > bottomRow)
                bottomRow = position[row][1];                     
        }
        return bottomRow;
    }
    
    //moveUp will allow the program to prevent the 
    //peice from falling out of bounds
    public boolean moveUp()
    {
        for(int row = 0; row < numSegments; row++)
        {
            position[row][1] -= 1;
        }
        return true;
    }
}

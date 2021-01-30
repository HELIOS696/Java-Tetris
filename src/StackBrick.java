/*
 * This class will house the StackBrick of the Tetris Game
 */
/**
 * Class for the StackBrick
 * for each unique brick
 * @version 1.000
 * @author Tyron Corpuz & Vanessa Ezenduka
 * 11/19/2020
 */

import java.awt.*;

public class StackBrick extends TetrisBrick
{
    public StackBrick(Color color, int orient, int numSeg, int col){
        
        super(color, orient, numSeg, col);
        initPosition(col);
    }
    
    @Override
    public int[][] initPosition(int cols)
    {
        int referenceCol = cols/2;
        int position[][] = {{referenceCol-1,1}, {referenceCol,1}, {referenceCol,0}, {referenceCol+1,1}};
        return position;
    }
    
    public void rotate()
    {
        if(orientation == 0)
        {
            position[0][0] = position[0][0] + 1;
            position[0][1] = position[0][1] + 1;
            position[2][0] = position[2][0] - 1;
            position[2][1] = position[2][1] + 1;
            position[3][0] = position[3][0] - 1;
            position[3][1] = position[3][1] - 1;
            orientation = 1;
        }
        else if(orientation == 1)
        {
            position[0][0] = position[0][0] + 1;
            position[0][1] = position[0][1] - 1;
            position[2][0] = position[2][0] + 1;
            position[2][1] = position[2][1] + 1;
            position[3][0] = position[3][0] - 1;
            position[3][1] = position[3][1] + 1;
            orientation = 2;
        }
        else if(orientation == 2)
        {
            position[0][0] = position[0][0] - 1;
            position[0][1] = position[0][1] - 1;
            position[2][0] = position[2][0] + 1;
            position[2][1] = position[2][1] - 1;
            position[3][0] = position[3][0] + 1;
            position[3][1] = position[3][1] + 1;
            orientation = 3;
        }
        else if(orientation == 3)
        {
            position[0][0] = position[0][0] - 1;
            position[0][1] = position[0][1] + 1;
            position[2][0] = position[2][0] - 1;
            position[2][1] = position[2][1] - 1;
            position[3][0] = position[3][0] + 1;
            position[3][1] = position[3][1] - 1;
            orientation = 0;
        }
    }
    
    @Override
    public void unrotate()
    {
        rotate();
        rotate();
        rotate();
    }
}
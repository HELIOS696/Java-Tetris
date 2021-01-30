/*
 * This class will house the SquareBrick of the Tetris Game
 */
/**
 * Class for the SquareBrick
 * for each unique brick
 * @version 1.000
 * @author Tyron Corpuz & Vanessa Ezenduka
 * 11/19/2020
 */

import java.awt.*;

public class SquareBrick extends TetrisBrick
{
    public SquareBrick(Color color, int orient, int numSeg, int col){
        
        super(color, orient, numSeg, col);
        initPosition(col);
    }
    
    @Override
    public int[][] initPosition(int cols)
    {
        int referenceCol = cols/2;
        int position[][] = {{referenceCol,0}, {referenceCol+1,0}, {referenceCol,1}, {referenceCol+1,1}};
        return position;
    }
    
    public void rotate()
    {
        
    }
    
    @Override
    public void unrotate()
    {

    }
}
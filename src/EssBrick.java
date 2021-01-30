/*
 * This class will house the EssBrick of the Tetris Game
 */
/**
 * Class for the EssBrick
 * for each unique brick
 * @version 1.000
 * @author Tyron Corpuz & Vanessa Ezenduka
 * 11/19/2020
 */

import java.awt.*;

public class EssBrick extends TetrisBrick
{
    public EssBrick(Color color, int orient, int numSeg, int col){
        
        super(color, orient, numSeg, col);
        initPosition(col);
    }
    
    @Override
    public int[][] initPosition(int cols)
    {
        int referenceCol = cols/2;
        int position[][] = {{referenceCol,0}, {referenceCol+1,0}, {referenceCol,1}, {referenceCol-1,1}};
        return position;
    }
    
    public void rotate()
    {
        if(orientation == 0)
        {
            position[0][0] = position[0][0] - 1;
            position[0][1] = position[0][1] + 1;
            position[1][0] = position[1][0] - 2;
            position[1][1] = position[1][1];
            position[3][0] = position[3][0] + 1;
            position[3][1] = position[3][1] + 1;
            orientation = 1;
        }
        else
        {
            position[0][0] = position[0][0] + 1;
            position[0][1] = position[0][1] - 1;
            position[1][0] = position[1][0] + 2;
            position[1][1] = position[1][1];
            position[3][0] = position[3][0] - 1;
            position[3][1] = position[3][1] - 1;
            orientation = 0;
        }
    }
    
    @Override
    public void unrotate()
    {
        rotate();
    }
}
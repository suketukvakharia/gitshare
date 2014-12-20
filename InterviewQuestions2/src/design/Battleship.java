package design;

import java.util.List;



/**
 * http://www.careercup.com/question?id=5644457835757568
 * @author suketu
 *
 */
public class Battleship {
    
    
    /**
     * The block hit by each player each turn.
     * @author suketu
     *
     */
    private class Turn {
        int x, y;
    }
    
    /**
     * Game state classs. 
     * Game version that's storing the information.
     * Encrypt data to prevent cheating may be?
     * @author suketu
     *
     */
    private class GameState {
        int version;
        
        Block[][] player1Grid;
        Block[][] player2Grid;
        
        List<Ship> player1Ships;
        List<Ship> player2Ships;
    }
    
    private class Block {
        boolean shotThrough = false;
        Ship placedShip = null;
    }
    
    private class Ship {
//        List<Block> destroyedBlocks = new LinkedList<Block>();
//        List<Block> atBlocks = new LinkedList<Block>();
        boolean destroyed = false;
    }
}

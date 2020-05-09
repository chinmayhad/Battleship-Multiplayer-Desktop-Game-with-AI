
package battleship;

import java.util.Random;
import java.util.Stack;


public class AIEngine {
	private int latestX = 0;
    private int latestY = 0;
	private int searchingX = 0;
    private int searchingY = 0;
    private boolean guessedLatestHit = false;
    private Stack<Integer> stack = new Stack<Integer>();
    private Random generator = new Random();
    private int lastDirection = 0;
    private int x = 0;
    private int y = 0;
    private char[] charArray[];


    public AIEngine() {
        this.generate();
    }

    void reset() {
    	searchingX = 0;
        searchingY = 0;
        latestX = 0;
        latestY = 0;
        x = 0;
        y = 0;
        guessedLatestHit = false;
        stack.empty();
        lastDirection = 0;
        generate();
    }

    /**
     *
     * @return X coordinate of the next point the computer is going to hit
     */
    public int nextX() {
        if (x == latestX && y == latestY) {
            this.generate();
        }
        return x;
    }

    /**
     *
     * @return Y coordinate of the next point the computer is going to hit 
     */
    public int nextY() {
        if (x == latestX && y == latestY) {
            this.generate();
        }
        return y;
    }

    /**
     * generates a new X,Y coordinate for the PC to hit
     * 
     * Follows this general formula:
     *  Pick random point on opponent's board.
     *  If hit is successful, search nearby points for remaining parts of ship.
     *  Keep hitting until a ship is either completely destroyed or we run out of points to search
     *  Repeat
     */
    void generate() {
    	//Select random coordinates if no boat is found yet
        if (!guessedLatestHit && stack.isEmpty()) {
            x = generator.nextInt(10);
            y = generator.nextInt(10);
            return;
        }

        if (!guessedLatestHit && !stack.isEmpty()) {
            latestX = searchingX;
            x = latestX;
            latestY = searchingY;
            y = latestY;
            movingThroughPoints();
            return;
        }

        if (guessedLatestHit && !stack.isEmpty()) {
        	movingThroughPoints();
            return;
        }

        if (guessedLatestHit && stack.isEmpty()) {
        	//Pick any random direction
            int dir = generator.nextInt(4);
            searchingX = latestX;
            searchingY = latestY;
            //Push all directions to stack in random order
            int i = 4;
            while (i > 0) {
                stack.push(dir);
                dir++;
                if (dir == 4)
                    dir = 0;
                i--;
            }
            movingThroughPoints();
            return;
        }
    }

    /**
     * This function achieves movement in direction received in parameter
     * 
     * @param dir is the parameter that gives specified direction to move
     * @return true if movement in specified direction is successful, otherwise false.
     */
    public boolean move(int dir) {
    	//Northward direction movement 
        if (dir == 0) {
            if (y != 0) {
                x = latestX;
                y = latestY - 1;
                return true;
            } else
                return false;
        }

        //Southward direction movement
        if (dir == 2) {
            if (y != 9) {
                x = latestX;
                y = latestY + 1;
                return true;
            } else
                return false;
        }

        //Eastward direction movement
        if (dir == 1) {
            if (x != 9) {
                x = latestX + 1;
                y = latestY;
                return true;
            } else
                return false;
        }

        
        //Westward direction movement
        if (x != 0)
        {
            x = latestX - 1;
            y = latestY;
            return true;
        } else
            return false;
    }
    


    /**
     * We make our current direction as next direction & put it in stack, if we've 
     * directions already pushed in stack that are to be searched and
     * we have restrictions on movement in current direction.
     * 
     * We generate random point on coordinates if we don't have more queued directions
     */
        public void movingThroughPoints() {
        int dir = (int) stack.pop();
        while (!move(dir)) {
            if (stack.isEmpty()) {
                x = generator.nextInt(10);
                y = generator.nextInt(10);
                lastDirection = dir;
                return;
            } else {
                dir = (int) stack.pop();
            }
        }
        lastDirection = dir;
    }

}

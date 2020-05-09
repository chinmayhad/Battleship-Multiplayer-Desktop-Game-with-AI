package battleship;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class AbstractBoard {

	public class Ocean {
	    private boolean clicked;
	    private ShipFactory shipInstance;
	    
	    public Ocean(){
	        this.clicked = false;
	        this.shipInstance = null;
	    }
	    
	    public Ocean(ShipFactory ship){
	        this();
	        this.shipInstance = ship;
	    }

	    /**
	     *
	     * @return true if this ocean has been clicked
	     */
	    public boolean isClicked() {
	        return clicked;
	    }

	    /**
	     *
	     * @param clicked
	     */
	    public void setClicked(boolean clicked) {
	        this.clicked = clicked;
	    }

	    /**
	     *
	     * @return the ShipFactory occupying this ocean
	     */
	    public ShipFactory getShip() {
	        return shipInstance;
	    }

	    /**
	     *
	     * @param ship the ShipFactory to place in this ocean
	     */
	    public void setShip(ShipFactory ship) {
	        this.shipInstance = ship;
	    }
	    
	    
	}
	
	GridPane basicGrid;
    int gameStyle;
    ArrayList<ArrayList<Ocean>> gameBoard;
    ArrayList<ShipFactory> shipFleet;
    
    public AbstractBoard(GridPane gp){
        this.basicGrid = gp;
        gameBoard = new ArrayList<ArrayList<Ocean>>(10);
        shipFleet = new ArrayList<ShipFactory>(10);
    }
    
    /**
     *  Cleans the display board by painting every cell black
     */
    void cleanDisplayBoard() {
        GridPane board = this.basicGrid;
        for (Node node : board.getChildren()) {
            Integer x = GridPane.getColumnIndex(node);
            Integer y = GridPane.getRowIndex(node);

            if (x == null) {
                GridPane.setColumnIndex(node, 0);
            }

            if (y == null) {
                GridPane.setRowIndex(node, 0);
            }
            Rectangle rect = (Rectangle) node;
            rect.setFill(Color.BLACK);
            rect.setStroke(Color.WHITE);

        }
    }
    
    /**
     * Function to add a ship to a gameBoard
     * @param first is the ship to be added to fleet
     * @param isRotated indicates if the ship to be added is rotated or not
     */
    void addShip(ShipFactory ship, boolean isRotated) {

    	//Do not add invalid ships.This validation is based on space constraints.
        if (gameStyle == 1) {
        	// (This applies only to human player's game board)
            if (!this.isShipValid(ship.getStartX(), ship.getStartY(), ship.getSize(), isRotated))
                return;
        }
        for (int x = ship.getStartX(); x <= ship.getEndX(); x++)
            for (int y = ship.getStartY(); y <= ship.getEndY(); y++)
                this.gameBoard.get(x).get(y).setShip(ship);
    }

    
    /**
     * @return True if whole of the shipFleet is sunk, otherwise False
     */
    boolean checkVictory() {
        for (ShipFactory ship : shipFleet) {
            if (ship.isDestroyed() == false)
                return false;
        }
        return true;
    }
    
    
    /**
     * Vital method that has logic of processing a shot fired on opponent's board
     * 
     * @param index of the column that has to be hit
     * @param index of the row that has to be to be hit
     * @param the instance of logic used to decide the next point to hit
     */
    void hit(int column, int row, AIEngine ai) {
        GridPane gp = this.basicGrid;
        ArrayList<ArrayList<Ocean>> board = this.gameBoard;
        
        ShipFactory shipInstance = null;
        try {
            shipInstance = board.get(column).get(row).getShip();
        } catch (Exception e) {
        	//if there's exception in getting column or row or ship
            System.out.println("FAILURE");
            System.out.println("column: " + column + "\nRow: " + row);
        }
        Node n = this.getNode(column, row);
        Rectangle r = (Rectangle) n;
        if (shipInstance != null) {
            r.setFill(Color.PURPLE);
            if (shipInstance.hit()) {
                for (int x = shipInstance.getStartX(); x <= shipInstance.getEndX(); x++) {
                    for (int y = shipInstance.getStartY(); y <= shipInstance.getEndY(); y++) {
                        Node paintedNode = this.getNode(x, y);
                        ((Rectangle) paintedNode).setFill(Color.GREEN);
                    }
                }            }
        } 
        else
        {   ////When ship is not hit nor destroyed while computer is playing
            r.setFill(Color.WHITE);
            r.setStroke(Color.BLACK);
        }
        board.get(column).get(row).setClicked(true);
    }

    /**
     * 
     * This method is logic to get a node by it's indices of columns and rows.
     * 
     * @param column's index of the node we are looking for.
     * @param row's index of the node we are looking for.
     * @return the node we are looking for. Returns null if node is not found.
     */
    Node getNode(int col, int row) {
        GridPane gridPane = this.basicGrid;
        for (Node node : gridPane.getChildren()) {
            if (node == null)
                return null;
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                return node;
        }
        return null;
       }
    
    /**
     * Verifies if ship's placement is valid or not 
     *
     * @param x index of the boat to place
     * @param y the y index of the boat to place
     * @param size the size of the boat to place
     * @return true if placement is a valid spot, false otherwise
     */
    Boolean isShipValid(int x, int y, int size, boolean isRotated) {  

        //if boat is already in the code
        if (isRotated)
        {
            for (int i = 0; i < size && i <= 9; i++) {
                Node n = this.getNode(x, y + i);
                Rectangle rect = (Rectangle) n;
                if (rect.getFill().equals(Color.GREEN))
                    return false;
            }
        }
    	
    	if (isRotated && ((y + size - 1 > 9)))
        {
        	//See if it's too big to stay inside grid in horizontal position
            return false;
        }
        if (!isRotated && (x + size - 1 > 9))
        {
        	//See if it's too big to stay inside grid in vertical position
            return false;
        }

        if (!isRotated) {
            for (int i = 0; i < size && i <= 9; i++) {
                Node n = this.getNode(x + i, y);
                Rectangle rect = (Rectangle) n;
                if (rect.getFill().equals(Color.GREEN))
                    return false;
            }
        }
        return true;
    }

    boolean shipOnSpace(int x, int y, int size, boolean isRotated) {
        if (isRotated) {
            for (int i = 0; i < size && i <= 9; i++) {
                Node n = this.getNode(x, y + i);
                Rectangle r = (Rectangle) n;
                if (r.getFill().equals(Color.GREEN))
                    return false;
            }
        }

        if (!isRotated) {
            for (int i = 0; i < size && i <= 9; i++) {
                Node n = this.getNode(x + i, y);
                Rectangle r = (Rectangle) n;
                if (r.getFill().equals(Color.GREEN))
                    return false;
            }
        }
        return true;
    }
    
    /**
     * Abstract method that will be implemented by methods that set up game and display boards
     */
    abstract void initGrid();
    
}

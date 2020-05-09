package battleship;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;

public class PCAbstractBoard extends AbstractBoard {

    public PCAbstractBoard(GridPane gp) {
        super(gp);
        this.gameStyle = 0;
    }
    
    @Override
    void initGrid() {
        for (int x = 0; x < 10; x++) {
            ArrayList<Ocean> row = new ArrayList<Ocean>(10);
            for (int y = 0; y < 10; y++) {
                row.add(new Ocean());
            }
            gameBoard.add(row);
        }
        
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                gameBoard.get(x).get(y).setClicked(true);
                
        this.cleanDisplayBoard();
        this.setComputerBoard();
    }
    
    /**
     * Cleans computer's game board, and then adds new ships to it
     */
    void setComputerBoard(){
        this.shipFleet = new ArrayList<ShipFactory>(10);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                this.gameBoard.get(x).get(y).setShip(null);
                this.gameBoard.get(x).get(y).setClicked(false);
            }
        }

        ShipFactory s1 = new ShipFactory(2, this.gameBoard);
        this.addShip(s1, false);
        this.shipFleet.add(s1);

        ShipFactory s2 = new ShipFactory(3, this.gameBoard);
        this.addShip(s2, false);
        this.shipFleet.add(s2);

        ShipFactory s3 = new ShipFactory(3, this.gameBoard);
        this.addShip(s3, false);
        this.shipFleet.add(s3);

        ShipFactory s4 = new ShipFactory(4, this.gameBoard);
        this.addShip(s4, false);
        this.shipFleet.add(s4);
        
        ShipFactory s5 = new ShipFactory(5, this.gameBoard);
        this.addShip(s5, false);
        this.shipFleet.add(s5);
    }
}

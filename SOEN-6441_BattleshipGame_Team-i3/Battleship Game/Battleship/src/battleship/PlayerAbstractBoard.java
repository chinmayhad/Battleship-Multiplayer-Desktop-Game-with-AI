package battleship;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;

public class PlayerAbstractBoard extends AbstractBoard {

    public PlayerAbstractBoard(GridPane gp) {
        super(gp);
        this.gameStyle = 1;
    }

    @Override
    void initGrid() {
        for (int x = 0; x < 10; x++) {
            ArrayList<Ocean> row = new ArrayList<Ocean>(10);
            for (int y = 0; y < 10; y++)
                row.add(new Ocean());
            gameBoard.add(row);
        }
        shipFleet.clear();
        this.cleanDisplayBoard();
    }
}

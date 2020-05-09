package battleship;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BattleshipController implements Initializable {

    PlayerAbstractBoard playerGrid;
    PCAbstractBoard computerGrid;
    private int numShips = 5;
    private double cellSize = 30.0;
    private boolean needInput = true;
    private boolean clicked = false;
    private boolean rotationNeeded = true;
    private boolean isRotated = false;
    private boolean boatDragged = false;
    private boolean boatsPlaced = true;
    private Rectangle selectedShip;
    private AIEngine aiEngine = new AIEngine();

    @FXML
    private Rectangle boat1;
    @FXML
    private GridPane pcDisplayBoard;
    @FXML
    private GridPane playerDisplayBoard;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Rectangle boat3;
    @FXML
    private Rectangle boat4;
    @FXML
    private Button startButton;
    @FXML
    private Rectangle boat5;
    @FXML
    private Rectangle boat7;


    /**
     * Thi method initializes the controller.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Alert startPop = new Alert(AlertType.INFORMATION);
        startPop.setTitle("Welcome");
        startPop.setHeaderText(null);
        startPop.setContentText("BattleshipApplication Game!\nPlot your ships on the player's grid.\n(Drag n drop to plot, click to rotate)");
        startPop.showAndWait();
        playerGrid = new PlayerAbstractBoard(playerDisplayBoard);
        computerGrid = new PCAbstractBoard(pcDisplayBoard);
        reset();
    }

    /**
     * Add listeners on ships in form of rectangles.
     *
     * @param ship the Shape that the ship listeners are being installed on
     */
    private void installBoatListeners(Node ship) {

        ship.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	rotationNeeded = true;
            	clicked = true;
                Rectangle rect = (Rectangle) ship;
                selectShip(rect);
                if (ship.rotateProperty().getValue() == 0) {
                    isRotated = false;
                } else {
                    isRotated = true;
                }
                double localX = ship.getLayoutX() - 225;
                double localY = ship.getLayoutY() - 334;
                if (isRotated) {
                    localX = localX + selectedShip.getWidth() / 2;
                    localY = localY - selectedShip.getWidth() / 2 + cellSize;
                }
                int size = (int) ((int) selectedShip.getWidth() / cellSize);
                int x = (int) (localX / cellSize);
                int y = (int) (localY / cellSize);
                ShipFactory shipToRemove = null;
                for (ShipFactory ship : playerGrid.shipFleet) {
                    if (ship.getStartX() == x && ship.getStartY() == y) {
                        shipToRemove = ship;
                        for (int x2 = shipToRemove.getStartX(); x2 <= shipToRemove.getEndX(); x2++) {
                            for (int y2 = shipToRemove.getStartY(); y2 <= shipToRemove.getEndY(); y2++) {
                                playerGrid.gameBoard.get(x2).get(y2).setShip(null);
                            }
                        }
                        if (shipToRemove != null) {
                            if (shipToRemove.getIsSet()) {
                                if (!isRotated) {
                                    if (playerDisplayBoard.contains(localX, localY)) {
                                        for (int i = 0; i < size && i <= 9 - x; i++) {
                                            Rectangle r = (Rectangle) playerGrid.getNode(x + i, y);
                                            r.setFill(Color.BLACK);
                                        }
                                    }
                                }
                                if (isRotated) {
                                    if (playerDisplayBoard.contains(localX, localY)) {
                                        for (int i = 0; i < size && i <= 9 - y; i++) {
                                            Rectangle r = (Rectangle) playerGrid.getNode(x, y + i);
                                            r.setFill(Color.BLACK);
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

                if (playerGrid.shipFleet.remove(shipToRemove)) {
                    startButton.disableProperty().set(true);
                }
                ship.setCursor(Cursor.MOVE);
            }
        });
        ship.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clicked = false;
                ship.setCursor(Cursor.HAND);
                boatDragged = false;
            }
        });
        
        ship.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ship.setCursor(Cursor.HAND);
            }
        });
        
        ship.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if (rotationNeeded) {
                    if (ship.rotateProperty().getValue() == 0) {
                        ship.setRotate(90.0);
                    } else ship.setRotate(0);
                }
            }
        });
        
        ship.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rotationNeeded = false;
                if (ship.rotateProperty().getValue() == 0) {
                    isRotated = false;
                } else {
                    isRotated = true;
                }

                boatDragged = true;

                if (isRotated) {
                    ship.setLayoutX(mouseEvent.getSceneX() - (ship.getBoundsInLocal().getWidth() / 2));
                    ship.setLayoutY(mouseEvent.getSceneY() + (ship.getBoundsInLocal().getWidth() / 3));
                } else {
                    ship.setLayoutX(mouseEvent.getSceneX());
                    ship.setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
    }

    void addCompBoardListeners(PCAbstractBoard grid) {
        for (Node node : pcDisplayBoard.getChildren()) {
            Rectangle rect = (Rectangle) node;
            rect.disableProperty().set(false);

            rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    Rectangle r = (Rectangle) event.getSource();
                    GridPane g = (GridPane) r.getParent();

                    int col = GridPane.getColumnIndex(r);
                    int row = GridPane.getRowIndex(r);

                    if (computerGrid.gameBoard.get(col).get(row).isClicked()) {
                        return;
                    }

                    computerGrid.hit(col, row, aiEngine);

                    if (grid.checkVictory()) {
                        Alert winAlert = new Alert(AlertType.INFORMATION);
                        winAlert.setTitle("WINNER");
                        winAlert.setHeaderText(null);
                        winAlert.setContentText("Congratulations, you are a winner.\nThanks for playing!");
                        winAlert.showAndWait();
                        reset();
                    } else {
                        //Now it is computer's turn
                        try {
                            TimeUnit.MILLISECONDS.sleep(200);
                            //sleep is added to simulate real gameplay
                        } catch (InterruptedException ex) {
                            Logger.getLogger(BattleshipController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        int x = aiEngine.nextX();
                        int y = aiEngine.nextY();

                        //Since we don't hit same spot again
                        while (playerGrid.gameBoard.get(x).get(y).isClicked()) {
                            aiEngine.generate();
                            x = aiEngine.nextX();
                            y = aiEngine.nextY();
                        }
                        playerGrid.hit(x, y, aiEngine);
                        if (playerGrid.checkVictory()) {
                            Alert defeatPopUp = new Alert(AlertType.INFORMATION);
                            defeatPopUp.setTitle("LOSER");
                            defeatPopUp.setHeaderText(null);
                            defeatPopUp.setContentText("Sorry you lose the game.\nThanks for playing!");
                            defeatPopUp.showAndWait();
                            reset();
                        }
                    }
                }
            });
            
            rect.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    rect.setStrokeWidth(0.5);
                }
            });

            rect.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (!computerGrid.gameBoard.get(GridPane.getColumnIndex(rect)).get(GridPane.getRowIndex(rect)).isClicked())
                        rect.setStrokeWidth(3.0);
                }
            });            
        }
    }

    /**
     * Adding listeners to player's board.
     */
    void addPlayerBoardListener(PlayerAbstractBoard grid) {
        anchor.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!clicked)
                    return;

                anchor.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseRelease) {

                    	int size = (int) ((int) selectedShip.getWidth() / cellSize);
                        double localX = mouseRelease.getX() - 225.0;
                        double localY = mouseRelease.getY() - 334.0;
                        if (playerDisplayBoard.contains(localX, localY)) {
                            int x = (int) (localX / cellSize);
                            int y = (int) (localY / cellSize);
                            Rectangle r = (Rectangle) playerGrid.getNode(x, y);
                            int endingXCoord, endingYCoord;
                            if (isRotated) {
                                selectedShip.setLayoutX(r.getLayoutX() + r.getParent().getTranslateX() - selectedShip.getWidth() / 2 + cellSize / 2);
                                selectedShip.setLayoutY(r.getLayoutY() + r.getParent().getTranslateY() + size * cellSize - selectedShip.getWidth() / 2 - cellSize / 2 + 5);
                                endingXCoord = x;
                                endingYCoord = y + size - 1;
                            } else {
                                selectedShip.setLayoutX(r.getLayoutX() + r.getParent().getTranslateX());
                                selectedShip.setLayoutY(r.getLayoutY() + r.getParent().getTranslateY() + 5);
                                endingXCoord = x + size - 1;
                                endingYCoord = y;
                            }

                            ShipFactory ship = new ShipFactory(size, x, y, endingXCoord, endingYCoord);
                            grid.addShip(ship, isRotated);


                            Paint color;
                            if (grid.isShipValid(x, y, size, isRotated)) {
                                color = Color.GREEN;
                                playerGrid.shipFleet.add(ship);
                                ship.setIsSet(true);
                                if (playerGrid.shipFleet.size() == 5)
                                    startButton.disableProperty().set(false);

                                for (int i = 0; i < size && i < 9; i++) {
                                    if (isRotated) {
                                        if (y + i <= 9)
                                            r = (Rectangle) playerGrid.getNode(x, y + i);

                                    } else {
                                        if (x + i <= 9)
                                            r = (Rectangle) playerGrid.getNode(x + i, y);
                                    }
                                    r.setFill(color);
                                }
                            }
                        }
                        anchor.setOnMouseReleased(null);
                    }
                });
            }
        });
    }

    /**
     * This method sets game ready to play
     *
     * @param event
     */
    @FXML
    private void startGame(ActionEvent event) {
        Alert startPop = new Alert(AlertType.INFORMATION);
        startPop.setTitle("Start Game");
        startPop.setHeaderText(null);
        startPop.setContentText("You will play first.\nSelect one grid alternatively on the top board to hit opponent's Ship.");
        startPop.showAndWait();

        selectShip(null);

        //paint all cells in player's board to black
        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 9; y++) {
                ((Rectangle) playerGrid.getNode(x, y)).setFill(Color.BLACK);
            }
        }

        //Setting all listeners to null to begin
        for (Node node : anchor.getChildren()) {
        	node.setOnMousePressed(null);
        	node.setOnMouseDragged(null);
            node.setOnMouseReleased(null);
            node.setOnMouseClicked(null);
            node.setOnMouseEntered(null);
        }
        //anchors to null
        anchor.setOnMouseDragged(null);
        anchor.setOnMouseReleased(null);
        //computer's grid initialized
        computerGrid.initGrid();
        addCompBoardListeners(computerGrid);
        startButton.disableProperty().set(true);
    }
    
    /**
     * Resets both boards and ships on them
     */
    public void reset() {
        startButton.disableProperty().set(true);

        selectedShip = boat1;

        //repaint both the display boards
        computerGrid.initGrid();
        playerGrid.initGrid();
        addPlayerBoardListener(playerGrid);

        //Plot ships
        installBoatListeners(boat1);
        boat1.setLayoutX(30);
        boat1.setLayoutY(330);
        boat1.setRotate(0);
        
        installBoatListeners(boat3);
        boat3.setLayoutX(30);
        boat3.setLayoutY(360);
        boat3.setRotate(0);

        installBoatListeners(boat4);
        boat4.setLayoutX(30);
        boat4.setLayoutY(390);
        boat4.setRotate(0);

        installBoatListeners(boat5);
        boat5.setLayoutX(30);
        boat5.setLayoutY(420);
        boat5.setRotate(0);

        installBoatListeners(boat7);
        boat7.setLayoutX(30);
        boat7.setLayoutY(450);
        boat7.setRotate(0);

        for (Node node : pcDisplayBoard.getChildren()) {
            Rectangle rect = (Rectangle) node;
            rect.setDisable(true);
        }

        aiEngine.reset();
    }
    
    /**
     * Point out selected ship
     *
     * @param The ship to point out
     * @return
     */
    private Rectangle selectShip(Rectangle ship) {
        selectedShip.setStroke(Color.WHITE);
        selectedShip.setStrokeWidth(1.0);
        if (ship != null) {
            ship.setStrokeWidth(2.5);
            ship.setStroke(Color.WHITE);
        }
        selectedShip = ship;
        return selectedShip;
    }

    class Delta {
        double x, y;
    }
}

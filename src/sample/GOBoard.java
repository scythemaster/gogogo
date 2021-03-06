package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

class   GOBoard extends Pane {

    private Rectangle background;
    // arrays for the lines that makeup the horizontal and vertical grid lines
    private Line[] horizontal;
    private Line[] vertical;
    // arrays holding translate objects for the horizontal and vertical grid lines
    private Translate[] horizontal_t;
    private Translate[] vertical_t;
    // arrays for the internal representation of the board and the pieces that are
    // in place
    private GOPiece[][] render;
    // the current player who is playing and who is his opposition
    private int current_player;
    private int opposing;
    // is the game currently in play
    private boolean in_play;
    // current scores of player 1 and player 2
    private int player1_score;
    private int player2_score;
    // the width and height of a cell in the board
    private double cell_width;
    private double cell_height;

    // default constructor for the class
    public GOBoard() {
        //current_player = XPIECE;

        background = new Rectangle();
        background.setFill(Color.BEIGE);
        horizontal = new Line[7];
        vertical = new Line[7];
        horizontal_t = new Translate[7];
        vertical_t = new Translate[7];
        getChildren().add(background);

        for (int i = 0; i < 7; i++) {
            horizontal_t[i] = new Translate(0, 0);
        }
        for (int i = 0; i < 7; i++) {
            vertical_t[i] = new Translate(0, 0);
        }
        for (int i = 0; i < 7; i++) {
            horizontal[i] = new Line();
            horizontal[i].setStartX(0);
            horizontal[i].setStartY(0);
            horizontal[i].setEndY(0);
            horizontal[i].setStroke(Color.BLACK);
            horizontal[i].getTransforms().add(horizontal_t[i]);
            getChildren().add(horizontal[i]);
        }

        for (int i = 0; i < 7; i++) {
            vertical[i] = new Line();
            vertical[i].setStartX(0);
            vertical[i].setStartY(0);
            vertical[i].setEndX(0);
            vertical[i].setStroke(Color.BLACK);
            vertical[i].getTransforms().add(vertical_t[i]);
            getChildren().add(vertical[i]);
        }

        render = new GOPiece[7][7];
        for(int i = 0; i < 7; i++)
            for(int j = 0; j < 7; j++) {
                render[i][j] = new GOPiece(0);
                getChildren().add(render[i][j]);
            }
        resetGame();
    }

    // public method that will try to place a piece in the given x,y coordinate
    public void placePiece(final double x, final double y) {
        final int cellx = (int) (x / cell_width);
        final int celly = (int) (y / cell_height);


        // if the game is not in play then do nothing
        if(!in_play)
            return;

        // if there is a piece already placed then return and do nothing
        if(render[cellx][celly].getPiece() != 0)
            return;

        if(current_player == 1)
            if(!render[cellx][celly].isLiberty_player1())
                return;
        if(current_player == 2)
            if(!render[cellx][celly].isLiberty_player2())
                return;
        render[cellx][celly].setPiece(current_player);
        checkBoard();
        swapPlayers();
        updateScores();
        //determineEndGame();

        // print out some information
        System.out.println("placed at: " + cellx + ", " + celly);
        if(current_player == 1)
            System.out.println("current player is White");
        else
            System.out.println("current player is Black");
    }

    // overridden version of the resize method to give the board the correct size
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        cell_width = width / 7.0;
        cell_height = height / 7.0;
        background.setWidth(width);
        background.setHeight(height);
        for (int i = 0; i < 7; i++) {
            horizontal[i].setStartX(cell_width / 2);
            horizontal[i].setStartY(cell_height / 2);
            vertical[i].setStartX(cell_width / 2);
            vertical[i].setStartY(cell_height / 2);
            horizontal[i].setEndX(width - (cell_width / 2));
            horizontal[i].setEndY(cell_height / 2);
            vertical[i].setEndY(height - (cell_height / 2));
            vertical[i].setEndX(cell_width / 2);
            horizontal_t[i].setY(i * cell_height);
            vertical_t[i].setX(i * cell_width);
        }
        // we need to reset the sizes and positions of all Pieces that were placed
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                if (render[i][j] != null) {
                    render[i][j].relocate(i * cell_width, j * cell_height);
                    render[i][j].resize(cell_width, cell_height);
                }
            }
        }
    }

    // public method for resetting the game
    public void resetGame() {
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                render[i][j].setPiece(0);
            }
        }
        in_play = true;

        render[2][3].setPiece(2);
        render[4][3].setPiece(2);
        render[3][4].setPiece(2);


        current_player = 2;
        opposing = 1;
        player1_score = 2;
        player2_score = 2;
    }

    // private method for swapping the players
    private void swapPlayers() {
        if (current_player == 1) {
            current_player = 2;
            opposing = 1;
        }
        else {
            current_player = 1;
            opposing = 2;
        }
    }

    // private method for updating the player scores
    private void updateScores() {
    }

    protected int getScorePlayer1() {
    return player1_score;
    }

    protected int getScorePlayer2() {
        return player2_score;
    }

    protected int getTerritoryPlayer1() {
        return player1_score;
    }

    protected int getTerritoryPlayer2() {
        return player2_score;
    }

    protected int getCurrent_player() {
        return current_player;
    }

    protected void setCurrent_player(int currentPlayer) {
       current_player = currentPlayer;
    }

    // private method for determining which pieces surround x,y will update the
    // surrounding array to reflect this
    private boolean checkOpposingPieces(final int x, final int y) {
        return false;
    }

    // private method for determining if a reverse can be made will update the can_reverse
    // array to reflect the answers will return true if a single reverse is found
    private boolean determineReverse(final int x, final int y) {
        return false;
    }

    // private method for determining if a reverse can be made from a position (x,y) for
    // a player piece in the given direction (dx,dy) returns true if possible
    // assumes that the first piece has already been checked
    private boolean isReverseChain(final int x, final int y, final int dx, final int dy, final int player) {
        return false;
    }

    public void checkBoard(){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                capture(i, j);
                setLiberty(i, j);
            }
        }
    }
    //GameLogic
    public void capture(int cellx, int celly){
        int opposing_pieces = 0;
        int opponent = 1;
        if (render[cellx][celly].getPiece() == 1)
            opponent = 2;
        if((cellx + 1) < 7){
            if (render[cellx + 1][celly].getPiece() == opponent)
                opposing_pieces ++;
        }
        if((cellx - 1) >= 0){
            if (render[cellx - 1][celly].getPiece() == opponent)
                opposing_pieces ++;
        }
        if((celly + 1) < 7){
            if (render[cellx][celly + 1].getPiece() == opponent)
                opposing_pieces ++;
        }
        if((celly - 1) >= 0){
            if (render[cellx][celly - 1].getPiece() == opponent)
                opposing_pieces ++;
        }
        if (opposing_pieces == 4)
            render[cellx][celly].setPiece(0);
    }

    public void setLiberty(int cellx, int celly){
        int opposing_pieces = 0;
        int opponent = 1;
        if (render[cellx][celly].getPiece() == 1)
            opponent = 2;
        if((cellx + 1) < 7){
            if (render[cellx + 1][celly].getPiece() == opponent)
                opposing_pieces ++;
        }
        if((cellx - 1) >= 0){
            if (render[cellx - 1][celly].getPiece() == opponent)
                opposing_pieces ++;
        }
        if((celly + 1) < 7){
            if (render[cellx][celly + 1].getPiece() == opponent)
                opposing_pieces ++;
        }
        if((celly - 1) >= 0){
            if (render[cellx][celly - 1].getPiece() == opponent)
                opposing_pieces ++;
        }

        if (opposing_pieces >= 3)
            render[cellx][celly].setLiberty_player(render[cellx][celly].getPiece());
    }
}

package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

/**
 * Created by guill on 12/11/2017.
 */

class GOBoard extends Pane {

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
    // 3x3 array that holds the pieces that surround a given piece
    private int[][] surrounding;
    // 3x3 array that determines if a reverse can be made in any direction
    private boolean[][] can_reverse;

    // default constructor for the class
    public GOBoard() {
        //current_player = XPIECE;

        background = new Rectangle();
        background.setFill(Color.CYAN);
        horizontal = new Line[8];
        vertical = new Line[8];
        horizontal_t = new Translate[8];
        vertical_t = new Translate[8];
        getChildren().add(background);

        for (int i = 0; i < 8; i++) {
            horizontal_t[i] = new Translate(0, 0);
        }
        for (int i = 0; i < 8; i++) {
            vertical_t[i] = new Translate(0, 0);
        }
        for (int i = 0; i < 8; i++) {
            horizontal[i] = new Line();
            horizontal[i].setStartX(0);
            horizontal[i].setStartY(0);
            horizontal[i].setEndY(0);
            horizontal[i].setStroke(Color.BLACK);
            horizontal[i].getTransforms().add(horizontal_t[i]);
            getChildren().add(horizontal[i]);
        }

        for (int i = 0; i < 8; i++) {
            vertical[i] = new Line();
            vertical[i].setStartX(0);
            vertical[i].setStartY(0);
            vertical[i].setEndX(0);
            vertical[i].setStroke(Color.BLACK);
            vertical[i].getTransforms().add(vertical_t[i]);
            getChildren().add(vertical[i]);
        }

        render = new GOPiece[8][8];
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++) {
                render[i][j] = new GOPiece(0);
                getChildren().add(render[i][j]);
            }

        surrounding = new int[3][3];
        can_reverse = new boolean[3][3];
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

        // determine what pieces surround the current piece. if there is no opposing
        // pieces then a valid move cannot be made.
        if (!checkOpposingPieces(cellx, celly)){
            return;
        }
        // see if a reverse can be made in any direction if none can be made then return
        if(!determineReverse(cellx, celly))
            return;

        // at this point we have done all the checks and they have passed so now we can place
        // the piece and perform the reversing also check if the game has ended
        // if we get to this point then a successful move has been made so swap the
        // players and update the scores
        swapPlayers();
        updateScores();
        //determineEndGame();

        // print out some information
        System.out.println("placed at: " + cellx + ", " + celly);
        System.out.println("White: " + player1_score + " Black: " + player2_score);
        if(current_player == 1)
            System.out.println("current player is White");
        else
            System.out.println("current player is Black");
    }

    // overridden version of the resize method to give the board the correct size
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        cell_width = width / 8.0;
        cell_height = height / 8.0;
        background.setWidth(width);
        background.setHeight(height);
        for (int i = 0; i < 8; i++) {
            horizontal[i].setEndX(width);
            vertical[i].setEndY(height);
            horizontal_t[i].setY(i * cell_height);
            vertical_t[i].setX(i * cell_width);
        }
        // we need to reset the sizes and positions of all Pieces that were placed
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if (render[i][j] != null) {
                    render[i][j].relocate(i * cell_width, j * cell_height);
                    render[i][j].resize(cell_width, cell_height);
                }
            }
        }
    }

    // public method for resetting the game
    public void resetGame() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                render[i][j].setPiece(0);
            }
        }
        in_play = true;

        render[3][3].setPiece(1);
        render[4][4].setPiece(1);
        render[3][4].setPiece(2);
        render[4][3].setPiece(2);

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
        player2_score = 0;
        player1_score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(render[i][j].getPiece() == 1)
                    player1_score ++;
                else if (render[i][j].getPiece() == 2)
                    player2_score ++;
            }
        }
    }

    // private method for determining which pieces surround x,y will update the
    // surrounding array to reflect this
    private boolean checkOpposingPieces(final int x, final int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if((y + j) < 8 && (y + j) >= 0 && (x + i) < 8 && (x + i) >= 0){
                    if(render[x + i][y + j].getPiece() == opposing)
                        return true;
                }
            }
        }
        return false;
    }

    // private method for determining if a reverse can be made will update the can_reverse
    // array to reflect the answers will return true if a single reverse is found
        private boolean determineReverse(final int x, final int y) {
        boolean play = false;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if((x + i) < 8 && (x + i) > 0 && (y + j) < 8 && (y + j) > 0){
                    if(render[x + i][y + j].getPiece() != current_player){
                        if (isReverseChain(x, y, j, i, opposing)){
                            reverseChain(x, y, j, i);
                            render[x][y].setPiece(current_player);
                            play = true;
                        }
                    }
                }
            }
        }
        return play;
    }

    // private method for determining if a reverse can be made from a position (x,y) for
    // a player piece in the given direction (dx,dy) returns true if possible
    // assumes that the first piece has already been checked
    private boolean isReverseChain(final int x, final int y, final int dx, final int dy, final int player) {
        for (int i = 1; i < 8; i++) {
            if ((i*dx + x) < 8 && (i*dx + x) >= 0 && ((i * dy)  + y) < 8 && ((i * dy)  + y) >= 0){
                if (render[(i * dx) + x][(i * dy)  + y].getPiece() == 0){
                    return false;
                }
                if(render[(i * dx) + x][(i * dy)  + y].getPiece() == current_player){
                    return true;
                }
                } else return false;
            }
        return false;
    }

    // private method to reverse a chain
    private void reverseChain(final int x, final int y, final int dx, final int dy) {
        for (int i = 1; render[(i * dx) + x][(i * dy)  + y].getPiece() != current_player; i++) {
            if ((i*dx + x) < 8 && (i*dx + x) > 0 && ((i * dy)  + y) < 8 && ((i * dy)  + y) > 0){
                if(render[(i * dx) + x][(i * dy)  + y].getPiece() == opposing){
                    render[(i * dx) + x][(i * dy)  + y].swapPiece();
                }
            }
        }
    }

    // private method for getting a piece on the board. this will return the board
    // value unless we access an index that doesnt exist. this is to make the code
    // for determing reverse chains much easier
    private int getPiece(final int x, final int y) {
        if (x < 0 || x > 8 || y < 0 || y > 8)
            return -1;
        return render[x][y].getPiece();
    }

    // private method that will determine if the end of the game has been reached
    private void determineEndGame() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (render[i][j].getPiece() == 0){
                   // render[i][j].canMove();
                }
            }
        }
    }

    // private method to determine if a player has a move available
    private boolean canMove() {
        return false;
    }

    // private method that determines who won the game
    private void determineWinner() {

    }
}

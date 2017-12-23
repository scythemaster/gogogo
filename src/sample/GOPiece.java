package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Translate;

class GOPiece extends Group {

    private int player;		// the player that this piece belongs to
    private boolean liberty_player1;
    private boolean liberty_player2;
    private Ellipse piece;	// ellipse representing the player's piece
    private Translate t;	// translation for the player piece

    public GOPiece(int player) {
// create a new translate object and take a copy of the type
        t = new Translate();
        this.player = player;
        piece = new Ellipse();
        getChildren().addAll(piece);
        piece.getTransforms().add(t);
        piece.setVisible(false);
        liberty_player1 = true;
        liberty_player2 = true;
        if (player == 1) {
            piece.setVisible(true);
            piece.setFill(Color.WHITE);
        }
        else if (player == 2) {
            piece.setVisible(true);
            piece.setFill(Color.BLACK);
        }
    }

    // overridden version of the resize method to give the piece the correct size
    @Override
    public void resize(double width, double height) {
        // call the super class method
        super.resize(width, height);
// recenter the ellipse// and update the radii
        piece.setCenterX(width / 2);
        piece.setCenterY(height / 2);
        piece.setRadiusX(width / 2);
        piece.setRadiusY(height / 2);
    }

    // overridden version of the relocate method to position the piece correctly
    @Override
    public void relocate(double x, double y) {
        super.relocate(x, y);
        t.setX(x);
        t.setY(y);
    }

    // public method that will swap the colour and type of this piece
    public void swapPiece() {
        if (player == 1){
            player = 2;
            piece.setFill(Color.BLACK);
        }
        else if (player == 2){
            player = 1;
            piece.setFill(Color.WHITE);
        }

    }

    // method that will set the piece type
    public void setPiece(final int player) {
        this.player = player;
        if (player == 0){
            piece.setVisible(false);
        }
        else if (player == 1) {
            piece.setVisible(true);
            piece.setFill(Color.WHITE);
        }
        else if (player == 2) {
            piece.setVisible(true);
            piece.setFill(Color.BLACK);
        }
    }

    public void setLiberty_player(final int player){
        if(player == 1)
            liberty_player1 = false;
        if(player == 2)
            liberty_player2 = false;
    }

    // returns the type of this piece
    public int getPiece() {
        return player;
    }

    public boolean isLiberty_player1(){
        return liberty_player1;
    }

    public boolean isLiberty_player2() {
        return liberty_player2;
    }
}

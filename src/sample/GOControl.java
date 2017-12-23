package sample;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

class GOControl extends Control {

    private GOBoard rb_board;
    private GoControlPanel goControlPanel;

    // constructor for the class
    public GOControl() {
        setSkin(new GOControlSkin(this));
        rb_board = new GOBoard();
        goControlPanel = new GoControlPanel(rb_board);
        getChildren().add(rb_board);
        setOnMouseClicked(event -> rb_board.placePiece(event.getX(), event.getY()));
        setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.SPACE) {
                System.out.println("SPACE pressed");
                rb_board.resetGame();
            }
            if(event.getCode() == KeyCode.ENTER) {
                if (rb_board.getCurrent_player() == 1) {
                    rb_board.setCurrent_player(2);
                }
                else if (rb_board.getCurrent_player() == 2){
                    rb_board.setCurrent_player(1);
                }
            }
        });
    }

    // overridden version of the resize method
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        rb_board.resize(width, height);
    }
}

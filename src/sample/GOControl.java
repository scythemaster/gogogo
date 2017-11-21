package sample;

import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;

/**
 * Created by guill on 12/11/2017.
 */


class GOControl extends Control {

    private GOBoard rb_board;

    // constructor for the class
    public GOControl() {
        setSkin(new GOControlSkin(this));
        rb_board = new GOBoard();
        getChildren().add(rb_board);
        setOnMouseClicked(event -> rb_board.placePiece(event.getX(), event.getY()));
        setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.SPACE)
                rb_board.resetGame();
        });
    }

    // overridden version of the resize method
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        rb_board.resize(width, height);
    }
}

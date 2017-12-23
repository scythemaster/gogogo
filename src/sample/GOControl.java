package sample;

import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;

/**
 * Created by guill on 12/11/2017.
 */


class GOControl extends Control {

    private GOBoard goboard;

    // constructor for the class
    public GOControl() {
        setSkin(new GOControlSkin(this));
        goboard = new GOBoard();
        getChildren().add(goboard);
        setOnMouseClicked(event -> goboard.placePiece(event.getX(), event.getY()));
        setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.SPACE)
                goboard.resetGame();
        });
    }

    // overridden version of the resize method
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        goboard.resize(width, height);
    }
}

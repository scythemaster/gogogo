package sample;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GoControlPanel extends Pane {

    private GOBoard goBoard;
    protected TextField scorePlayerOne;
    protected TextField scorePlayerTwo;
    protected TextField territoryPlayerOne;
    protected TextField territoryPlayerTwo;
    protected TextField current_player;
    protected VBox vb;

    public GoControlPanel(GOBoard goBoard) {
        this.goBoard = goBoard;
        this.current_player = new TextField();
        this.scorePlayerOne = new TextField();
        this.scorePlayerTwo = new TextField();
        this.territoryPlayerOne = new TextField();
        this.territoryPlayerTwo = new TextField();
        current_player.setText("" + goBoard.getCurrent_player());
        territoryPlayerOne.setText("" + goBoard.getTerritoryPlayer1());
        territoryPlayerTwo.setText("" + goBoard.getTerritoryPlayer2());
        scorePlayerOne.setText("" + goBoard.getScorePlayer1());
        scorePlayerTwo.setText("" + goBoard.getScorePlayer2());
        this.vb = new VBox();
        this.getChildren().add(vb);
        vb.getChildren().add(new Label("Go is an abstract strategy board game for two players,\n" +
                "in which the aim is to surround more territory than the opponent.\n" +
                "to do this you can capture stones or territory.\n" +
                " "));
        vb.getChildren().addAll (new Label("Current player:"), current_player);
        vb.getChildren().addAll (new Label("Score Player One (prisoners):"), scorePlayerOne);
        vb.getChildren().addAll (new Label("Territory Player One:"), territoryPlayerOne);
        vb.getChildren().addAll (new Label("Score Player Two (prisoners):"), scorePlayerTwo);
        vb.getChildren().addAll (new Label("Territory Player Two:"), territoryPlayerTwo);
    }
}

package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GO extends Application {

    private GOControl Go;
    private GoControlPanel controlPanel;
    private BorderPane bp_layout;
    private GOBoard goBoard;

    public void init(){
        bp_layout = new BorderPane();
        Go = new GOControl();
        goBoard = new GOBoard();
        controlPanel = new GoControlPanel(goBoard);
        bp_layout.setCenter(Go);
        bp_layout.setRight(controlPanel);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GO !!! GO !!! GO !!!");
        primaryStage.setScene(new Scene(bp_layout, 1000, 700));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package sample;/**
 * Created by guill on 21/11/2017.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GO extends Application {

    private StackPane sp_mainlayout;
    private GOControl Go;

    public void init(){
        sp_mainlayout = new StackPane();
        Go = new GOControl();
        sp_mainlayout.getChildren().add(Go);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GO !!! GO !!! GO !!!");
        primaryStage.setScene(new Scene(sp_mainlayout, 700, 700));
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

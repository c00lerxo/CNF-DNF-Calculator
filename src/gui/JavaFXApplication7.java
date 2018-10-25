/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author HOME
 */
public class JavaFXApplication7 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        //loader.setController()
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.setTitle("CNF and DNF calculator v1.0 GUI");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}

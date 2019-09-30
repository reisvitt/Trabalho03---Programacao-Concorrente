
/***********************************************************************************
  * Autor: Vitor de Almeida Reis
  * Matricula: 201710793
  * Inicio: 14/09/2019
  * Ultima alteracao: 15/09/2019 
  * Nome: Problema da Corrida
  * Funcao: Simulacao resolvendo problemas de programacao concorrente (Variavel de travamento)
  **********************************************************************************/
  
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.Controller;


public class Principal extends Application {

  @Override
  public void start(Stage primaryStage) {
    try {
      Parent fxmlhome = FXMLLoader.load(getClass().getResource("/view/telaConcorrente.fxml"));
      primaryStage.setScene(new Scene(fxmlhome));
      primaryStage.setTitle("Programação Concorrente");
      primaryStage.setResizable(false);
      primaryStage.centerOnScreen();
      primaryStage.show();

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

}

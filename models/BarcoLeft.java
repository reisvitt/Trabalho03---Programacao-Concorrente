/******************************************************
 * Classe:  BarcoLeft
 * Funcao: controla todas as Threads responsaveis pelos
 *          barcos do lado esquerdo
 * 
 *****************************************************/
package models;
import java.lang.Thread;
import controller.Controller; 

public class BarcoLeft extends Thread {
  private boolean kill;
  private Controller controlador;
  private int id;
  private int veloz = 60;


  public BarcoLeft(){
  }

  @Override
  public void run(){
    while(true){
      if(kill){// quando Kill for verdadeiro, deverá sair do laco e finalizar a Thread
        return;
      }
      switch(this.id){ // direciona as Thread para a sua rota de acordo com o seu id
        case 0:
          controlador.caminhoLeftBarco1();
          break;
        case 1:
          controlador.caminhoLeftBarco2();
          break;
        case 2:
          controlador.caminhoLeftBarco3();
          break;
        case 3:
          controlador.caminhoLeftBarco4();
          break;
        case 4:
          controlador.caminhoLeftBarco5();
          break;

      }

      try {
        sleep(this.veloz);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }//Fim método

  /************************************************************
   * Metodo: setVeloz
   * Funcao: controlar a velocidade da Thread
   * Parametros: int -> velocidade
   * Retorno: void
   ***********************************************************/
  public void setVeloz(int veloz){
    this.veloz = veloz;
  }

  /************************************************************
   * Metodo: setController
   * Funcao: dar acesso ao controlador da telaConcorrente.fxml
   * Parametros: Controller
   * Retorno: void
   ***********************************************************/
  public void setController(Controller controller){
    this.controlador = controller;
  }

  /************************************************************
   * Metodo: getID
   * Funcao: retornar o ID desta Thread
   * Parametros: void
   * Retorno: int -> ID
   ***********************************************************/
  public int getID(){
    return this.id;
  }

  /************************************************************
   * Metodo: setID
   * Funcao: setar o ID desta Thread
   * Parametros: int -> ID
   * Retorno: void
   ***********************************************************/
  public void setID(int id){
    this.id = id;
  }

  /*****************************************************************
   * Metodo: setKill
   * Funcao: setar a variavel Kill desta Thread, podendo finaliza-la
   * Parametros: URL, ResourceBudle
   * Retorno: void
   *****************************************************************/
  public void setKill(boolean valor){
    this.kill = valor;
  }
}

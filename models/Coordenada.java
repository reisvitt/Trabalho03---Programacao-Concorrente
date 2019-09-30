/******************************************************
 * Classe:  Coordenada
 * Funcao: cria o objeto Coordenada, onde armazena os 
 *          valores de X e Y 
 *****************************************************/

package models;

public class Coordenada{
    private double x;
    private double y;
    
    /*************************************************************
   * Metodo: Coordenada
   * Funcao: Construtor da classe
   * Parametros: (double, double)
   * Retorno: void
   ***********************************************************/
    public Coordenada(double x, double y){
        this.x = x;
        this.y = y;
    }

    /************************************************************
   * Metodo: setX
   * Funcao: setar o  valor de X
   * Parametros: (double)
   * Retorno: void
   ***********************************************************/
    public void setX(double x){
        this.x = x;
    }

    /************************************************************
   * Metodo: getX
   * Funcao: retornar o valor de X
   * Parametros: void
   * Retorno: (double)
   ***********************************************************/
    public double getX(){
        return this.x;
    }

    /************************************************************
   * Metodo: setY
   * Funcao: setar o  valor de Y
   * Parametros: (double)
   * Retorno: void
   ***********************************************************/
    public void setY(double y){
        this.y = y;
    }

    /************************************************************
   * Metodo: getY
   * Funcao: retornar o valor de Y
   * Parametros: void
   * Retorno: (double)
   ***********************************************************/
    public double getY(){
        return this.y;
    }
}
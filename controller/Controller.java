/******************************************************
 * Classe:  Controller
 * Funcao: Classe responsável por controlar todas as
 *         acoes da view telaConcorrente.fxml
 * 
 *****************************************************/
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.control.Spinner;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.DragEvent;
import javafx.event.EventHandler;
import java.lang.Thread;

import models.*;

public class Controller implements Initializable {
  @FXML
  private AnchorPane root, apresentacao;

  public ImageView[] imagesLeft = new ImageView[5];
  public Slider[] slidersLeft = new Slider[5];

  public ImageView[] imagesRight = new ImageView[5];
  public Slider[] slidersRight = new Slider[5];

  private ArrayList<Coordenada> coordenadasLeft = new ArrayList<>();
  private ArrayList<Coordenada> coordenadasRight = new ArrayList<>();
  private ArrayList<Coordenada> coordenadasSlidersLeft = new ArrayList<>();
  private ArrayList<Coordenada> coordenadasSlidersRight = new ArrayList<>();


  @FXML
  private ImageView bg;

  @FXML
  public ImageView go1, go2, stop1, stop2;

  //VARIAVEL DE TRAVAMENTO SAO AS BANDEIRA
  // GO1 e STOP1 - visivel para os barcos do lado esquerdo
  // GO2 e STOP2 - visivel para os barcos do lado direito


  @FXML
  private Spinner<Integer> spinnerRight, spinnerLeft;

  public BarcoRight[] barcoRight = new BarcoRight[5];
  public BarcoLeft[] barcoLeft = new BarcoLeft[5];


  public Controller(){

    spinnerRight = new Spinner<>();
    spinnerLeft = new Spinner<>();
    
  }

  /************************************************************
   * Metodo: initialize
   * Funcao: metodo pradrao no javafx
   * Parametros: URL, ResourceBudle
   * Retorno: void
   ***********************************************************/
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    
    SpinnerValueFactory<Integer> spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
    SpinnerValueFactory<Integer> spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
    spinnerRight.setValueFactory(spinner1);
    spinnerLeft.setValueFactory(spinner2);
    
    carregarCoordenadas();
    
  }

  @FXML
  public void fecharApresentacao(){
    apresentacao.setVisible(false);

    inicializarThreadEsquerda(1);
    inicializarThreadDireita(1);
  }

  /************************************************************
   * Metodo: spinnerRightEvent
   * Funcao: controla o spinner de lado eireito, criando ou
             deletando Threads
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  @FXML
  public void spinnerRightEvent(){
    int valor = spinnerRight.getValue();

    if(barcoRight[valor - 1] == null){
      inicializarThreadDireita(valor);
      
    }else if (valor == 1 && barcoRight[valor] == null){ // caso seja o primeiro e o segundo barco esteja morto
      //faz nada
      return;
    }else if (valor == 5 && barcoRight[valor - 1] != null) { // caso seja o ultimo e já esteja vivo
      //faz nada
      return;

    }else{
      matarThreadDireita(valor);
    }
  }

  /************************************************************
   * Metodo: spinnerLeftEvent
   * Funcao: controla o spinner de lado esquerdo, criando ou
             deletando Threads
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  @FXML
  public void spinnerLeftEvent(){
    int valor = spinnerLeft.getValue();

    if(barcoLeft[valor - 1] == null){
      inicializarThreadEsquerda(valor);
      
    }else if (valor == 1 && barcoLeft[valor] == null){ // caso seja o primeiro e o segundo barco esteja morto
      //faz nada
      return;
    }else if (valor == 5 && barcoLeft[valor - 1] != null) { // caso seja o ultimo e já esteja vivo
      //faz nada
      return;

    }else{
      matarThreadEsquerda(valor);
    }
  }

  /************************************************************
   * Metodo: inicializarThreadEsquerda
   * Funcao: instanciar e dar start nas Threads
   * Parametros: int -> representa o ID da Thread
   * Retorno: void
   ***********************************************************/
  public void inicializarThreadEsquerda(int valor){

    //carregar imagem do barco
    imagesLeft[valor - 1] = new ImageView(new Image("/imagens/barco.png")); // carregar Imagem do barco0
    imagesLeft[valor - 1].setFitWidth(130); // setar largura
    imagesLeft[valor - 1].setFitHeight(115); // setar altura
    imagesLeft[valor - 1].setRotate(270); // setar rotacao da imagem
    imagesLeft[valor - 1].setX(coordenadasLeft.get(valor - 1).getX()); // setar a coordenada X na imagem
    imagesLeft[valor - 1].setY(coordenadasLeft.get(valor - 1).getY()); // setar a coordenada Y na imagem

    //carregar slider do barco
    slidersLeft[valor - 1] = new Slider();
    slidersLeft[valor - 1].setMax(50);
    slidersLeft[valor - 1].setMin(0);
    slidersLeft[valor - 1].setBlockIncrement(5);
    slidersLeft[valor - 1].setLayoutX(coordenadasSlidersLeft.get(valor - 1).getX());
    slidersLeft[valor - 1].setLayoutY(coordenadasSlidersLeft.get(valor - 1).getY());
    slidersLeft[valor - 1].setPrefWidth(60);

    root.getChildren().add(imagesLeft[valor - 1]);
    root.getChildren().add(slidersLeft[valor -1]);
    

    //eventos de click
    slidersLeft[valor - 1].setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            sliderControlLeft(valor - 1);
          }
    });

    slidersLeft[valor - 1].setOnDragDropped(new EventHandler<DragEvent>(){
          @Override
          public void handle(DragEvent event) {
            sliderControlLeft(valor - 1);
          }
    });

    slidersLeft[valor - 1].setOnDragDone(new EventHandler<DragEvent>(){
          @Override
          public void handle(DragEvent event) {
            sliderControlLeft(valor - 1);
          }
    });

    barcoLeft[valor - 1] = new BarcoLeft();
    barcoLeft[valor - 1].setController(this);
    barcoLeft[valor - 1].setID(valor - 1);
    barcoLeft[valor - 1].start();
  }

  /************************************************************
   * Metodo: inicializarThreadDireita
   * Funcao: instanciar e dar start nas Threads
   * Parametros: int -> representa o ID da Thread
   * Retorno: void
   ***********************************************************/
  public void inicializarThreadDireita(int valor){

    //carregar imagem do barco
    imagesRight[valor - 1] = new ImageView(new Image("/imagens/barco.png")); // carregar Imagem do barco0
    imagesRight[valor - 1].setFitWidth(130); // setar largura
    imagesRight[valor - 1].setFitHeight(115); // setar altura
    imagesRight[valor - 1].setRotate(90); // setar rotacao da imagem
    imagesRight[valor - 1].setX(coordenadasRight.get(valor - 1).getX()); // setar a coordenada X na imagem
    imagesRight[valor - 1].setY(coordenadasRight.get(valor - 1).getY()); // setar a coordenada Y na imagem

    //carregar slider do barco
    slidersRight[valor - 1] = new Slider();
    slidersRight[valor - 1].setMax(50);
    slidersRight[valor - 1].setMin(0);
    slidersRight[valor - 1].setBlockIncrement(5);
    slidersRight[valor - 1].setLayoutX(coordenadasSlidersRight.get(valor - 1).getX());
    slidersRight[valor - 1].setLayoutY(coordenadasSlidersRight.get(valor - 1).getY());
    slidersRight[valor - 1].setPrefWidth(60);

    root.getChildren().add(imagesRight[valor - 1]);
    root.getChildren().add(slidersRight[valor -1]);
    

    //eventos de click
    slidersRight[valor - 1].setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            sliderControlRight(valor - 1);
          }
    });

    slidersRight[valor - 1].setOnDragDropped(new EventHandler<DragEvent>(){
          @Override
          public void handle(DragEvent event) {
            sliderControlRight(valor - 1);
          }
    });

    slidersRight[valor - 1].setOnDragDone(new EventHandler<DragEvent>(){
          @Override
          public void handle(DragEvent event) {
            sliderControlRight(valor - 1);
          }
    });

    barcoRight[valor - 1] = new BarcoRight();
    barcoRight[valor - 1].setController(this);
    barcoRight[valor - 1].setID(valor - 1);
    barcoRight[valor - 1].start();
  }

  /************************************************************
   * Metodo: matarThreadEsquerda
   * Funcao: finalizar as Threads do lado esquerdo
   * Parametros: int -> represeta o ID da Thread a ser finalizada
   * Retorno: void
   ***********************************************************/
  public void matarThreadEsquerda(int valor){
    if(imagesLeft[valor].getX() > 126 &&  imagesLeft[valor].getX() < 685){
      //Thread esta na zona compartilhada, logo deve habilitar a bandeira verde
      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    root.getChildren().remove(imagesLeft[valor]);
    root.getChildren().remove(slidersLeft[valor]);

    barcoLeft[valor].setKill(true);
    barcoLeft[valor] = null;
  }

  /************************************************************
   * Metodo: matarThreadDireita
   * Funcao: finalizar as Threads do lado esquerdo
   * Parametros: int -> represeta o ID da Thread a ser finalizada
   * Retorno: void
   ***********************************************************/
  public void matarThreadDireita(int valor){
    if(imagesRight[valor].getX() < 790 &&  imagesRight[valor].getX() > 150){
      //Thread esta na zona compartilhada, logo deve habilitar a bandeira verde
      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    root.getChildren().remove(imagesRight[valor]);
    root.getChildren().remove(slidersRight[valor]);

    barcoRight[valor].setKill(true);
    barcoRight[valor] = null;
  }

  /************************************************************
   * Metodo: caminhoLeftBarco1
   * Funcao: rota da Thread do barcoLeft1
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoLeftBarco1(){

    imagesLeft[0].setX(imagesLeft[0].getX() + 1);

    if(imagesLeft[0].getX() == 125){ // local para verificao da bandeira


      while(go1.isVisible() == false){ // verificao da bandeira
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      } // diferente de visivel -> ficar no loop
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesLeft[0].getX() == 685){  // ponto de saida da zona compartilhada
      go2.setVisible(true);
      go1.setVisible(true);
      stop1.setVisible(false);
      stop2.setVisible(false);
    }

    if(imagesLeft[0].getX() >= 125 && imagesLeft[0].getX() < 300){ // leve curva para entrar no canal
      imagesLeft[0].setX(imagesLeft[0].getX() + 1);
      imagesLeft[0].setY(imagesLeft[0].getY() - 1.75);

    } 

    if(imagesLeft[0].getX() > 570 && imagesLeft[0].getX() < 680){ // leve curva para alinhar a direita na saida do canal
      imagesLeft[0].setX(imagesLeft[0].getX() + 0.5);
      imagesLeft[0].setY(imagesLeft[0].getY() + 2);
    }

    if(imagesLeft[0].getX() >= 680 && imagesLeft[0].getX() < 681){ // ponto de saida da zona compartilhada
      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    if(imagesLeft[0].getX() >= 900 && imagesLeft[0].getX() < 901){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesLeft[0].setX( coordenadasLeft.get(0).getX());
      imagesLeft[0].setY( coordenadasLeft.get(0).getY());
    }
  
  }

  /************************************************************
   * Metodo: caminhoLeftBarco2
   * Funcao: rota da Thread do barcoLeft2
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoLeftBarco2(){
    imagesLeft[1].setX(imagesLeft[1].getX() + 1);

    if(imagesLeft[1].getX() == 125){ // local para verificao da bandeira

      while(go1.isVisible() == false) { // verificao da bandeira
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      }// diferente de visivel -> ficar no loop
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesLeft[1].getX() >= 125 && imagesLeft[1].getX() < 300){ // leve curva para entrar no canal
      imagesLeft[1].setX(imagesLeft[1].getX() + 1);
      imagesLeft[1].setY(imagesLeft[1].getY() - 1);

    }

    if(imagesLeft[1].getX() > 570 && imagesLeft[1].getX() < 680){ // leve curva para alinhar a direita na saida do canal
      imagesLeft[1].setX(imagesLeft[1].getX() + 0.5);
      imagesLeft[1].setY(imagesLeft[1].getY() + 2);
    }

    if(imagesLeft[1].getX() == 680){ // ponto de saida da zona compartilhada
      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

     if(imagesLeft[1].getX() >= 900 && imagesLeft[1].getX() < 901){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesLeft[1].setX( coordenadasLeft.get(1).getX());
      imagesLeft[1].setY( coordenadasLeft.get(1).getY());
    }
  }

  /************************************************************
   * Metodo: caminhoLeftBarco3
   * Funcao: rota da Thread do barcoLeft3
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoLeftBarco3(){
    imagesLeft[2].setX(imagesLeft[2].getX() + 1);

    if(imagesLeft[2].getX() == 125){ // local para verificao da bandeira


      while(go1.isVisible() == false){// verificao da bandeira
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      } // diferente de visivel -> ficar no loop
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesLeft[2].getX() == 685){ // ponto de saida da zona compartilhada
      go2.setVisible(true);
      go1.setVisible(true);
      stop1.setVisible(false);
      stop2.setVisible(false);
    }

    if(imagesLeft[2].getX() >= 125 && imagesLeft[2].getX() < 300){ // leve curva para entrar no canal
      imagesLeft[2].setX(imagesLeft[2].getX() + 1);
      imagesLeft[2].setY(imagesLeft[2].getY() - 0.5);

    }
    
    if(imagesLeft[2].getX() > 570 && imagesLeft[2].getX() < 680){ // leve curva para alinhar a direita na saida do canal
      imagesLeft[2].setX(imagesLeft[2].getX() + 0.5);
      imagesLeft[2].setY(imagesLeft[2].getY() + 2);
    }

    if(imagesLeft[2].getX() >= 680 && imagesLeft[2].getX() < 681){ // ponto de saida da zona compartilhada

      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    if(imagesLeft[2].getX() >= 900 && imagesLeft[2].getX() < 901){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesLeft[2].setX( coordenadasLeft.get(2).getX());
      imagesLeft[2].setY( coordenadasLeft.get(2).getY());
    }
  }

  /************************************************************
   * Metodo: caminhoLeftBarco4
   * Funcao: rota da Thread do barcoLeft4
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoLeftBarco4(){
    imagesLeft[3].setX(imagesLeft[3].getX() + 1);

    if(imagesLeft[3].getX() == 125){ // local para verificao da bandeira


      while(go1.isVisible() == false){ // verificao da bandeira
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      } // diferente de visivel -> ficar no loop
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesLeft[3].getX() == 685){ // ponto de saida da zona compartilhada
      go2.setVisible(true);
      go1.setVisible(true);
      stop1.setVisible(false);
      stop2.setVisible(false);
    }

    if(imagesLeft[3].getX() >= 125 && imagesLeft[3].getX() < 300){ // leve curva para entrar no canal
      imagesLeft[3].setX(imagesLeft[3].getX() + 1);
      imagesLeft[3].setY(imagesLeft[3].getY() + 0.1);

    }
    
    if(imagesLeft[3].getX() > 570 && imagesLeft[3].getX() < 680){ // leve curva para alinhar a direita na saida do canal
      imagesLeft[3].setX(imagesLeft[3].getX() + 0.5);
      imagesLeft[3].setY(imagesLeft[3].getY() + 2);
    }

    if(imagesLeft[3].getX() >= 680 && imagesLeft[3].getX() < 681){ // ponto de saida da zona compartilhada

      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    if(imagesLeft[3].getX() >= 900 && imagesLeft[3].getX() < 901){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesLeft[3].setX( coordenadasLeft.get(3).getX());
      imagesLeft[3].setY( coordenadasLeft.get(3).getY());
    }
  }

  /************************************************************
   * Metodo: caminhoLeftBarco5
   * Funcao: rota da Thread do barcoLeft5
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoLeftBarco5(){
    imagesLeft[4].setX(imagesLeft[4].getX() + 1);

    if(imagesLeft[4].getX() == 125){ // local para verificao da bandeira


      while(go1.isVisible() == false){ // verificao da bandeira
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      } // diferente de visivel -> ficar no loop
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesLeft[4].getX() == 685){ // ponto de saida da zona compartilhada
      go2.setVisible(true);
      go1.setVisible(true);
      stop1.setVisible(false);
      stop2.setVisible(false);
    }

    if(imagesLeft[4].getX() >= 125 && imagesLeft[4].getX() < 300){ // leve curva para entrar no canal
      imagesLeft[4].setX(imagesLeft[4].getX() + 1);
      imagesLeft[4].setY(imagesLeft[4].getY() + 0.53);

    }
    
    if(imagesLeft[4].getX() > 570 && imagesLeft[4].getX() < 680){ // leve curva para alinhar a direita na saida do canal
      imagesLeft[4].setX(imagesLeft[4].getX() + 0.5);
      imagesLeft[4].setY(imagesLeft[4].getY() + 2);
    }

    if(imagesLeft[4].getX() >= 680 && imagesLeft[4].getX() < 681){ // ponto de saida da zona compartilhada

      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    if(imagesLeft[4].getX() >= 900 && imagesLeft[4].getX() < 901){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesLeft[4].setX( coordenadasLeft.get(4).getX());
      imagesLeft[4].setY( coordenadasLeft.get(4).getY());
    }
  }

  /************************************************************
   * Metodo: caminhoRightBarco1
   * Funcao: rota da Thread do barcoRight1
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoRightBarco1(){

    imagesRight[0].setX(imagesRight[0].getX() - 1);

    if(imagesRight[0].getX() == 795){ // local para verificao da bandeira

      while(go2.isVisible() == false){ // diferente de visivel -> ficar no loop && // verificao da bandeira
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      } 
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesRight[0].getX() <= 795 && imagesRight[0].getX() > 600){ //leve curva para alinhar o barco ao centro
      imagesRight[0].setX(imagesRight[0].getX() - 1);
      imagesRight[0].setY(imagesRight[0].getY() - 0.4);
    }

    if(imagesRight[0].getX() <= 149.5 && imagesRight[0].getX() > 148.5){ // ponto de saida da zona compartilhada
      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    if(imagesRight[0].getX() <= 325 && imagesRight[0].getX() > 200){ // leve curva para alinhar a direita na saida do canal
      imagesRight[0].setX(imagesRight[0].getX() - 0.5);
      imagesRight[0].setY(imagesRight[0].getY() - 2);
    }

    if(imagesRight[0].getX() == -125){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesRight[0].setX( coordenadasRight.get(0).getX());
      imagesRight[0].setY( coordenadasRight.get(0).getY());
    }
  
  }

  /************************************************************
   * Metodo: caminhoRightBarco2
   * Funcao: rota da Thread do barcoRight2
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoRightBarco2(){

    imagesRight[1].setX(imagesRight[1].getX() - 1); // movimento do barco

    if(imagesRight[1].getX() == 795){ // local para verificao da bandeira

      while(go2.isVisible() == false){ // diferente de visivel -> ficar no loop obs: verificao da bandeira  
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      } 
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesRight[1].getX() <= 795 && imagesRight[1].getX() > 600){ //leve curva para alinhar o barco ao centro
      imagesRight[1].setX(imagesRight[1].getX() - 1);
      imagesRight[1].setY(imagesRight[1].getY() + 0.2);
    }

    if(imagesRight[1].getX() <= 149.5 && imagesRight[1].getX() > 148.5){ // ponto de saida da zona compartilhada
      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    if(imagesRight[1].getX() <= 325 && imagesRight[1].getX() > 200){ // leve curva para alinhar a direita na saida do canal
      imagesRight[1].setX(imagesRight[1].getX() - 0.5);
      imagesRight[1].setY(imagesRight[1].getY() - 2);
    }

    if(imagesRight[1].getX() == -125){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesRight[1].setX( coordenadasRight.get(1).getX());
      imagesRight[1].setY( coordenadasRight.get(1).getY());
    }
  }

  /************************************************************
   * Metodo: caminhoRightBarco3
   * Funcao: rota da Thread do barcoRight3
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoRightBarco3(){
    imagesRight[2].setX(imagesRight[2].getX() - 1); // movimento do barco

    if(imagesRight[2].getX() == 795){ // local para verificao da bandeira

      while(go2.isVisible() == false){ // diferente de visivel -> ficar no loop obs: verificao da bandeira
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      } 
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesRight[2].getX() <= 795 && imagesRight[2].getX() > 600){ //leve curva para alinhar o barco ao centro
      imagesRight[2].setX(imagesRight[2].getX() - 1);
      imagesRight[2].setY(imagesRight[2].getY() + 0.55);
    }

    if(imagesRight[2].getX() <= 149.5 && imagesRight[2].getX() > 148.5){ // ponto de saida da zona compartilhada
      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    if(imagesRight[2].getX() <= 325 && imagesRight[2].getX() > 200){ // leve curva para alinhar a direita na saida do canal
      imagesRight[2].setX(imagesRight[2].getX() - 0.5);
      imagesRight[2].setY(imagesRight[2].getY() - 2);
    }

    if(imagesRight[2].getX() == -125){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesRight[2].setX( coordenadasRight.get(2).getX());
      imagesRight[2].setY( coordenadasRight.get(2).getY());
    }
  }

  /************************************************************
   * Metodo: caminhoRightBarco4
   * Funcao: rota da Thread do barcoRight4
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoRightBarco4(){
    imagesRight[3].setX(imagesRight[3].getX() - 1); // movimento do barco

    if(imagesRight[3].getX() == 795){ // local para verificao da bandeira

      while(go2.isVisible() == false){ // diferente de visivel -> ficar no loop obs: verificao da bandeira
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      } 
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesRight[3].getX() <= 795 && imagesRight[3].getX() > 600){ //leve curva para alinhar o barco ao centro
      imagesRight[3].setX(imagesRight[3].getX() - 1);
      imagesRight[3].setY(imagesRight[3].getY() + 1.1);
    }

    if(imagesRight[3].getX() <= 149.5 && imagesRight[3].getX() > 148.5){ // ponto de saida da zona compartilhada
      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    if(imagesRight[3].getX() <= 325 && imagesRight[3].getX() > 200){ // leve curva para alinhar a direita na saida do canal
      imagesRight[3].setX(imagesRight[3].getX() - 0.5);
      imagesRight[3].setY(imagesRight[3].getY() - 2);
    }

    if(imagesRight[3].getX() == -125){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesRight[3].setX( coordenadasRight.get(3).getX());
      imagesRight[3].setY( coordenadasRight.get(3).getY());
    }
  }

  /************************************************************
   * Metodo: caminhoRightBarco5
   * Funcao: rota da Thread do barcoRight5
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void caminhoRightBarco5(){
    imagesRight[4].setX(imagesRight[4].getX() - 1); // movimento do barco

    if(imagesRight[4].getX() == 795){ // local para verificao da bandeira

      while(go2.isVisible() == false){ // diferente de visivel -> ficar no loop obs: verificao da bandeira
        try{
          Thread.sleep(60);
        }catch(InterruptedException ex){
          System.out.println("Erro ao esperar bandeira ficar verde");
        }
      } 
      go2.setVisible(false); // entra na zona critica, e tira a visibilidade da bandeira oposta
      go1.setVisible(false); // tira a visibilidade da bandeira adjacente
      stop1.setVisible(true); // exibe a bandeira STOP adjacente
      stop2.setVisible(true); // exibe a bandeira STOP aposta
    }

    if(imagesRight[4].getX() <= 795 && imagesRight[4].getX() > 600){ //leve curva para alinhar o barco ao centro
      imagesRight[4].setX(imagesRight[4].getX() - 1);
      imagesRight[4].setY(imagesRight[4].getY() + 1.7);
    }

    if(imagesRight[4].getX() <= 149.5 && imagesRight[4].getX() > 148.5){ // ponto de saida da zona compartilhada
      stop1.setVisible(false);
      stop2.setVisible(false);
      go2.setVisible(true);
      go1.setVisible(true);
    }

    if(imagesRight[4].getX() <= 325 && imagesRight[4].getX() > 200){ // leve curva para alinhar a direita na saida do canal
      imagesRight[4].setX(imagesRight[4].getX() - 0.5);
      imagesRight[4].setY(imagesRight[4].getY() - 2);
    }

    if(imagesRight[4].getX() == -125){ // ao passar pelo mapa inteiro, ele volta para o ponto inicial
      imagesRight[4].setX( coordenadasRight.get(4).getX());
      imagesRight[4].setY( coordenadasRight.get(4).getY());
    }
  }

  /************************************************************
   * Metodo: carregarCoordenadas
   * Funcao: cria uma lista com todas as coordendas iniciais
              dos barcos (direito e esquerdo), das imagens dos
              barcos e dos Sliders
   * Parametros: void
   * Retorno: void
   ***********************************************************/
  public void carregarCoordenadas(){
    //carregar Coordenadas dos Barcos
    Coordenada barco1Left = new Coordenada(-110, 325); // criar um objeto Coordenada que representa onde deve começar
    Coordenada barco2Left = new Coordenada(-110, 270); 
    Coordenada barco3Left = new Coordenada(-110, 215); 
    Coordenada barco4Left = new Coordenada(-110, 160); 
    Coordenada barco5Left = new Coordenada(-110, 105); 
    coordenadasLeft.add(barco1Left); // add Coordenada do barco0 a lista de coordenadas
    coordenadasLeft.add(barco2Left);
    coordenadasLeft.add(barco3Left);
    coordenadasLeft.add(barco4Left);
    coordenadasLeft.add(barco5Left);

    Coordenada barco1Right = new Coordenada(1000, 210);
    Coordenada barco2Right = new Coordenada(1000, 155); 
    Coordenada barco3Right = new Coordenada(1000, 100); 
    Coordenada barco4Right = new Coordenada(1000, 45); 
    Coordenada barco5Right = new Coordenada(1000, -10); 
    coordenadasRight.add(barco1Right);
    coordenadasRight.add(barco2Right);
    coordenadasRight.add(barco3Right);
    coordenadasRight.add(barco4Right);
    coordenadasRight.add(barco5Right);


    //carregar Coordenadas dos Sliders
    Coordenada slider1Left = new Coordenada(14, 410);
    Coordenada slider2Left = new Coordenada(78, 410);
    Coordenada slider3Left = new Coordenada(138, 410);
    Coordenada slider4Left = new Coordenada(201, 410);
    Coordenada slider5Left = new Coordenada(265, 410);
    coordenadasSlidersLeft.add(slider1Left);
    coordenadasSlidersLeft.add(slider2Left);
    coordenadasSlidersLeft.add(slider3Left);
    coordenadasSlidersLeft.add(slider4Left);
    coordenadasSlidersLeft.add(slider5Left);

    Coordenada slider1Right = new Coordenada(689, 410);
    Coordenada slider2Right = new Coordenada(759, 410);
    Coordenada slider3Right = new Coordenada(813, 410);
    Coordenada slider4Right = new Coordenada(876, 410);
    Coordenada slider5Right = new Coordenada(940, 410);
    coordenadasSlidersRight.add(slider1Right);
    coordenadasSlidersRight.add(slider2Right);
    coordenadasSlidersRight.add(slider3Right);
    coordenadasSlidersRight.add(slider4Right);
    coordenadasSlidersRight.add(slider5Right);

  }

  /************************************************************
   * Metodo: sliderControllLeft
   * Funcao: controla todos os Sliders de velocidade das Thredas
              do lado esquerdo
   * Parametros: int -> ID da Thread
   * Retorno: void
   ***********************************************************/
  public void sliderControlLeft(int valor){
    switch(valor){
      case 0:
        double result = 60 - slidersLeft[valor].getValue();
        barcoLeft[valor].setVeloz((int) result);
        break;
      case 1:
        double result1 = 60 - slidersLeft[valor].getValue();
        barcoLeft[valor].setVeloz((int) result1);
        break;
      case 2:
        double result2 = 60 - slidersLeft[valor].getValue();
        barcoLeft[valor].setVeloz((int) result2);
        break;
      case 3:
        double result3 = 60 - slidersLeft[valor].getValue();
        barcoLeft[valor].setVeloz((int) result3);
        break;
      case 4:
        double result4= 60 - slidersLeft[valor].getValue();
        barcoLeft[valor].setVeloz((int) result4);
        break;
    }
  }

  /************************************************************
   * Metodo: sliderControllRight
   * Funcao: controla todos os Sliders de velocidade das Thredas
              do lado direito
   * Parametros: int -> ID da Thread
   * Retorno: void
   ***********************************************************/
  public void sliderControlRight(int valor){
    switch(valor){
      case 0:
        double result = 60 - slidersRight[valor].getValue();
        barcoRight[valor].setVeloz((int) result);
        break;
      case 1:
        double result1 = 60 - slidersRight[valor].getValue();
        barcoRight[valor].setVeloz((int) result1);
        break;
      case 2:
        double result2 = 60 - slidersRight[valor].getValue();
        barcoRight[valor].setVeloz((int) result2);
        break;
      case 3:
        double result3 = 60 - slidersRight[valor].getValue();
        barcoRight[valor].setVeloz((int) result3);
        break;
      case 4:
        double result4 = 60 - slidersRight[valor].getValue();
        barcoRight[valor].setVeloz((int) result4);
        break;
    }
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerBusca;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Pagina;
import model.Palavra;

/**
 *
 * @author Matheus Nascimento
 */
public class Interface2 extends Application {
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage palco) throws Exception {
        ControllerBusca search = new ControllerBusca();
        BorderPane raiz = new BorderPane();
        VBox subBox2 = new VBox(5); // 1
        VBox subBox1 = new VBox(15);
        raiz.setTop(subBox1);
        raiz.setCenter(subBox2);
        HBox positionBotoes = new HBox(5);
        subBox1.setAlignment(Pos.CENTER); // 2
        Button pesquisar = new Button("Pesquisar");
        pesquisar.setTooltip(new Tooltip("Pesquisar páginas"));
        Button editar = new Button("Editar");
        editar.setTooltip(new Tooltip("Editar páginas"));
        pesquisar.setAlignment(Pos.CENTER);
        editar.setAlignment(Pos.CENTER);
        positionBotoes.setAlignment(Pos.CENTER);
        Label rotuloDemo = new Label("Gougle FSA"); // 3
       
        rotuloDemo.setScaleX(3);
        rotuloDemo.setScaleY(3);
        rotuloDemo.setTranslateY(-30);
        TextField campoTexto = new TextField(); // 5
        campoTexto.setTooltip(new Tooltip(
                "Digite uma palavra"));
  
         
        Separator separadorHorizontal = new Separator(); // 7
        subBox2.getChildren().addAll(rotuloDemo, campoTexto);
        subBox2.setAlignment(Pos.CENTER);
        positionBotoes.getChildren().addAll(pesquisar, editar);
        subBox1.getChildren().addAll(subBox2, positionBotoes, separadorHorizontal);
        Scene cena = new Scene(raiz, 800, 400);
        palco.setTitle("Gougle FSA");
        palco.setScene(cena);
        palco.show();
        
        VBox paginas = new VBox(5);
        Scene cena2 = new Scene(paginas,800,400);
        
        pesquisar.setOnAction(new EventHandler() {
            
            @Override
            public void handle(Event event) {
                
                Palavra p = null;
                try {
                    p = (Palavra) search.search(campoTexto.getText());
                } catch (Exception ex) {
                    Logger.getLogger(Interface2.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(p != null){
                    
                    
                    
                    Iterator it = p.imprimirArquivos();
                    while(it.hasNext()){
                        Pagina pag = (Pagina) it.next();
                        ToggleButton pagina = new ToggleButton(pag.getNome());
                        paginas.setAlignment(Pos.CENTER);
                        paginas.setPrefWidth(10);
                        paginas.getChildren().add(pagina);
                    }
                    
                    palco.setScene(cena2);
                    palco.show();
                }
                else try {
                    if(search.addPalavra(campoTexto.getText()) == true){
                        System.out.println("Palavra encontrada entre os arquivos");
                    }
                    else{
                        System.out.println("Palavra não encontrada");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Interface2.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Interface2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}

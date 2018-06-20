/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        VBox subBox = new VBox(5); // 1
        VBox raiz = new VBox(15);
        HBox positionBotoes = new HBox(5);
        raiz.setAlignment(Pos.CENTER); // 2
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
        TextArea areaTexto = new TextArea(""); // 6
        areaTexto.setTooltip(new Tooltip(
                "Página selecionada para edição"));

        Separator separadorHorizontal = new Separator(); // 7
        areaTexto.visibleProperty().set(false);
        subBox.getChildren().addAll(rotuloDemo, campoTexto);
        subBox.setAlignment(Pos.CENTER);
        positionBotoes.getChildren().addAll(pesquisar, editar);
        raiz.getChildren().addAll(subBox, positionBotoes, separadorHorizontal, areaTexto);
        Scene cena = new Scene(raiz, 800, 400);
        palco.setTitle("Gougle FSA");
        palco.setScene(cena);
        palco.show();
    }

}

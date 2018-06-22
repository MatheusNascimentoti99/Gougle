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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
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

    private final ControllerBusca search = new ControllerBusca();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage palco) throws Exception {

        screenMain(palco);

    }

    public void screenMain(Stage palco) {

        BorderPane raiz = new BorderPane();
        VBox partePesquisa = new VBox(5); // 1
        VBox subBox = new VBox(15);
        VBox resultados = new VBox(5);
        HBox positionBotoes = new HBox(5);
        Scene cena = new Scene(subBox, 800, 400);

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

        partePesquisa.setAlignment(Pos.CENTER);
        positionBotoes.getChildren().addAll(pesquisar, editar);
        partePesquisa.getChildren().addAll(rotuloDemo, campoTexto, positionBotoes);
        subBox.setAlignment(Pos.CENTER); // 2
        subBox.getChildren().addAll(partePesquisa, separadorHorizontal, resultados);

        palco.setTitle("Gougle FSA");
        palco.setScene(cena);
        palco.show();

        pesquisar.setOnMouseClicked((Event event) -> {

            Palavra p = null;
            try {
                p = (Palavra) search.search(campoTexto.getText());
            } catch (Exception ex) {
                Logger.getLogger(Interface2.class.getName()).log(Level.SEVERE, null, ex);
            }
            Group root = new Group();
            Scene cena2 = new Scene(root, 800, 400);
            resultados.setLayoutY(0);
            resultados.setAlignment(Pos.CENTER);
            Slider deslizante = new Slider(); // 9
            deslizante.setShowTickLabels(true); // 10
            deslizante.setShowTickMarks(true); // 11
            deslizante.setOrientation(Orientation.VERTICAL);
            if (p != null) {
                int height = 0;
                Iterator it = p.imprimirArquivos();
                while (it.hasNext()) {
                    height += 5;
                    Pagina pag = (Pagina) it.next();
                    ToggleButton pagina = new ToggleButton(pag.getNome());
                    pagina.setPrefSize(780, 20);
                    resultados.setAlignment(Pos.CENTER);
                    resultados.getChildren().add(pagina);

                }
                resultados.setPrefHeight(height);

                root.getChildren().addAll(resultados, deslizante);

                palco.setScene(cena2);
                palco.show();
            }

        });
    }
}

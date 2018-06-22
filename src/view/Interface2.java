/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerBusca;
import java.io.FileNotFoundException;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    public void screenMain(Stage palco) throws FileNotFoundException {

        BorderPane raiz = new BorderPane();
        VBox partePesquisa = new VBox(20); // 1
        VBox subBox = new VBox(15);
        HBox positionBotoes = new HBox(5);
        Scene cena = new Scene(subBox, 800, 400);
        HBox barra = new HBox(5);

        TextArea campo = new TextArea();
        Button pesquisar = new Button("Pesquisar");
        pesquisar.setTooltip(new Tooltip("Pesquisar páginas"));
        pesquisar.setAlignment(Pos.CENTER);
        pesquisar.setPrefWidth(100);
        positionBotoes.setAlignment(Pos.CENTER);
        Label rotuloDemo = new Label("Gougle FSA"); // 3
        ListView listaResul = new ListView();
        listaResul.visibleProperty().set(false);
        listaResul.setPrefWidth(subBox.getWidth());
        rotuloDemo.setScaleX(3);
        rotuloDemo.setScaleY(3);
        TextField campoTexto = new TextField(); // 5
        campoTexto.setTooltip(new Tooltip(
                "Digite uma palavra"));
        Separator separadorHorizontal = new Separator(); // 7

        partePesquisa.setAlignment(Pos.CENTER);
        positionBotoes.getChildren().add(pesquisar);
        partePesquisa.getChildren().addAll(rotuloDemo, campoTexto, positionBotoes);
        partePesquisa.setTranslateY(10);
        subBox.setAlignment(Pos.CENTER); // 2
        barra.getChildren().addAll(listaResul);
        subBox.getChildren().addAll(partePesquisa, separadorHorizontal, barra);

        palco.setTitle("Gougle FSA");
        palco.setScene(cena);

        pesquisar.setOnMouseClicked((Event event) -> {
            listaResul.visibleProperty().set(true);
            Palavra p = null;
            try {
                p = (Palavra) search.search(campoTexto.getText());
            } catch (Exception ex) {
                Logger.getLogger(Interface2.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (p != null) {

                Iterator it = p.imprimirArquivos();
                while (it.hasNext()) {
                    Pagina pag = (Pagina) it.next();
                    ToggleButton pagina = new ToggleButton(pag.getNome());
                    pagina.setPrefWidth(listaResul.getWidth());
                    listaResul.getItems().add(pagina);
                    pagina.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            int i = 0;
                            int index = -1;
                            try {
                                for (Object pagina1 : search.getControlRead().getPaginas()) {
                                    if (pagina.getText().equals(((Pagina) pagina1).getNome())) {
                                        index = i;
                                        break;
                                    }
                                    i++;
                                    
                                }
                                System.out.println(pagina);
                                if(index == -1)
                                    throw new FileNotFoundException();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Interface2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                search.getControlRead().showFile(index);
                            } catch (Exception ex) {
                                Logger.getLogger(Interface2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
            }

        });

        palco.show();

    }

}

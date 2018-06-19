package view;

import controller.ControllerBusca;
import controller.ControllerPaginas;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Palavra;

public class Interface extends Application {

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        ControllerBusca c = new ControllerBusca();
        launch();

    }

    @Override
    public void start(Stage palco) throws Exception {
        ControllerBusca search = new ControllerBusca();

        VBox raiz = new VBox(10);
        raiz.setTranslateX(10);
        raiz.setTranslateY(20);

        Label lblTitulo = new Label("Digite a palavra para pesquisa");
        lblTitulo.setUnderline(true); // 1

        final TextField txtNome = new TextField();
        HBox hbNome = new HBox(10); // 2
        hbNome.getChildren().addAll(new Label("Nome"), txtNome);

        Button fechar = new Button("Fechar Aplicação");
        Button btnSubmeter = new Button("Pesquisar");
        btnSubmeter.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {

                System.out.println("\t\tResultado da pesquisa para \""
                        + txtNome.getText() + "\"\n");
                Palavra p = null;
                String palavraProcurada = txtNome.getText().toUpperCase();
                if(palavraProcurada != null)
                   try {
                       p = (Palavra) search.search(palavraProcurada);
                } catch (IOException ex) {
                }
                if(p != null)
                    System.out.println(p.toString());
                System.out.println(":(");
                
                
                // Podemos não ter um SO selecionado
                try {

                    FileWriter arq = new FileWriter("testeEditar\\texte.txt");
                    PrintWriter gravaArq = new PrintWriter(arq);
                    gravaArq.print(txtNome.getText());
                    arq.close();
                } catch (IOException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        fechar.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Platform.exit();
            }
        });

        raiz.getChildren().addAll(lblTitulo, hbNome, btnSubmeter, fechar);

        Scene cena = new Scene(raiz, 450, 250);
        palco.setTitle("GOUGLE SEARCH");
        palco.setScene(cena);
        palco.show();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerBusca;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Pagina;
import model.Palavra;
import util.Crescente;
import util.Decrescente;

/**
 *
 * @author Matheus Nascimento
 */
public class Interface extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage palco) throws Exception {

        screenMain(palco);

    }

    private ImageView icone(String nome, double height, double width) {
        ImageView icon = new ImageView(new Image(nome));
        icon.setFitHeight(height);
        icon.setFitWidth(width);
        return icon;
    }

    public void screenMain(Stage palco) throws FileNotFoundException, Exception {
        ControllerBusca search = new ControllerBusca();
        search.getControlPages().atualize();
        VBox partePesquisa = new VBox(20); // 1
        VBox raiz = new VBox(15);
        HBox positionBotoes = new HBox(5);
        Scene cena = new Scene(raiz, 800, 400);
        HBox campoResul = new HBox(5);
        campoResul.setAlignment(Pos.CENTER);
        TextArea campo = new TextArea();
        Button alterna = new Button();
        alterna.setGraphic(icone("icon/UpDown.png", 15, 20));
        Button pesquisar = new Button("Pesquisar");
        pesquisar.setTooltip(new Tooltip("Pesquisar páginas"));
        Button topPaginas = new Button("Top-K Páginas");
        topPaginas.setTooltip(new Tooltip("Páginas mais ou menos pesquisadas"));
        Button topPalavras = new Button("Top-K Palavras");
        topPalavras.setTooltip(new Tooltip("Palavras mais ou menos pesquisadas"));
        TextField kEscolhas = new TextField();
        kEscolhas.setTooltip(new Tooltip(
                "Digite K quantidades"));
        kEscolhas.setPrefWidth(75);
        kEscolhas.visibleProperty().set(false);
        Button crescente = new Button("Crescente");
        crescente.visibleProperty().set(false);
        Button decrescente = new Button("Decrescente");
        decrescente.visibleProperty().set(false);
        pesquisar.setAlignment(Pos.CENTER);
        topPaginas.setAlignment(Pos.CENTER);
        positionBotoes.setAlignment(Pos.CENTER);

        ListView listaResul = new ListView();
        listaResul.visibleProperty().set(false);
        listaResul.setPrefWidth(raiz.getWidth());
        listaResul.setTooltip(new Tooltip("Resultados de pesquisas"));
        Label titulo = new Label("Gougle FSA"); // 3
        titulo.setScaleX(3);
        titulo.setScaleY(3);

        TextField campoTexto = new TextField(); // 5
        campoTexto.setTooltip(new Tooltip(
                "Digite uma palavra"));
        partePesquisa.setAlignment(Pos.CENTER);
        positionBotoes.getChildren().addAll(alterna,pesquisar, topPaginas, topPalavras);
        partePesquisa.getChildren().addAll(titulo, campoTexto, positionBotoes);
        partePesquisa.setTranslateY(10);
        raiz.setAlignment(Pos.CENTER); // 2
        campoResul.getChildren().addAll(listaResul);
        raiz.getChildren().addAll(partePesquisa, campoResul);

        palco.setTitle("Gougle FSA");
        palco.setScene(cena);
        

        pesquisar.setOnMouseClicked((Event event) -> {

            LinkedList paginas = new LinkedList();
            try {
                search.getControlPages().atualize();
            } catch (Exception ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
            listaResul.getItems().clear();
            positionBotoes.getChildren().remove(kEscolhas);
            positionBotoes.getChildren().remove(crescente);
            positionBotoes.getChildren().remove(decrescente);
            try {
                search.getControlPages().atualize();
            } catch (Exception ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (campoTexto.getText().length() > 0) {
                listaResul.visibleProperty().set(true);

                try {
                    paginas = search.foundPages(campoTexto.getText());
                } catch (Exception ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (paginas != null) {
                    resulOrdemBusca(campoTexto, search, listaResul, paginas, search);
                } 
            }
        });
        alternar(listaResul, alterna);
        botaoTopPag(topPaginas, listaResul, kEscolhas, crescente, decrescente, positionBotoes, search);
        botaoTopPala(topPalavras, listaResul, kEscolhas, crescente, decrescente, positionBotoes, search);
        palco.show();

    }

    private static void resulOrdemBusca(TextField campoTexto, ControllerBusca search, ListView listaResul, LinkedList paginas, Comparator ordem) {
        LinkedList temp = search.getControlPages().sort(paginas, ordem);
        Iterator it = temp.iterator();
        while (it.hasNext()) {
            Pagina pag = (Pagina) it.next();
            ToggleButton pagina = new ToggleButton(pag.getNome());
            pagina.setPrefWidth(listaResul.getWidth());
            listaResul.getItems().add(pagina);
            pagina.setOnMouseClicked((Event event) -> {
                try {
                    search.getControlPages().atualize();
                } catch (Exception ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }

                int i = 0;
                int index = -1;
                try {
                    for (Object pagina1 : search.getControlPages().getPaginas()) {
                        if (pagina.getText().equals(((Pagina) pagina1).getNome())) {
                            index = i;
                            break;
                        }
                        i++;

                    }
                    System.out.println(pagina);
                    if (index == -1) {
                        throw new FileNotFoundException();
                    }

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    search.getControlPages().showFile(index);
                } catch (Exception ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public static void alternar(ListView listaResul, Button ordem) throws Exception {
        ordem.setOnMouseClicked((Event event) -> {
            ListView alternar = new ListView();
            while (listaResul.getItems().size() > 0) {
                alternar.getItems().add(listaResul.getItems().remove(listaResul.getItems().size()-1));
            }
            listaResul.setItems(alternar.getItems());
        });

    }


    public static void botaoOrdemPag(ListView listaResul, Button botaoOrdem, TextField kEscolhas, ControllerBusca search, Comparator ordem) throws Exception {
        search.getControlPages().atualize();
        botaoOrdem.setOnMouseClicked(new EventHandler() {
            Label pagina;

            @Override
            public void handle(Event event) {
                listaResul.getItems().clear();
                int k;
                try {
                    k = Integer.parseInt(kEscolhas.getText());
                } catch (NumberFormatException ex) {
                    k = 0;
                }
                //O algoritmo de ordenação usa uma fila, com isso a ordem é invertida.
                LinkedList listOrdem = null;
                try {
                    listOrdem = search.getControlPages().sort(search.getControlPages().getPaginas(), ordem);
                } catch (FileNotFoundException exe) {
                    pagina = new Label("Paginas não encontradas");
                    listaResul.getItems().add(pagina);
                }
                if (listOrdem != null) {
                    Iterator temp = listOrdem.iterator();
                    for (int i = 0; i < k && temp.hasNext(); i++) {
                        pagina = new Label((((Pagina) temp.next()).toString()));
                        listaResul.getItems().add(pagina);
                    }
                }
                listaResul.visibleProperty().set(true);

            }
        });

    }

    public static void botaoCresPala(ListView listaResul, Button crescente, TextField kEscolhas, ControllerBusca search) throws Exception {
        search.getControlPages().atualize();
        crescente.setOnMouseClicked(new EventHandler() {
            Label pagina;

            @Override
            public void handle(Event event) {
                listaResul.getItems().clear();
                int k;
                try {
                    k = Integer.parseInt(kEscolhas.getText());
                } catch (NumberFormatException ex) {
                    k = 0;
                }
                Queue fila = null;
                try {
                    //O algoritmo de ordenação usa uma fila, com isso a ordem é invertida.
                    fila = search.filaPalavras();

                } catch (Exception ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                    pagina = new Label("Não há palavras.");
                    listaResul.getItems().add(pagina);
                }

                if (fila != null) {
                    search.getSort().quickSort(fila, new Crescente());
                    for (int i = 0; i < k && !fila.isEmpty(); i++) {
                        listaResul.getItems().add(((Palavra) fila.remove()).toString());
                    }
                }
                listaResul.visibleProperty().set(true);

            }

        });
    }

    public static void botaoDecresPala(ListView listaResul, Button crescente, TextField kEscolhas, ControllerBusca search) throws Exception {
        search.getControlPages().atualize();
        crescente.setOnMouseClicked(new EventHandler() {
            Label pagina;

            @Override
            public void handle(Event event) {
                listaResul.getItems().clear();
                int k;
                try {
                    k = Integer.parseInt(kEscolhas.getText());
                } catch (NumberFormatException ex) {
                    k = 0;
                }
                Queue fila = null;
                try {
                    //O algoritmo de ordenação usa uma fila, com isso a ordem é invertida.
                    fila = search.filaPalavras();
                } catch (Exception ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                    pagina = new Label("Paginas não encontradas");
                    listaResul.getItems().add(pagina);
                }
                if (fila != null) {
                    search.getSort().quickSort(fila, new Decrescente());
                    for (int i = 0; i < k && !fila.isEmpty(); i++) {
                        listaResul.getItems().add(((Palavra) fila.remove()).toString());
                    }
                }
                listaResul.visibleProperty().set(true);

            }

        });
    }

    public static void botaoTopPag(Button topPaginas, ListView listaResul, TextField kEscolhas, Button crescente, Button decrescente, HBox positionBotoes, ControllerBusca search) throws Exception {
        search.getControlPages().atualize();
        topPaginas.setOnMouseClicked((Event event) -> {
            listaResul.getItems().clear();
            listaResul.visibleProperty().set(false);
            kEscolhas.visibleProperty().set(true);
            crescente.visibleProperty().set(true);
            decrescente.visibleProperty().set(true);
            if (positionBotoes.getChildren().size() < 5) {
                positionBotoes.getChildren().addAll(kEscolhas, crescente, decrescente);
            }
            try {
                botaoOrdemPag( listaResul,  crescente,  kEscolhas,  search, new Crescente());
            } catch (Exception ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
            try
            
            {
                botaoOrdemPag( listaResul,  decrescente,  kEscolhas,  search, new Decrescente());
            } catch (Exception ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }

    public static void botaoTopPala(Button topPalavras, ListView listaResul, TextField kEscolhas, Button crescente, Button decrescente, HBox positionBotoes, ControllerBusca search) throws Exception {
        search.getControlPages().atualize();
        topPalavras.setOnMouseClicked((Event event) -> {
            
            listaResul.getItems().clear();
            listaResul.visibleProperty().set(false);
            kEscolhas.visibleProperty().set(true);
            crescente.visibleProperty().set(true);
            decrescente.visibleProperty().set(true);
            if (positionBotoes.getChildren().size() < 5) {
                positionBotoes.getChildren().addAll(kEscolhas, crescente, decrescente);
            }
            try {
                botaoCresPala(listaResul, crescente, kEscolhas, search);
            } catch (Exception ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                botaoDecresPala(listaResul, decrescente, kEscolhas, search);
            } catch (Exception ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}

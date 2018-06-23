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
import java.util.LinkedList;
import java.util.Queue;
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

    public void screenMain(Stage palco) throws FileNotFoundException, Exception {
        ControllerBusca search = new ControllerBusca();
        search.getControlPages().atualize();
        VBox partePesquisa = new VBox(20); // 1
        VBox subBox = new VBox(15);
        HBox positionBotoes = new HBox(5);
        Scene cena = new Scene(subBox, 800, 400);
        HBox campoResul = new HBox(5);
        campoResul.setAlignment(Pos.CENTER);
        TextArea campo = new TextArea();

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
        listaResul.setPrefWidth(subBox.getWidth());
        listaResul.setTooltip(new Tooltip("Resultados de pesquisas"));
        Label titulo = new Label("Gougle FSA"); // 3
        titulo.setScaleX(3);
        titulo.setScaleY(3);

        TextField campoTexto = new TextField(); // 5
        campoTexto.setTooltip(new Tooltip(
                "Digite uma palavra"));
        Separator separadorHorizontal = new Separator(); // 7

        partePesquisa.setAlignment(Pos.CENTER);
        positionBotoes.getChildren().addAll(pesquisar, topPaginas, topPalavras);
        partePesquisa.getChildren().addAll(titulo, campoTexto, positionBotoes);
        partePesquisa.setTranslateY(10);
        subBox.setAlignment(Pos.CENTER); // 2
        campoResul.getChildren().addAll(listaResul);
        subBox.getChildren().addAll(partePesquisa, separadorHorizontal, campoResul);

        palco.setTitle("Gougle FSA");
        palco.setScene(cena);

        pesquisar.setOnMouseClicked((Event event) -> {
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
                Palavra p = null;
                try {
                    p = (Palavra) search.search(campoTexto.getText());
                } catch (Exception ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (p != null) {
                    LinkedList temp = search.getControlPages().sort(p.getPaginas(), p);
                    Iterator it = temp.iterator();
                    while (it.hasNext()) {
                        Pagina pag = (Pagina) it.next();
                        ToggleButton pagina = new ToggleButton(pag.getNome());
                        pagina.setPrefWidth(listaResul.getWidth());
                        listaResul.getItems().add(pagina);
                        pagina.setOnMouseClicked(new EventHandler() {

                            @Override
                            public void handle(Event event) {
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

                            }
                        });
                    }
                } else {
                    try {
                        if (search.addPalavra(campoTexto.getText()) == true) {
                            p = (Palavra) search.search(campoTexto.getText());
                            if (p != null) {

                                LinkedList temp = search.getControlPages().sort(p.getPaginas(), p);
                                Iterator it = temp.iterator();
                                while (it.hasNext()) {
                                    Pagina pag = (Pagina) it.next();
                                    ToggleButton pagina = new ToggleButton(pag.getNome());
                                    pagina.setPrefWidth(listaResul.getWidth());
                                    listaResul.getItems().add(pagina);
                                    pagina.setOnMouseClicked(new EventHandler() {

                                        @Override
                                        public void handle(Event event) {
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
                                        }
                                    });
                                }
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
        botaoTopPag(topPaginas, listaResul, kEscolhas, crescente, decrescente, positionBotoes, search);
        botaoTopPala(topPalavras, listaResul, kEscolhas, crescente, decrescente, positionBotoes, search);
        palco.show();

    }

    public static void botaoCres(ListView listaResul, Button crescente, TextField kEscolhas, ControllerBusca searc, Queue fila) {

    }

    public static void botaoCresPag(ListView listaResul, Button crescente, TextField kEscolhas, ControllerBusca search) {
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
                //O algoritmo de ordenação usa uma fila, com isso a ordem é invertida.
                LinkedList cresc = null;
                try {
                    cresc = search.getControlPages().sort(search.getControlPages().getPaginas(), new Crescente());
                } catch (FileNotFoundException exe) {
                    pagina = new Label("Paginas não encontradas");
                    listaResul.getItems().add(pagina);
                }
                if (cresc != null) {
                    Iterator temp = cresc.iterator();
                    for (int i = 0; i < k && temp.hasNext(); i++) {
                        pagina = new Label((((Pagina) temp.next()).toString()));
                        listaResul.getItems().add(pagina);
                    }
                }
                listaResul.visibleProperty().set(true);

            }

        });

    }

    public static void botaoDecresPag(ListView listaResul, Button decrescente, TextField kEscolhas, ControllerBusca search) {
        decrescente.setOnMouseClicked(new EventHandler() {
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
                LinkedList decres = null;
                try {
                    decres = search.getControlPages().sort(search.getControlPages().getPaginas(), new Decrescente());
                } catch (FileNotFoundException exe) {
                    pagina = new Label("Paginas não encontradas");
                    listaResul.getItems().add(pagina);
                }
                if (decres != null) {
                    Iterator temp = decres.iterator();
                    for (int i = 0; i < k && temp.hasNext(); i++) {
                        pagina = new Label((((Pagina) temp.next()).toString()));
                        listaResul.getItems().add(pagina);
                    }
                }
                listaResul.visibleProperty().set(true);

            }

        });

    }

    public static void botaoCresPala(ListView listaResul, Button crescente, TextField kEscolhas, ControllerBusca search) {
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
                search.getSort().quickSort(fila, new Crescente());
                if (fila != null) {
                    while (!fila.isEmpty()) {
                        listaResul.getItems().add(((Palavra) fila.remove()).toString());
                    }
                }
                listaResul.visibleProperty().set(true);

            }

        });
    }

    public static void botaoDecresPala(ListView listaResul, Button crescente, TextField kEscolhas, ControllerBusca search) {
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
                search.getSort().quickSort(fila, new Decrescente());
                if (fila != null) {
                    while (!fila.isEmpty()) {
                        listaResul.getItems().add(((Palavra) fila.remove()).toString());
                    }
                }
                listaResul.visibleProperty().set(true);

            }

        });
    }

    public static void botaoTopPag(Button topPaginas, ListView listaResul, TextField kEscolhas, Button crescente, Button decrescente, HBox positionBotoes, ControllerBusca search) {

        topPaginas.setOnMouseClicked((Event event) -> {
            botaoCresPag(listaResul, crescente, kEscolhas, search);
            botaoDecresPag(listaResul, decrescente, kEscolhas, search);
            listaResul.getItems().clear();
            listaResul.visibleProperty().set(false);
            kEscolhas.visibleProperty().set(true);
            crescente.visibleProperty().set(true);
            decrescente.visibleProperty().set(true);
            if (positionBotoes.getChildren().size() < 5) {
                positionBotoes.getChildren().addAll(kEscolhas, crescente, decrescente);
            }
        });
    }

    public static void botaoTopPala(Button topPalavras, ListView listaResul, TextField kEscolhas, Button crescente, Button decrescente, HBox positionBotoes, ControllerBusca search) {

        topPalavras.setOnMouseClicked((Event event) -> {
            botaoCresPala(listaResul, crescente, kEscolhas, search);
            botaoDecresPala(listaResul, decrescente, kEscolhas, search);
            listaResul.getItems().clear();
            listaResul.visibleProperty().set(false);
            kEscolhas.visibleProperty().set(true);
            crescente.visibleProperty().set(true);
            decrescente.visibleProperty().set(true);
            if (positionBotoes.getChildren().size() < 5) {
                positionBotoes.getChildren().addAll(kEscolhas, crescente, decrescente);
            }
        });
    }

}

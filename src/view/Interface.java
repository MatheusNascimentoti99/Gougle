/** *****************************************************************************
 * Autor: Matheus Nascimento e Elvis Serafim
 * Componente Curricular: Programa��o
 * Concluido em: 01/07/2018
 * Declaro que este c�digo foi elaborado por mim de forma individual e n�o cont�m nenhum
 * trecho de c�digo de outro colega ou de outro autor, tais como provindos de livros e
 * apostilas, e p�ginas ou documentos eletr�nicos da Internet. Qualquer trecho de c�digo
 * de outra autoria que n�o a minha est� destacado com uma cita��o para o autor e a fonte
 * do c�digo, e estou ciente que estes trechos n�o ser�o considerados para fins de avalia��o.
 ***************************************************************************************** */
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Pagina;
import model.Palavra;
import util.Crescente;
import util.Decrescente;

/**
 * A classe <b>Interface</b> classe onde est� a interface gr�fica do projeto.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 **/
 public class Interface extends Application {

    /**
     *
     * @param args
     */
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
        alterna.setGraphic(icone("icon/UpDown.png", 15, 20));   //Se apresentar erros, por favor, comente essa linha.
        Button pesquisar = new Button("Pesquisar");
        pesquisar.setTooltip(new Tooltip("Pesquisar paginas"));
        Button topPaginas = new Button("Top-K P�ginas");
        topPaginas.setTooltip(new Tooltip("P�ginas mais ou menos pesquisadas"));
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
            Thread thread = new Thread(search);
            thread.start();
            try {
                thread.sleep(3 * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
        Interface.botaoTopPag(topPaginas, listaResul, kEscolhas, crescente, decrescente, positionBotoes, search);
        botaoTopPala(topPalavras, listaResul, kEscolhas, crescente, decrescente, positionBotoes, search);
        palco.show();

    }


    private static void resulOrdemBusca(TextField campoTexto, ControllerBusca search, ListView listaResul, LinkedList paginas, Comparator ordem) {
        LinkedList temp = search.getControlPages().sort(paginas, ordem);
        Iterator it = temp.iterator();
        if(temp.isEmpty()){
            listaResul.getItems().add(new Label("Sem resultados de busca"));
        }
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

    /**
     *
     * @param listaResul
     * @param ordem
     * @throws Exception
     */
    public static void alternar(ListView listaResul, Button ordem) throws Exception {
        ordem.setOnMouseClicked((Event event) -> {
            ListView alternar = new ListView();
            while (listaResul.getItems().size() > 0) {
                alternar.getItems().add(listaResul.getItems().remove(listaResul.getItems().size()-1));
            }
            listaResul.setItems(alternar.getItems());
        });

    }


    private static void botaoOrdemPag(ListView listaResul, Button botaoOrdem, TextField kEscolhas, ControllerBusca search, Comparator ordem) throws Exception {
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
                //O algoritmo de ordena��o usa uma fila, com isso a ordem � invertida.
                LinkedList listOrdem = null;
                try {
                    listOrdem = search.getControlPages().sort(search.getControlPages().getPaginas(), ordem);
                } catch (FileNotFoundException exe) {
                    pagina = new Label("Paginas n�o encontradas");
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

    private static void botaoCresPala(ListView listaResul, Button crescente, TextField kEscolhas, ControllerBusca search) throws Exception {
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
                    //O algoritmo de ordena��o usa uma fila, com isso a ordem � invertida.
                    fila = search.filaPalavras();

                } catch (Exception ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                    pagina = new Label("N�o h� palavras.");
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


    private static void botaoDecresPala(ListView listaResul, Button crescente, TextField kEscolhas, ControllerBusca search) throws Exception {
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
                    //O algoritmo de ordena��o usa uma fila, com isso a ordem � invertida.
                    fila = search.filaPalavras();
                } catch (Exception ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                    pagina = new Label("Paginas n�o encontradas");
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


    private static void botaoTopPag(Button topPaginas, ListView listaResul, TextField kEscolhas, Button crescente, Button decrescente, HBox positionBotoes, ControllerBusca search) throws Exception {
        search.getControlPages().atualize();
        topPaginas.setOnMouseClicked((Event event) -> {
            Thread thread = new Thread(search);
            thread.start();
            try {
                thread.sleep(3 * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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


    private static void botaoTopPala(Button topPalavras, ListView listaResul, TextField kEscolhas, Button crescente, Button decrescente, HBox positionBotoes, ControllerBusca search) throws Exception {
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

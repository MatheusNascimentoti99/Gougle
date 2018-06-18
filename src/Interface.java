
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
        launch();
        ControllerBusca c = new ControllerBusca();
        Scanner scanner = new Scanner(new FileReader("C:\\Users\\Usuário 01\\Documents\\PBL 3 - Google Search\\Gougle\\testeEditar\\texte.txt"));
        String palavraProcurada = null;
        Scanner scanner2 = new Scanner(System.in);

        System.out.println(":)");
        // Depois eu crio uma classe para cuidar disso mano
        boolean opcao = true;
        while (opcao == true) {
            while (scanner.hasNext()) {
                palavraProcurada = scanner.next();
            }
            palavraProcurada = palavraProcurada.toUpperCase();
            String[] palavrasMultiplas = palavraProcurada.split(" ");
            Palavra a = null;
            for (String palavraMultipla : palavrasMultiplas) {
                Palavra p = (Palavra) c.search(palavraMultipla);
                if (c.search(palavraMultipla) != null) {
                    System.out.println(p.toString());
                    System.out.println("Palavra encontrada na arvore");

                } else if (c.addPalavra(palavraMultipla) == true) {
                    System.out.println("Palavra encontrada entre os arquivos");
                } else {
                    System.out.println("Palavra não encontrada");
                }
                a = p;
            }
            ControllerPaginas save = new ControllerPaginas();
            long ca = 1000;
            c.saveTree();
            System.out.println("Deseja procurar novamente ?");
            int verifica;
            verifica = scanner2.nextInt();
            if (verifica == 1) {
                opcao = true;
            } else {
                opcao = false;
            }

        }

    }

    @Override
    public void start(Stage palco) throws Exception {
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
                // Podemos não ter um SO selecionado
                try {
                    FileWriter arq = new FileWriter("C:\\Users\\Usuário 01\\Documents\\PBL 3 - Google Search\\Gougle\\testeEditar\\texte.txt");
                    PrintWriter gravaArq = new PrintWriter(arq);
                    gravaArq.print(txtNome.getText());
                    arq.close();
                    Platform.exit();

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

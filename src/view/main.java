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
import java.util.Scanner;
import model.Pagina;
import model.Palavra;
import util.Crescente;
import util.Decrescente;

/**
 *
 * @author Matheus Nascimento
 */
public class main {

    public static void main(String[] args) throws IOException, Exception {
        ControllerBusca controleBusca = new ControllerBusca();
        System.out.println("--------------------------- Gougle FSA ---------------------------");
        String sair;
        do {
            int opcao = escolha();
            switch (opcao) {
                case 1:
                    pesquisa(controleBusca);
                    break;
                case 2:
                    visitPage(controleBusca);
                    break;
                case 4:
                    relevanciaPag(controleBusca);
                    break;
                case 3: 
                    controleBusca.ordenar();break;
                default:
                    break;
            }
            System.out.println("Digite 0 para sair");
            System.out.println("Pressione qualquer outro botão para continuar");
            sair = input();
        } while (!sair.equalsIgnoreCase("0"));
    }

    public static int escolha() {

        String escolha;
        System.out.println("Digite:");
        System.out.println("1 - Para realizar pesquisa");
        System.out.println("2 - Para acessar páginas");
        System.out.println("3 - Para ver palavras mais ou menos buscas");
        System.out.println("4 - Para ver páginas mais ou menos visitadas");
        escolha = input();
        switch (escolha) {
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            default:
                return -1;
        }

    }

    public static void relevanciaPag(ControllerBusca controleBusca) throws FileNotFoundException, Exception {
        controleBusca.getControlPages().atualize();
        System.out.println("Digite 1 para ordenar relevancia por ordem crescente e 2 para decrescente");
        String escolha = input();
        System.out.println("Digite o top K dejesado");
        String topK = input();
        int k;
        try {
            k = Integer.parseInt(topK);
        } catch (NumberFormatException ex) {
            k = 0;
        }
        //O algoritmo de ordenação usa uma fila, com isso a ordem é invertida.
        if (escolha.equals("1")) {
            LinkedList cresc = null;
            try {
                cresc = controleBusca.getControlPages().sort(controleBusca.getControlPages().getPaginas(), new Crescente());
            } catch (FileNotFoundException exe) {
                System.out.println("Paginas não encontradas");
            }
            if (cresc != null) {
                Iterator temp = cresc.iterator();
                for (int i = 0; i < k && temp.hasNext(); i++) {
                    System.out.println(((Pagina) temp.next()).toString());
                }
            }

        } //O algoritmo de ordenação usa uma fila, com isso a ordem é invertida.
        else if (escolha.equals("2")) {
            LinkedList decres = null;
            try {
                decres = controleBusca.getControlPages().sort(controleBusca.getControlPages().getPaginas(), new Decrescente());
            } catch (FileNotFoundException exe) {
                System.out.println("Paginas não encontradas");
            }
            if (decres != null) {
                Iterator temp = decres.iterator();
                for (int i = 0; i < k && temp.hasNext(); i++) {
                    System.out.println(((Pagina) temp.next()).toString());
                }
            }
        }

    }

    public static void visitPage(ControllerBusca controleBusca) throws FileNotFoundException, Exception {
        System.out.println("Digite o nome da página com a extenção \".txt\":");
        String pagina = input();
        int i = 0;
        int index = 0;
        for (Object pagina1 : controleBusca.getControlPages().getPaginas()) {
            if (pagina.equals(((Pagina) pagina1).getNome())) {
                index = i;
                break;
            }
            i++;

        }
        controleBusca.getControlPages().showFile(index);
        System.out.println(":(");
    }

    public static void pesquisa(ControllerBusca controleBusca) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String palavraProcurada;

        System.out.println(":)");
        // Depois eu crio uma classe para cuidar disso mano

        System.out.println("Digite uma palavra a ser procurada");
        palavraProcurada = scanner.next();
        palavraProcurada = palavraProcurada.toUpperCase();
        String[] palavrasMultiplas = palavraProcurada.split(" ");
        Palavra a = null;
        Palavra p = (Palavra) controleBusca.search(palavrasMultiplas[0]);
        if (p != null) {
            System.out.println(p.toString());
            Iterator it = p.imprimirArquivos();
            while (it.hasNext()) {
                Pagina pag = (Pagina) it.next();
                System.out.println(pag.getNome() + pag.getRelevancia());

            }
        } else if (controleBusca.addPalavra(palavrasMultiplas[0]) == true) {
            p = (Palavra) controleBusca.search(palavrasMultiplas[0]);
            if (p != null) {
                LinkedList temp = controleBusca.getControlPages().sort(p.getPaginas(), p);
                System.out.println(p.toString());
                Iterator it = temp.iterator();
                while (it.hasNext()) {
                    Pagina pag = (Pagina) it.next();
                    System.out.println("Página: "+pag.getNome()+" Relevância: " + pag.getRelevancia());

                }
            }
        } else {
            System.out.println("Palavra não encontrada");
        }
        System.out.println("Digite 2 para acessar uma página");
        String visitPag = input();
        if(visitPag.equals("2"))
            visitPage(controleBusca);
    }

    private static String input() {                //Para resumir as entradas do teclado;
        Scanner opcao = new Scanner(System.in);
        return opcao.nextLine();
    }
}

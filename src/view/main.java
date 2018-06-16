/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerFile;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import model.Palavra;
import util.Arvore;
import util.No;

/**
 *
 * @author Matheus Nascimento
 */
public class main {
    
    public static void main(String[] args) throws IOException{
        ControllerFile c = new ControllerFile();
        Scanner scanner = new Scanner(System.in);
        String palavraProcurada;
        Arvore a = c.readFiles();
        
        System.out.println(":)");
        // Depois eu crio uma classe para cuidar disso mano
        Arvore b = new Arvore();
        while(true){
            System.out.println("Digite uma palavra a ser procurada");
            palavraProcurada = scanner.next();

            if (b.getRaiz() == null){

                Comparable inserirNaB = a.search(palavraProcurada);

                if (inserirNaB == null){

                    System.out.println("Elemento não está presente na árvore principal");
                }
                else{
                    b.inserir(inserirNaB);
                    System.out.println("Palavra achada na principal");
                }
            }
            else{

                Comparable procurar = b.search(palavraProcurada);

                if (procurar == null){

                    procurar = a.search(palavraProcurada);

                    if (procurar == null){

                        System.out.println("Elemento não está em nenhuma das árvore");
                    }
                    else{

                        a.inserir(procurar);
                        System.out.println("Achada na arvore principal");
                    }
                }
                else{

                    System.out.println(" Achada na arvore de pesquisa");
                }
            }
        }
    }
}

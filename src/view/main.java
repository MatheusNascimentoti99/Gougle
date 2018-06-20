/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerBusca;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import model.Pagina;
import model.Palavra;


/**
 *
 * @author Matheus Nascimento
 */
public class main {
    
    public static void main(String[] args) throws IOException, Exception{
        ControllerBusca c = new ControllerBusca();
        Scanner scanner = new Scanner(System.in);
        String palavraProcurada;
        
        System.out.println(":)");
        // Depois eu crio uma classe para cuidar disso mano
        
        while(true){
            System.out.println("Digite uma palavra a ser procurada");
            palavraProcurada = scanner.next();
            palavraProcurada = palavraProcurada.toUpperCase();
            String[] palavrasMultiplas = palavraProcurada.split(" ");
            Palavra a = null;
            for ( String palavraMultipla: palavrasMultiplas){
                Palavra p = (Palavra) c.search(palavraMultipla);
                if(p != null){
                    System.out.println(p.toString());
                    Iterator it = p.imprimirArquivos();
                    while(it.hasNext()){
                        Pagina pag = (Pagina) it.next();
                        System.out.println(pag.getNome());
                    }
                    System.out.println("Palavra encontrada na arvore");

                }
                else if(c.addPalavra(palavraMultipla) == true){
                    System.out.println("Palavra encontrada entre os arquivos");
                }
                else{
                    System.out.println("Palavra não encontrada");
                }
                a = p;
            }            
        }
    }
}

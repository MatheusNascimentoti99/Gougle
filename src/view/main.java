/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerBusca;
import java.io.IOException;
import java.util.Scanner;
import model.Palavra;


/**
 *
 * @author Matheus Nascimento
 */
public class main {
    
    public static void main(String[] args) throws IOException{
        ControllerBusca c = new ControllerBusca();
        Scanner scanner = new Scanner(System.in);
        String palavraProcurada;
        
        System.out.println(":)");
        // Depois eu crio uma classe para cuidar disso mano
        
        while(true){
            System.out.println("Digite uma palavra a ser procurada");
            palavraProcurada = scanner.next();
            palavraProcurada = palavraProcurada.toUpperCase();
            Palavra p = (Palavra) c.getBuscaRapida().search(palavraProcurada);
            if(c.getBuscaRapida().search(palavraProcurada) != null){
                p.sort();
                System.out.println(p.toString());
                System.out.println("Palavra encontrada na arvore");
                
            }
            else if(c.addPalavra(palavraProcurada) == true){
                System.out.println("Palavra encontrada entre os arquivos");
            }
            else{
                System.out.println("Palavra não encontrada");
            }
            
        }
    }
}

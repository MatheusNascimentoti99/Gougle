/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerBusca;
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
        ControllerBusca c = new ControllerBusca();
        Scanner scanner = new Scanner(System.in);
        String palavraProcurada;
        
        System.out.println(":)");
        // Depois eu crio uma classe para cuidar disso mano
        
        while(true){
            System.out.println("Digite uma palavra a ser procurada");
            palavraProcurada = scanner.next();

            if(c.getBuscaRapida().search(palavraProcurada) != null){
                System.out.println("Palavra achada na arvore");
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

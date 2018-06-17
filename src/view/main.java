/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerBusca;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import model.Palavra;


/**
 *
 * @author Matheus Nascimento
 */
public class main {
    
    public static void main(String[] args) throws IOException{

        
      File arquivoAserLido = new File("C:\\Users\\Usuário 01\\Documents\\PBL 3 - Google Search\\Gougle\\testeEditar\\teste.txt");
      
      System.out.println("Ultima alteração no arquivo: "+arquivoAserLido.lastModified());
        
      long dataModif = arquivoAserLido.lastModified();
        System.out.println("Nome do arquivo: "+ arquivoAserLido.getName());
//      
//      FileWriter arq = new FileWriter("C:\\Users\\Usuário 01\\Documents\\PBL 3 - Google Search\\Gougle\\testeEditar\\Salvar.txt");
//      PrintWriter gravarArq = new PrintWriter(arq);
//      gravarArq.printf(""+dataModif+":"+arquivoAserLido.getName());
//      arq.close();
    
      FileReader arquivo;
      arquivo = new FileReader("C:\\Users\\Usuário 01\\Documents\\PBL 3 - Google Search\\Gougle\\testeEditar\\Salvar.txt");
     BufferedReader lerArq = new BufferedReader(arquivo);
      String linha = lerArq.readLine(); // só leio a primeira linha pq só testei pra um mano 
      
      String[] ultModif = linha.split(":");
        
      System.out.println("Ultima alteração registrada no arquivo: "+ ultModif[0]);
        
        Long verifica = Long.parseLong(ultModif[0]);
        
        if (verifica == dataModif){
            
            System.out.println("Sem alteração no arquivo: "+ultModif[1]);
            arquivo.close();
            
        }
        else{
            // Como houve alteração, ele escreve a nova data e o nome do arquivo
            // No arquivo que vai salvar essas modificações dos arquivos
            System.out.println("Houve alteração, leia novamente o arquivo: "+ ultModif[1]);
            FileWriter arquivo2 = new FileWriter("C:\\Users\\Usuário 01\\Documents\\PBL 3 - Google Search\\Gougle\\testeEditar\\Salvar.txt");
            PrintWriter gravarArq = new PrintWriter(arquivo2);
            gravarArq.printf(""+dataModif+":"+arquivoAserLido.getName());
            arquivo2.close();
        }
        
    }
    
}


//
//        ControllerBusca c = new ControllerBusca();
//        Scanner scanner = new Scanner(System.in);
//        String palavraProcurada;
//        
//        System.out.println(":)");
//         Depois eu crio uma classe para cuidar disso mano
//        
//        while(true){
//            System.out.println("Digite uma palavra a ser procurada");
//            palavraProcurada = scanner.next();
//            palavraProcurada = palavraProcurada.toUpperCase();
//            
//            String[] palavrasMultiplas = new String[10];
//            palavrasMultiplas = palavraProcurada.split(" ");
//            
//            for ( String o: palavrasMultiplas){
//                System.out.println(""+ o);
//            }
//            for ( String palavraMultipla: palavrasMultiplas){
//                Palavra p = (Palavra) c.getBuscaRapida().search(palavraMultipla);
//                if(c.getBuscaRapida().search(palavraMultipla) != null){
//                    System.out.println(p.toString());
//                    System.out.println("Palavra encontrada na arvore");
//
//                }
//                else if(c.addPalavra(palavraMultipla) == true){
//                    System.out.println("Palavra encontrada entre os arquivos");
//                }
//                else{
//                    System.out.println("Palavra não encontrada");
//                }
//            }
//        }
//        
//       
//    }
//}

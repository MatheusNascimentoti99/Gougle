/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.Pagina;
import model.Palavra;
import util.Arvore;

/**
 *
 * @author Matheus Nascimento
 */
public class ControllerBuscaFile {

   public boolean changePagina(long change, Pagina pagina){
       return change != pagina.getChange();            
   }
    
    
    public boolean readFiles(String palavraBuscada, Arvore arvore) throws IOException {
        boolean existe = false;
        
        FileFilter filter = (File pathname) -> pathname.getName().endsWith(".txt");
        File dir = new File("repositorio");
        File[] files = dir.listFiles(filter);
        for (File file : files) {
            if(readFile(file, palavraBuscada, arvore))
                existe = true;
        }
        return existe;
    }

    private boolean readFile(File file, String palavraBuscada, Arvore arvore) throws FileNotFoundException, IOException {
        FileReader arq = new FileReader("repositorio\\" + file.getName());
        BufferedReader read = new BufferedReader(arq);
        String linha = "";
        Pagina pagina = new Pagina(file.getName(), file.lastModified());
        boolean existe = false;
        while (linha != null) {
            linha = read.readLine();
            if (linha != null) {
                linha = linha.toUpperCase();
                if(pegarPalavra(linha, pagina, palavraBuscada, arvore))
                    existe = true;
            }
        }
        return existe;
    }

    private boolean pegarPalavra(String linha, Pagina pagina, String palavraBuscada, Arvore arvore) {
        String[] textoSeparado;
        boolean existe = false;
        textoSeparado = linha.split(" ");
        for (String palavra : textoSeparado) {
            palavra = palavra.replace(" ", "");
            palavra = palavra.replace(".", "");
            palavra = palavra.replace("/", "");
            palavra = palavra.replace("-", "");
            palavra = palavra.replace("\\", "");
            palavra = palavra.replace("(", "");
            palavra = palavra.replace(")", "");
            palavra = palavra.replace(",", "");
            palavra = palavra.replace("{", "");
            palavra = palavra.replace("}", "");
            palavra = palavra.replace("[", "");
            palavra = palavra.replace("]", "");
            palavra = palavra.replace("!", "");
            palavra = palavra.replace("?", "");         
            Palavra nova = new Palavra(palavra, pagina);
            palavraBuscada = palavraBuscada.toUpperCase();
            if(palavraBuscada.compareTo(palavra) == 0){
                arvore.inserir(nova);
                existe = true;
            }   
        }
        return existe;
    }
}

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
import model.Palavra;
import util.Arvore;

/**
 *
 * @author Matheus Nascimento
 */
public class ControllerFile {

    private Arvore arvore;

    public Arvore readFiles() throws IOException {
        arvore = new Arvore();
        FileFilter filter = (File pathname) -> pathname.getName().endsWith(".txt");
        File dir = new File("repositorio");
        File[] files = dir.listFiles(filter);
        for (File file : files) {
            readFile(file);
        }
        return arvore;
    }

    private void readFile(File file) throws FileNotFoundException, IOException {
        FileReader arq = new FileReader("repositorio\\" + file.getName());
        BufferedReader read = new BufferedReader(arq);
        String linha = "";

        while (linha != null) {
            linha = read.readLine();
            if (linha != null) {
                linha = linha.toUpperCase();
                pegarPalavra(linha);
            }
            
            System.out.printf("%s\n", linha);

        }
    }

    private void pegarPalavra(String linha) {
        String[] textoSeparado;
        textoSeparado = linha.split(" ");
        for (String p : textoSeparado) {
            p = p.replace(" ", "");
            p = p.replace(".", "");
            p = p.replace("/", "");
            p = p.replace("-", "");
            p = p.replace("\\", "");
            p = p.replace("(", "");
            p = p.replace(")", "");
            p = p.replace(",", "");
            p = p.replace("{", "");
            p = p.replace("}", "");
            p = p.replace("[", "");
            p = p.replace("]", "");
            p = p.replace("!", "");
            p = p.replace("?", "");
            Palavra nova = new Palavra(p);
            arvore.inserir(nova);
        }

    }
}

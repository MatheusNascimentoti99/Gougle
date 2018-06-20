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
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Pagina;
import model.Palavra;
import util.Arvore;
import util.QuickSort;
import view.Interface;

/**
 *
 * @author Matheus Nascimento
 */
public class ControllerPaginas {

    private final ControllerFile save;
    public final String pastaRecursos = "resources\\";
    public final String repositorio = "repositorio\\";
    private Arvore allPages;

    public ControllerPaginas() {
        save = new ControllerFile();
        allPages = new Arvore();
    }

    void saveTreePage() throws Exception {
        save.save(allPages, pastaRecursos + "TreePages.date");
    }

    public Arvore readTree() throws FileNotFoundException {
        Arvore temp;
        try {
            temp = (Arvore) save.readDate("resources\\TreePages.date");
        } catch (FileNotFoundException e) {
            temp = null;
        }
        if (temp == null) {
            return new Arvore();
        }
        return temp;
    }

    public void readFiles() {
        LinkedList paginas = getFiles();
        paginas.forEach((obj) -> {
            allPages.inserir((Comparable) obj);
        });
    }

    public LinkedList getFiles() {

        FileFilter filter = (File pathname) -> pathname.getName().endsWith(".txt");
        File dir = new File("repositorio");
        File[] files = dir.listFiles(filter);
        LinkedList temp = new LinkedList();
        temp.addAll(Arrays.asList(files));
        return temp;
    }

    public void saveFiles() throws Exception {
        save.save(getFiles(), pastaRecursos + "Files.date");
    }

    public void showFile(Pagina pagina) throws FileNotFoundException {
        if (allPages.getRaiz() == null) {
            allPages = this.readTree();
        }
        try {
            java.awt.Desktop.getDesktop().open(new File(repositorio + pagina.getNome()));
            pagina.visited();
        } catch (IOException | NullPointerException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean readFilesWords(String palavraBuscada, Arvore arvore) throws IOException {
        boolean existe = false;

        FileFilter filter = (File pathname) -> pathname.getName().endsWith(".txt");
        File dir = new File("repositorio");
        File[] files = dir.listFiles(filter);
        for (File file : files) {
            if (readFileWord(file, palavraBuscada, arvore)) {
                existe = true;
            }
        }
        return existe;
    }

    public boolean readFileWord(File file, String palavraBuscada, Arvore arvore) throws FileNotFoundException, IOException {
        FileReader arq = new FileReader(repositorio + file.getName());
        BufferedReader read = new BufferedReader(arq);
        String linha = "";
        Pagina pagina = new Pagina(file.getName(), file.lastModified());
        boolean existe = false;
        while (linha != null) {
            linha = read.readLine();
            if (linha != null) {
                linha = linha.toUpperCase();
                if (getFileWord(linha, pagina, palavraBuscada, arvore)) {
                    existe = true;
                }
            }
        }
        return existe;
    }

    private boolean getFileWord(String linha, Pagina pagina, String palavraBuscada, Arvore arvore) {
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
            pagina.setPalavra(nova);
            palavraBuscada = palavraBuscada.toUpperCase();
            if (palavraBuscada.compareTo(palavra) == 0) {
                arvore.inserir(nova);
                existe = true;
            }
        }
        return existe;
    }

    private Queue passListToQueue(LinkedList paginas, Queue fila) {
        while (!paginas.isEmpty()) {
            fila.add((Pagina) paginas.remove());
        }
        return fila;
    }

    private LinkedList passQueueToList(Queue fila, LinkedList paginas) {
        while (!fila.isEmpty()) {
            paginas.add(fila.peek());
            fila.remove();
        }
        return paginas;
    }

    public LinkedList sort(LinkedList paginas, Comparator ordem) {
        Queue fila = new LinkedList();
        fila = passListToQueue(paginas, fila);
        QuickSort sort = new QuickSort();
        sort.quickSort(fila, ordem);
        return passQueueToList(fila, paginas);
    }

}

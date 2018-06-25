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
import model.Pagina;
import model.Palavra;
import util.Arvore;
import util.QuickSort;

/**
 * A classe <b>ControllerPaginas</b> faz o gerenciamento das Páginas.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 */
public class ControllerPaginas {

    private final ControllerFile save;

    /**
     *
     */
    public final String pastaRecursos = "resources\\";

    /**
     *
     */
    public final String repositorio = "repositorio\\";
    private LinkedList allPages;

    /**
     *
     */
    public ControllerPaginas() {
        save = new ControllerFile();
        allPages = new LinkedList();
    }

    void saveListPage() throws Exception {
        getPaginas();
        save.save(allPages, pastaRecursos + "ListPages.date");
    }
    
    /**
     *
     * @return
     * @throws FileNotFoundException
     */
    public LinkedList readListPages() throws FileNotFoundException {
        LinkedList temp;
        try {
            temp = (LinkedList) save.readDate("resources\\ListPages.date");
        } catch (FileNotFoundException e) {
            temp = null;
        }
        if (temp == null) {
            return new LinkedList();
        }
        return temp;
    }

    /**
     *
     * @return
     */
    public LinkedList getFiles() {
        FileFilter filter = (File pathname) -> pathname.getName().endsWith(".txt");
        File dir = new File("repositorio");
        File[] files = dir.listFiles(filter);
        LinkedList temp = new LinkedList();
        temp.addAll(Arrays.asList(files));
        return temp;
    }

    /**
     *
     * @return
     * @throws FileNotFoundException
     */
    public LinkedList getPaginas() throws FileNotFoundException{
        for(Object aux: getFiles()){
            File file = (File) aux;
            Pagina temp = new Pagina(file.getName(), file.lastModified());
            if(!allPages.contains(temp))
                allPages.add(temp);
        }
        return allPages;
    }

    /**
     *
     * @return
     * @throws FileNotFoundException
     */
    public LinkedList readListFiles() throws FileNotFoundException{
        LinkedList lista = (LinkedList) save.readDate(pastaRecursos + "Files.date");
        return lista != null ? lista : null;
    }
    void saveListFiles() throws Exception {
        File file = new File(pastaRecursos + "Files.date");
        if(!file.exists())
            save.save(getFiles(), pastaRecursos + "Files.date");
    }

    /**
     *
     * @param paginas
     * @return
     * @throws FileNotFoundException
     */
    public int modificedFiles(LinkedList paginas) throws FileNotFoundException{
        LinkedList oldFiles = readListFiles();
        LinkedList recentFiles = getFiles();
        if(oldFiles == null)
            return 0;
        else if(oldFiles.size() != recentFiles.size())
            return 1;
    
        for(Object temp : paginas){
            Pagina aux = (Pagina) temp;
            File file = new File(aux.getNome());
            if(!file.exists())
                return 1;
        }
        return 0;
    }
    
    /**
     *
     * @param index
     * @throws FileNotFoundException
     * @throws Exception
     */
    public void showFile(int index) throws FileNotFoundException, Exception {
        File file = new File("resources\\ListPages.date");
        if(!file.exists())
            saveListPage();
        allPages = this.readListPages();  
        try {
            java.awt.Desktop.getDesktop().open(new File(repositorio + ((Pagina)allPages.get(index)).getNome()));
            ((Pagina)allPages.get(index)).visited();
        } catch (IOException | NullPointerException ex) {
            
        }
        saveListPage();
       

    }

    /**
     *
     * @throws Exception
     */
    public void atualize() throws Exception{
        File file = new File("resources\\ListPages.date");
        if(!file.exists())
            saveListPage();
        allPages = this.readListPages();  
        saveListPage();
    }

    /**
     *
     * @param palavraBuscada
     * @param arvore
     * @return
     * @throws IOException
     */
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

    /**
     *
     * @param file
     * @param palavraBuscada
     * @param arvore
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
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

    /**
     *
     * @param paginas
     * @param ordem
     * @return
     */
    public LinkedList sort(LinkedList paginas, Comparator ordem) {
        Queue fila = new LinkedList();
        fila = passListToQueue(paginas, fila);
        QuickSort sort = new QuickSort();
        sort.quickSort(fila, ordem);
        return passQueueToList(fila, paginas);
    }

}

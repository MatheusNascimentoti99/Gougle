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
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;
import model.Pagina;
import model.Palavra;
import util.Arvore;
import util.QuickSort;

/**
 * A classe <b>ControllerPaginas</b> faz o gerenciamento das P�ginas.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 */
public class ControllerPaginas {


    /**
     * Constante que informa o diretorio que ser� salvo os recursos utilizados
     * no projeto.
     */
    public final String pastaRecursos = "./resources/";

    /**
     * Constante que informa o diretorio que ser� capturado as p�ginas que
     * poder�o ser pesquisadas.
     */
    public final String repositorio = "./repositorio/";
    private LinkedList allPages;

    /**
     *
     */
    public ControllerPaginas() {
        allPages = new LinkedList();
    }

    void saveListPage() throws Exception {
        getPaginas();
        ControllerFile.save(allPages, pastaRecursos + "ListPages.data");
    }

    /**
     * Pega a lista desatualizada de arquivos.
     *
     * @return Retorna a "antiga" lista de p�ginas.
     * @throws FileNotFoundException Exce��o caso o arquivo n�o exista.
     */
    public LinkedList readListPages() throws FileNotFoundException {
        LinkedList temp;
        try {
            temp = (LinkedList) ControllerFile.readDate("./resources/ListPages.data");
        } catch (FileNotFoundException e) {
            temp = null;
        }
        if (temp == null) {
            return new LinkedList();
        }
        return temp;
    }

    /**
     * Pega a atual lista de arquivos no reposit�rio.
     *
     * @return Retorna a lista de arquivos no reposit�rio.
     */
    public LinkedList getFiles() {
        FileFilter filter = (File pathname) -> pathname.getName().endsWith(".txt");
        File dir = new File("./repositorio");
        File[] files = dir.listFiles(filter);
        LinkedList temp = new LinkedList();
        if(files != null){
        temp.addAll(Arrays.asList(files));
        }
        return temp;
        
    }

    /**
     * M�todo para pegar lista de p�ginas.
     *
     * @return Retorna a lista com todas as p�ginas instanciadas.
     * @throws FileNotFoundException Exce��o se n�o houver arquivos no
     * reposit�rio.
     */
    public LinkedList getPaginas() throws FileNotFoundException {

        for (Object aux : getFiles()) {
            File file = (File) aux;
            Pagina temp = new Pagina(file.getName(), file.lastModified());

            if (!allPages.contains(temp)) {
                allPages.add(temp);
            }
        }
        return allPages;
    }

    /**
     * Ler e retorna a lista de arquivos gravada em um arquivo bin�rio.
     *
     * @return Retorna uma lista de arquivos.
     * @throws FileNotFoundException Caso o arquivo n�o exista.
     */
    public LinkedList readListFiles() throws FileNotFoundException {
        LinkedList lista = (LinkedList) ControllerFile.readDate(pastaRecursos + "Files.data");
        return lista != null ? lista : null;
    }

    void saveListFiles() throws Exception {
        File file = new File(pastaRecursos + "Files.data");
        if (!file.exists()) {
            ControllerFile.save(getFiles(), pastaRecursos + "Files.data");
        }
    }

    /**
     *
     * @param paginas Paginas a serem verificadas se foram removidas ou n�o.
     * @return Retorna um valor booleando informando se as paginas foram
     * alteradas ou n�o.
     * @throws FileNotFoundException
     */
    public boolean modificedFiles(LinkedList paginas) throws FileNotFoundException {
        LinkedList recentFiles = getFiles();
        for (Object temp : paginas) {
            Pagina aux = (Pagina) temp;
            File file = new File(aux.getNome());
            if (!file.exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * M�todo utilizado para abrir p�gina.
     *
     * @param index Indice no qual a p�gina que ser� exebida est�.
     * @throws FileNotFoundException Caso a lista de paginas n�o exista.
     * @throws Exception Outras exce��o, como problemas ao salvar o arquivo ou a
     * index acessar um local da lista que n�o exista.
     */
    public void showFile(int index) throws FileNotFoundException, Exception {
        File file = new File("./resources/ListPages.data");
        if (!file.exists()) {
            saveListPage();
        }
        allPages = this.readListPages();
        try {
            java.awt.Desktop.getDesktop().open(new File(repositorio + ((Pagina) allPages.get(index)).getNome()));
            ((Pagina) allPages.get(index)).visited();
        } catch (IOException | NullPointerException ex) {

        }
        saveListPage();

    }

    /**
     * M�todo utilizado para atualizar as informa��es da lista de p�ginas.
     *
     * @throws Exception Caso a lista de paginas n�o exista ou haja problema ao
     * instanciar o File.
     */
    public void atualize() throws Exception {
        File file = new File("./resources/ListPages.data");
        if (!file.exists()) {
            saveListPage();
        }
        allPages = this.readListPages();
        saveListPage();
    }

    /**
     * M�todo utilizado para busca uma palavra entre os arquivos.
     *
     * @param palavraBuscada Par�metro utilizado para informar qual a palavra
     * que est� sendo buscada nos arquivos.
     * @param arvore Arvore a qual a palavra encontrada ser� salva.
     * @return Retorna um valor booleano informando se a palavra existe ou n�o.
     * @throws IOException Possiveis exce��es na grava��o e leitura de arquivos.
     */
    public boolean readFilesWords(String palavraBuscada, Arvore arvore) throws IOException {
        boolean existe = false;

        FileFilter filter = (File pathname) -> pathname.getName().endsWith(".txt");
        File dir = new File("./repositorio");
        File[] files = dir.listFiles(filter);
        for (File file : files) {
            if (readFileWord(file, palavraBuscada, arvore)) {
                existe = true;
            }
        }
        return existe;
    }

    public static String semAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    /**
     * M�todo utilizado para busca uma palavra em um �nico arquivo.
     *
     * @param file Arquivo a qual a palavra ser� buscada.
     * @param palavraBuscada Par�metro utilizado para informar qual a palavra
     * que est� sendo buscada nos arquivos.
     * @param arvore Arvore a qual a palavra encontrada ser� salva.
     * @return Retorna um valor booleano informando se a palavra existe ou n�o.
     * @throws java.io.FileNotFoundException Caso a arquivo n�o exista.
     * @throws IOException Possiveis exce��es na grava��o e leitura de arquivos.
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


    //M�todo que ler linha por linha de um arquivo.
    private boolean getFileWord(String linha, Pagina pagina, String palavraBuscada, Arvore arvore) {
        String[] textoSeparado;
        boolean existe = false;
        textoSeparado = linha.split(" ");
        for (String palavra : textoSeparado) {              //Remove as posiveis pontua��o que podem atrapalhar na identifica��o de uma palavra.
            palavra = semAcento(palavra);
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
            palavra = palavra.replace("#", "");
            palavra = palavra.replace("\"", "");
            palavra = palavra.replace("\'", "");
            Palavra nova = new Palavra(palavra, pagina);
            palavraBuscada = palavraBuscada.toUpperCase();
            if (palavraBuscada.compareTo(palavra) == 0) {
                arvore.inserir(nova);
                existe = true;
            }
        }
        return existe;
    }

    /*
    M�todos passListToQueue e passQueueToList s�o utilizados para manipula��o e ordena��o das paginas.
    
     */
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
     * M�todo para ordena��o de p�ginas.
     *
     * @param paginas Lista de p�ginas a serem ordenadas.
     * @param ordem Crit�rio a ser utilizado para ordena��o.
     * @return Retorna uma lista com o conte�do ordenado.
     */
    public LinkedList sort(LinkedList paginas, Comparator ordem) {
        Queue fila = new LinkedList();
        fila = passListToQueue(paginas, fila);
        QuickSort sort = new QuickSort();
        sort.quickSort(fila, ordem);
        return passQueueToList(fila, paginas);
    }

}

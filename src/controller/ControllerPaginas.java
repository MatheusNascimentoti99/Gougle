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
 * A classe <b>ControllerPaginas</b> faz o gerenciamento das Páginas.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 */
public class ControllerPaginas {


    /**
     * Constante que informa o diretorio que será salvo os recursos utilizados
     * no projeto.
     */
    public final String pastaRecursos = "./resources/";

    /**
     * Constante que informa o diretorio que será capturado as páginas que
     * poderão ser pesquisadas.
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
     * @return Retorna a "antiga" lista de páginas.
     * @throws FileNotFoundException Exceção caso o arquivo não exista.
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
     * Pega a atual lista de arquivos no repositório.
     *
     * @return Retorna a lista de arquivos no repositório.
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
     * Método para pegar lista de páginas.
     *
     * @return Retorna a lista com todas as páginas instanciadas.
     * @throws FileNotFoundException Exceção se não houver arquivos no
     * repositórioo.
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
     * Ler e retorna a lista de arquivos gravada em um arquivo binário.
     *
     * @return Retorna uma lista de arquivos.
     * @throws FileNotFoundException Caso o arquivo não exista.
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
     * @param paginas Paginas a serem verificadas se foram removidas ou não.
     * @return Retorna um valor booleando informando se as paginas foram
     * alteradas ou não.
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
     * Método utilizado para abrir página.
     *
     * @param index Indice no qual a página que será exebida está.
     * @throws FileNotFoundException Caso a lista de paginas não exista.
     * @throws Exception Outras exceção, como problemas ao salvar o arquivo ou a
     * index acessar um local da lista que não exista.
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
     * Método utilizado para atualizar as informações da lista de páginas.
     *
     * @throws Exception Caso a lista de paginas não exista ou haja problema ao
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
     * Método utilizado para busca uma palavra entre os arquivos.
     *
     * @param palavraBuscada Parâmetro utilizado para informar qual a palavra
     * que está sendo buscada nos arquivos.
     * @param arvore Arvore a qual a palavra encontrada será salva.
     * @return Retorna um valor booleano informando se a palavra existe ou não.
     * @throws IOException Possiveis exceções na gravação e leitura de arquivos.
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
     * Método utilizado para busca uma palavra em um único arquivo.
     *
     * @param file Arquivo a qual a palavra será buscada.
     * @param palavraBuscada Parâmetro utilizado para informar qual a palavra
     * que está sendo buscada nos arquivos.
     * @param arvore Arvore a qual a palavra encontrada será salva.
     * @return Retorna um valor booleano informando se a palavra existe ou não.
     * @throws java.io.FileNotFoundException Caso a arquivo não exista.
     * @throws IOException Possiveis exceções na gravação e leitura de arquivos.
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


    //Método que ler linha por linha de um arquivo.
    private boolean getFileWord(String linha, Pagina pagina, String palavraBuscada, Arvore arvore) {
        String[] textoSeparado;
        boolean existe = false;
        textoSeparado = linha.split(" ");
        for (String palavra : textoSeparado) {              //Remove as posiveis pontuação que podem atrapalhar na identificação de uma palavra.
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
    Métodos passListToQueue e passQueueToList são utilizados para manipulação e ordenação das paginas.
    
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

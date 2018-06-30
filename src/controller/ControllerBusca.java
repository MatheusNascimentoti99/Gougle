/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Pagina;
import model.Palavra;
import util.Arvore;
import util.Crescente;
import util.No;
import util.QuickSort;

/**
 * A classe <b>ControllerBusca</b> faz o gerenciamento da arvore de busca.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 */
public class ControllerBusca implements Comparator, Runnable {

    private ControllerPaginas allFiles;
    private Arvore buscaRapida;
    private File file;
    private final QuickSort sort;
    private final Crescente comparador;



    /**
     *
     */
    public ControllerBusca() {
        allFiles = new ControllerPaginas();
        buscaRapida = new Arvore();
        sort = new QuickSort();
        comparador = new Crescente();

    }

    /**
     *
     * @param linhaPesquisa Entreda do usuário
     * @return retorna uma lista com o resultado de todas as buscas de palavras.
     * @throws Exception
     */
    public LinkedList foundPages(String linhaPesquisa) throws Exception {
        String[] textoSeparado;
        LinkedList paginas = new LinkedList();
        boolean existe = false;
        Palavra p;
        textoSeparado = linhaPesquisa.split(" ");
        for (String palavra : textoSeparado) {              //Remove as posiveis pontuação que podem atrapalhar na identificação de uma palavra.
            palavra = palavra.toUpperCase();
            palavra = ControllerPaginas.semAcento(palavra);
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
            p = (Palavra) search(palavra);

            if (p == null) {
                if (addPalavra(palavra) == true) {
                    p = (Palavra) search(palavra);
                }
            }
            if (p != null) {

                for (int i = 0; i < p.getPaginas().size(); i++) {
                    if (paginas.contains(p.getPaginas().get(i))) {                  //Pega a relevância de todas as páginas que as palavras pertencem.
                        int index = paginas.indexOf(p.getPaginas().get(i));
                        Pagina pag = (Pagina) paginas.get(index);
                        paginas.remove(index);
                        pag.setRelevancia(pag.getRelevancia() + ((Pagina) p.getPaginas().get(i)).getRelevancia());
                        paginas.add(pag);
                    } else {
                        paginas.add(p.getPaginas().get(i));
                    }
                }
            }
        }
        saveTree();
        return paginas;
    }

    private boolean modificedFiles() throws FileNotFoundException, Exception {
        File pasta = new File("resources\\ModificedPasta.data");
        Pagina pastaOld;
        boolean change = false;
        if (!pasta.exists()) {
            savePasta();
        } else {
            pastaOld = readPasta();
            if (pastaOld != null) {
                File pastaRecent = new File("repositorio");
                if (pastaOld.getChange() != pastaRecent.lastModified()) {
                    savePasta();
                    change = true;

                }
            }
        }
        return change;
    }

    public void atualizarArvore() throws Exception {
        Queue palavras = filaPalavras();
        boolean allChange = false;
        if (modificedFiles()) {                 //Se for modificado, então é reiniciado a árvore com a nova leitura das palavras que estavam dentro dela.
            buscaRapida = new Arvore();
            if (palavras != null) {
                for (Object temp : palavras) {
                    if (this.addPalavra(((Palavra) temp).getPalavra())) {
                        Palavra p = (Palavra) search(buscaRapida.getRaiz(), ((Palavra) temp).getPalavra());
                        p.setSearch(((Palavra) temp).getSearch());
                        buscaRapida.remover(p);
                        buscaRapida.inserir(p);
                    }
                }
            }
            allChange = true;
        }
        if (!allChange) {                       //Se o ditetório não foi alterado, então ira verificar a integridade de cada página                     
            if (palavras != null) {
                for (Object temp : palavras) {
                    for (int i = 0; i < ((Palavra) temp).getPaginas().size(); i++) {
                        if (changePagina((Pagina) ((Palavra) temp).getPaginas().get(i)) != 0) {
                            buscaRapida.remover((Palavra) temp);
                            if (this.addPalavra(((Palavra) temp).getPalavra())) {
                                Palavra p = (Palavra) search(buscaRapida.getRaiz(), ((Palavra) temp).getPalavra());
                                p.setSearch(((Palavra) temp).getSearch());
                                buscaRapida.remover(p);
                                buscaRapida.inserir(p);
                            }
                        }
                    }
                }
            }
        }
        saveTree();
        atualizar();
    }

    private int changePagina(Pagina pagina) {                   //Método responsavel por verificar se o arquivo que a palavra pertence foi editada. 
        LinkedList paginas = allFiles.getFiles();
        Iterator it = paginas.iterator();
        File pag = new File("repositorio\\" + pagina.getNome());
        if (!pag.exists()) {                                    //Verifica se a página ainda existe.
            return -1;
        }
        while (it.hasNext()) {                                  //Perroce  a lista de páginas verificando se há diferenças.
            File arq = (File) it.next();
            if (arq.lastModified() == pagina.getChange()) {       //Se não tiver alteração, então retorna 0.
                return 0;
            }
        }
        return -1;                                              //Se não existir, então retorna -1.
    }

    private Pagina readPasta() throws FileNotFoundException {
        return (Pagina) ControllerFile.readDate("resources\\ModificedPasta.data");
    }

    private void savePasta() throws Exception {
        File arq = new File("repositorio");
        Pagina pasta = new Pagina("repositorio", arq.lastModified());

        ControllerFile.save(pasta, "resources\\ModificedPasta.data");
    }

    /**
     * Método que realiza busca das palavras e atualiza a arvore de busca.
     *
     * @param palavra Palavra a ser procurada na arvore.
     * @return Retorna um objeto comparavel que contém a mesma palavra.
     * @throws IOException Abrir arquivo que contém a arvore salva.
     *
     */
    public Comparable search(String palavra) throws IOException, NullPointerException, Exception {

        Palavra p;

        p = (Palavra) search(buscaRapida.getRaiz(), palavra);
        if (p != null) {
            p.moreSearch();
            p.setPaginas(allFiles.sort(p.getPaginas(), new Crescente()));
        }
        saveTree();
        allFiles.saveListPage();
        allFiles.saveListFiles();
        return p;
    }

    /**
     * Método responsavel por atualizar as informações da Arvore de busca em
     * disco.
     *
     * @throws Exception Exceção ao ler/salvar.
     */
    public void atualizar() throws Exception {
        File arq = new File("resources\\Tree.data");
        if (!arq.exists()) {
            this.saveTree();
        }
        buscaRapida = this.readTree();
        this.saveTree();
    }

    /**
     * Algoritmo de busca binária para busca de palavras.
     *
     * @param atual No atual a ser comparado.
     * @param palavra Valor a ser buscado.
     * @return Retorna o objeto que foi encontrado.
     */
    public Comparable search(No atual, String palavra) {
        Comparable aux = null;
        if (atual != null) {

            if (((Palavra) atual.getDate()).getPalavra().compareTo(palavra) < 0) {
                aux = search(atual.getRight(), palavra);
            } else if (((Palavra) atual.getDate()).getPalavra().compareTo(palavra) > 0) {
                aux = search(atual.getLeft(), palavra);
            } else {
                return atual.getDate();
            }
        }
        return aux;
    }

    /**
     *
     * @return Retorna a arvore de busca;
     */
    public Arvore getBuscaRapida() {
        return buscaRapida;
    }

    /**
     * Verifica e adiciona uma palavra na arvore de busca.
     *
     * @param palavra Adiciona uma nova palavra a arvore de busca.
     * @return Retorna um booleano informando se a palavra existe entre os
     * arquivos ou não.
     * @throws IOException Possível exceção ao abrir arquivos para leitura.
     * @throws Exception
     */
    public boolean addPalavra(String palavra) throws IOException, Exception {
        boolean flag = allFiles.readFilesWords(palavra, buscaRapida);
        saveTree();

        return flag;
    }

    /**
     * Altera o valor da arvore de busca.
     *
     * @param buscaRapida Valor para alteração da arvore de busca.
     */
    public void setBuscaRapida(Arvore buscaRapida) {
        this.buscaRapida = buscaRapida;
    }

    /**
     * Retorna o controlador de páginas que é útilizado durante as buscas.
     *
     * @return Retorna o controlador de páginas
     */
    public ControllerPaginas getControlPages() {
        return allFiles;
    }

    /**
     * Altera o controlador de páginas
     *
     * @param controlPages Valor para alteração do controlador de páginas.
     */
    public void setControlPages(ControllerPaginas controlPages) {
        this.allFiles = controlPages;
    }

    /**
     *
     * @return Retorna o sort do classe.
     */
    public QuickSort getSort() {
        return sort;
    }

    /**
     * Salva a arvore em disco.
     *
     * @throws Exception Possível exceção ao salvar a arvore em um arquivo
     * binário.
     */
    public void saveTree() throws Exception {
        ControllerFile.save(buscaRapida, "resources\\Tree.data");
    }

    /**
     * Ler a arvore de busca que está salva em disco.
     *
     * @return @throws FileNotFoundException
     */
    public Arvore readTree() throws FileNotFoundException {
        Arvore temp;
        try {
            temp = (Arvore) ControllerFile.readDate("resources\\Tree.data");
        } catch (FileNotFoundException e) {
            temp = null;
        }
        if (temp == null) {
            return new Arvore();
        }
        return temp;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Pagina && o2 instanceof Pagina) {
            Pagina p1 = (Pagina) o1;
            Pagina p2 = (Pagina) o2;
            return -p1.compareTo(p2);
        } else if (o1 instanceof Palavra && o2 instanceof Palavra) {
            Palavra p1 = (Palavra) o1;
            Palavra p2 = (Palavra) o2;
            return p1.compareTo(p2);
        }
        return 0;
    }

    /**
     * Retorna uma fila com as palavras que estão salvas na arvore de busca.
     *
     * @return @throws java.lang.Exception
     */
    public Queue filaPalavras() throws Exception {
        atualizar();
        Queue fila = null;
        try {
            fila = buscaRapida.preOrder();
            if (fila == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException ex) {

        }
        return fila;

    }

    @Override
    public void run() {
        try {
            atualizarArvore();

        } catch (Exception ex) {
            Logger.getLogger(ControllerBusca.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

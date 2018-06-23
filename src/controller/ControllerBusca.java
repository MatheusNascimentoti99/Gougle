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
import model.Pagina;
import model.Palavra;

import util.Arvore;
import util.ArvorePalavra;
import util.Crescente;
import util.No;
import util.QuickSort;

/**
 *
 * @author Matheus Nascimento e Elvis Serafim
 */
public class ControllerBusca implements Comparator {

    ControllerPaginas allFiles;
    ControllerFile save;
    ArvorePalavra buscaRapida;
    File file;

    Crescente comparador;

    /**
     *
     */
    public ControllerBusca() {
        allFiles = new ControllerPaginas();
        save = new ControllerFile();
        buscaRapida = new ArvorePalavra();

        comparador = new Crescente();
    }

    /**
     * Método que realiza busca das palavras e atualiza a arvore de busca.
     *
     * @param palavra Palavra a ser procurada na arvore.
     * @return Retorna um objeto comparavel que contém a mesma palavra.
     * @throws IOException Abrir arquivo que contém a arvore salva.
     * @throws Exception
     */
    public Comparable search(String palavra) throws IOException, Exception {
        atualizar();

        Palavra p = (Palavra) search(buscaRapida.getRaiz(), palavra);
        boolean flag = true;
        if (p != null) {

            flag = therePages(p.getPaginas(), p);
            p.moreSearch();
            p.setPaginas(allFiles.sort(p.getPaginas(), new Crescente()));

        }

        if (flag == false) {
            buscaRapida.remover(p);
            allFiles.saveListPage();
            saveTree();

            return null;
        }
        saveTree();
        allFiles.saveListPage();
        allFiles.saveListFiles();
        return p;
    }

    /**
     * Verifica se as páginas existem.
     *
     * @param paginas Lista de paginas a ser verificada
     * @param palavra Verifica se a palavra ainda existe nas páginas.
     * @return Retorna um booleando informando se a palavra existe ou não nas
     * paginas.
     * @throws IOException Realiza leitura de arquivos(Páginas).
     * @throws Exception
     */
    public boolean therePages(LinkedList paginas, Palavra palavra) throws IOException, Exception {
        boolean existe = false;
        for (int i = 0; i < paginas.size(); i++) {
            Pagina p = (Pagina) paginas.get(i);
            int flag = changePagina(p);
            switch (flag) {
                case 0:
                    existe = true;
                    break;
                case 1:
                    paginas.remove(i);
                    if (!existe) {
                        existe = allFiles.readFileWord(file, palavra.getPalavra(), buscaRapida);
                    }
                    allFiles.readFileWord(file, palavra.getPalavra(), buscaRapida);
                    saveTree();
                    break;

                case -1:
                    paginas.remove(i);
            }
        }

        return existe;
    }

    public void atualizar() throws Exception {
        File arq = new File("resources\\Tree.date");
        if (!arq.exists()) {
            this.saveTree();
        }
        buscaRapida = this.readTree();
        this.saveTree();
    }

    private int changePagina(Pagina oldPag) {
        LinkedList paginas = allFiles.getFiles();
        Iterator it = paginas.iterator();
        File pag = new File("repositorio\\" + oldPag.getNome());
        if (!pag.exists()) {
            return -1;
        }
        while (it.hasNext()) {
            File arq = (File) it.next();
            if (arq.lastModified() == oldPag.getChange()) {
                return 0;
            } else if (arq.getName().compareTo(oldPag.getNome()) == 0) {
                this.file = arq;
                return 1;
            }

        }
        return -1;
    }

    /**
     * Algoritmo de busca binária para busca de palavras.
     *
     * @param atual No atual a ser comparado.
     * @param palavra Valor a ser buscado.
     * @return
     */
    public Comparable search(No atual, String palavra) {
        palavra = palavra.toUpperCase();
        Comparable aux = null;
        if (atual != null) {

            if (((Palavra) atual.getDate()).getPalavra().compareTo(palavra) < 0) {
                aux = search(atual.getDireita(), palavra);
            } else if (((Palavra) atual.getDate()).getPalavra().compareTo(palavra) > 0) {
                aux = search(atual.getEsquerda(), palavra);
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
    public ArvorePalavra getBuscaRapida() {
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
    public void setBuscaRapida(ArvorePalavra buscaRapida) {
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
     * Salva a arvore em disco.
     *
     * @throws Exception Possível exceção ao salvar a arvore em um arquivo
     * binário.
     */
    public void saveTree() throws Exception {
        save.save(buscaRapida, "resources\\Tree.date");
    }

    /**
     *
     * @return @throws FileNotFoundException
     */
    public ArvorePalavra readTree() throws FileNotFoundException {
        ArvorePalavra temp;
        try {
            temp = (ArvorePalavra) save.readDate("resources\\Tree.date");
        } catch (FileNotFoundException e) {
            temp = null;
        }
        if (temp == null) {
            return new ArvorePalavra();
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
            return -p1.compareTo(p2);
        }
        return 0;
    }

    /**
     *
     * @throws FileNotFoundException
     */
    /**
     *
     */
    public void ordenar() throws Exception {
        atualizar();
        Queue fila = buscaRapida.preOrder();
        if (fila != null) {
            QuickSort quick = new QuickSort();
            quick.quickSort(fila, comparador);

            while (!fila.isEmpty()) {
                Palavra p = (Palavra) fila.remove();
                System.out.println("" + p.getPalavra() + "" + p.getSearch());
            }
        }

    }
}

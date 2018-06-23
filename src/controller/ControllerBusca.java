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

/*
*
*Arvore não tá verificando se houve remoção ao iniciar o programa
*
*
*
*/
/**
 *
 * @author Matheus Nascimento
 */
public class ControllerBusca implements Comparator {

    ControllerPaginas allFiles;
    ControllerFile save;
    ArvorePalavra buscaRapida;
    File file;
    Queue fila ;
    QuickSort quick;
    Crescente comparador;

    public ControllerBusca() {
        allFiles = new ControllerPaginas();
        save = new ControllerFile();
        buscaRapida = new ArvorePalavra();
        fila = new LinkedList();
        quick = new QuickSort();
        comparador = new Crescente();
    }

    public Comparable search(String palavra) throws IOException, Exception {
        if (buscaRapida.getRaiz() == null ) {
            buscaRapida = readTree();
        }

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

    private int changePagina(Pagina oldPag) {
        LinkedList paginas = allFiles.getFiles();
        Iterator it = paginas.iterator();
        File pag = new File("repositorio\\"+oldPag.getNome());
        if(!pag.exists())
            return -1;
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

    public Arvore getBuscaRapida() {
        return buscaRapida;
    }

    public boolean addPalavra(String palavra) throws IOException, Exception {
        boolean flag = allFiles.readFilesWords(palavra, buscaRapida);
        saveTree();

        return flag;
    }

    public void setBuscaRapida(ArvorePalavra buscaRapida) {
        this.buscaRapida = buscaRapida;
    }

    public ControllerPaginas getControlRead() {
        return allFiles;
    }

    public void setControlRead(ControllerPaginas controlRead) {
        this.allFiles = controlRead;
    }

    public void saveTree() throws Exception {
        save.save(buscaRapida, "resources\\Tree.date");
    }

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
    
    public void ordenar() throws FileNotFoundException{
        ArvorePalavra arvore = buscaRapida;
        ordenar(arvore.getRaiz());
    }
        public void ordenar(No atual) {
            if (atual != null) {
                Comparable b = atual.getDate();
                fila.add(b);
                if (atual.getEsquerda()!= null) {
                    ordenar(atual.getEsquerda());
                } else if (atual.getDireita()!= null) {
                    ordenar(atual.getDireita());
                }
            }
        quick.quickSort(fila, comparador);
        
        while(!fila.isEmpty()){
            Palavra p = (Palavra) fila.remove();
            System.out.println(""+p.getPalavra()+""+p.getSearch());
        }
    }
}

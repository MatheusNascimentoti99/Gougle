/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import model.Pagina;
import model.Palavra;

import util.Arvore;
import util.No;

/**
 *
 * @author Matheus Nascimento
 */
public class ControllerBusca {

    ControllerPaginas allFiles;
    ControllerSave save;
    Arvore buscaRapida;

    public ControllerBusca() {
        allFiles = new ControllerPaginas();
        buscaRapida = new Arvore();
        save = new ControllerSave();
    }

    public Comparable search(String palavra) throws IOException {
        Palavra p = (Palavra) search(buscaRapida.getRaiz(), palavra);
        if (p != null) {
            changePaginas(p.getPaginas(), p);
        }
        return p;
    }

    public boolean changePaginas(LinkedList paginas, Palavra palavra) throws IOException {
        Iterator it = paginas.iterator();
        while (it.hasNext()) {
            Pagina p = (Pagina) it.next();
            File aux = null;
            int flag = changePagina(p, aux);
            switch (flag) {
                case 0:
                    return false;
                case 1:
                    paginas.remove(p);
                    allFiles.readFile(aux, palavra.getPalavra(), buscaRapida);
                    return true;
                default:
                    paginas.remove(p);
                    return true;
            }
        }
        return true;
    }

    private int changePagina(Pagina oldPag, File temp) {
        LinkedList paginas = allFiles.getFiles();
        Iterator it = paginas.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            if (file.lastModified() == oldPag.getChange()) {
                return 0;
            } else if (file.getName().compareTo(oldPag.getNome()) == 0) {
                temp = file;
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
                ((Palavra) atual.getDate()).moreSearch();
                return atual.getDate();
            }
        }
        return aux;
    }

    public Arvore getBuscaRapida() {
        return buscaRapida;
    }

    public boolean addPalavra(String palavra) throws IOException {
        return allFiles.readFiles(palavra, buscaRapida);

    }

    public void setBuscaRapida(Arvore buscaRapida) {
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

    public Arvore readTree() {
        Arvore temp;
        temp = (Arvore) save.readDate("resources\\Tree.date");
        if (temp == null) {
            return new Arvore();
        }
        return temp;
    }

}

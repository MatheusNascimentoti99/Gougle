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
    File file;
    public ControllerBusca() {
        allFiles = new ControllerPaginas();
        buscaRapida = new Arvore();
        save = new ControllerSave();
    }

    public Comparable search(String palavra) throws IOException {
        Palavra p = (Palavra) search(buscaRapida.getRaiz(), palavra);
        
        boolean flag = true;
        
        if (p != null) {
            
            flag = changePaginas(p.getPaginas(), p);
            p.moreSearch();
        }
        
        if(flag == false){
            buscaRapida.remover(p);
            System.out.println("foda");
            return null;
        }
        
        
        return p;
    }

    public boolean changePaginas(LinkedList paginas, Palavra palavra) throws IOException {
        System.out.println(";)");
        boolean existe = false;
        for(int i = 0; i < paginas.size(); i++) {
            Pagina p = (Pagina) paginas.get(i);
            int flag = changePagina(p);
            switch (flag) {
                case 0:
                    existe = true;break;
                case 1:
                    paginas.remove(i);
                    System.out.println(file.getName());
                    if(!existe)
                        existe = allFiles.readFile(file, palavra.getPalavra(), buscaRapida);
                    allFiles.readFile(file, palavra.getPalavra(), buscaRapida);break;
                case -1:
                    System.out.println("0");
            }
        }
        return existe;
    }

    private int changePagina(Pagina oldPag) {
        LinkedList paginas = allFiles.getFiles();
        Iterator it = paginas.iterator();
        
        while (it.hasNext()) {
            File arq = (File) it.next();
            if (arq.lastModified() == oldPag.getChange()) {
                System.out.println("3");
                return 0;
            } else if (arq.getName().compareTo(oldPag.getNome()) == 0) {
                this.file = arq;
                System.out.println("2");
                return 1;
            }

        }
        System.out.println("1");
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

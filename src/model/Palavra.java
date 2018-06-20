/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Matheus Nascimento
 */
public class Palavra implements Comparable, Comparator, Serializable{

    private LinkedList paginas;
    private String palavra;
    private int search;
   
    public Palavra(String palavra, Pagina pagina) {
        this.palavra = palavra;
        paginas = new LinkedList();
        paginas.add(pagina);
        search = 1;
    }
    public int getSearch(){
        return search;
       
    }
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Pagina && o2 instanceof Pagina) {
            Pagina p1 = (Pagina) o1;
            Pagina p2 = (Pagina) o2;
            return -p1.compareTo(p2);
        }
        else if(o1 instanceof Palavra && o2 instanceof Palavra){
            Palavra p1 = (Palavra) o1;
            Palavra p2 = (Palavra) o2;
            return -p1.compareTo(p2);
        }
        return 0;
    }


    public void moreSearch(){
        search++;
    }
    @Override
    public String toString() {
        return ("Palavra:" + palavra + "Buscas: " + search);
    }

    public Iterator imprimirArquivos() {        
        return paginas.iterator();
    }

    public String getPalavra() {
        return palavra;
    }

    public LinkedList getPaginas() {
        return paginas;
    }

    public void setPaginas(LinkedList paginas) {
        this.paginas = paginas;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
    @Override
    public int compareTo(Object o) {
        Palavra outra = (Palavra) o;
        return palavra.compareTo( outra.getPalavra());
    }
}

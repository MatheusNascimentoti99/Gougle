/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Matheus Nascimento
 */
public class Palavra implements Comparable{

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

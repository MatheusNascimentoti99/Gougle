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
public class Palavra implements Comparable, Comparator{

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
    
    public void sort(){
        paginas.sort(this);
    }

    
    @Override
    public int compare(Object o1, Object o2) {
        Pagina p1 = (Pagina) o1;
        Pagina p2 = (Pagina) o2;
        return -p1.compareTo(p2);
    }

    public void moreSearch(){
        search++;
         paginas.sort(this);
    }
    @Override
    public String toString() {
        return ("Palavra:" + palavra + " - Arquivos: " + imprimirArquivos());
    }

    public String imprimirArquivos() {
        Iterator temp = paginas.iterator();
        String nomePaginas = "";
        Pagina aux = (Pagina) temp.next();
        nomePaginas = nomePaginas + "[" + aux.getNome() +", Quantidade de repetições " +aux.getRepeticao()+"]";
        while (temp.hasNext()) {
            aux = (Pagina) temp.next();
            nomePaginas = nomePaginas + ", [" + aux.getNome() +", Quantidade de repetições " +aux.getRepeticao()+"]";   
        }
        return nomePaginas;
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

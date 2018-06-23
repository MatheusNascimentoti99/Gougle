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
 *<b>Palavra</b> Classe utilizada para salvas as informações de uma palavra, contendo o nome, paginas que a palavra existe e a quantidade de vezes pesquisada.
 * @author Matheus Nascimento e Elvis Serafim
 */
public class Palavra implements Comparable, Comparator, Serializable{

    private LinkedList paginas;
    private String palavra;
    private int search;
   
    /**
     *
     * @param palavra Uma String informando o Palavra.
     * @param pagina Página que existe a palavra.
     */
    public Palavra(String palavra, Pagina pagina) {
        this.palavra = palavra;
        paginas = new LinkedList();
        paginas.add(pagina);
        search = 0;
    }

    /**
     *Quantidade de buscas por palavra.
     * @return Retorna a quantidade de vezes que a palavra foi pesquisada.
     */
    public int getSearch(){
        return search;
       
    }
    
    /**
     *Incrementa a quantidade de vezes que a palavra foi pesquisada. 
     */
    public void moreSearch(){
        search++;
    }
    @Override
    public String toString() {
        return ("Palavra: " + palavra + "   Buscas: " + search);
    }

    /**
     *Retorna um iterator para percorrer todas as páginas que a palavra pertence.
     * @return Retorna o iterator de paginas
     */
    public Iterator imprimirArquivos() {        
        return paginas.iterator();
    }

    /**
     *
     * @return Retorna uma String palavra.
     */
    public String getPalavra() {
        return palavra;
    }

    /**
     *Lista de páginas que a palavra pertence.
     * @return Retorna as paginas que a palavra pertence.
     */
    public LinkedList getPaginas() {
        return paginas;
    }

    /**
     *
     * @param paginas Parâmentro dado para alterar a lista de páginas.
     */
    public void setPaginas(LinkedList paginas) {
        this.paginas = paginas;
    }
    
    /**
     *
     * @param palavra Altera a palavra.
     */
    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
    @Override
    public int compareTo(Object o) {
        Palavra outra = (Palavra) o;
        return palavra.compareTo( outra.getPalavra());
    }

    /**
     *Quantidade de vezes que a palavra foi pesquisada.
     * @param search Retorna a quantidade de vezes que a palavra foi pesquisada.
     */
    public void setSearch(int search) {
        this.search = search;
    }

    @Override
    public int compare(Object o1, Object o2) {
            Pagina p1 = (Pagina) o1;
            Pagina p2 = (Pagina) o2;
            return -p1.compareTo(p2);
  
    }
}

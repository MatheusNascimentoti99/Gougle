/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Matheus Nascimento
 */
public class Palavra implements Comparable{

    private LinkedList paginas;
    private String palavra;

    public Palavra(String palavra) {
        this.palavra = palavra;
    }

    @Override
    public String toString() {
        return ("Palavra:" + palavra + " - Arquivos" + imprimirArquivos());
    }

    private String imprimirArquivos() {
        Iterator temp = paginas.iterator();
        String nomePaginas = "";
        while (!temp.hasNext()) {
            nomePaginas = nomePaginas.concat(" - " + ((Pagina) temp.next()).getNome());
        }
        return nomePaginas;
    }

    public Object getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
    @Override
    public int compareTo(Object o) {
        return palavra.compareTo((String) o);
    }
}

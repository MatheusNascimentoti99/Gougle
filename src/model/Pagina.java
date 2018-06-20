/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author Matheus Nascimento
 */
public class Pagina implements  Comparable {
    private String nome;
    private Palavra palavra;
    private int relevancia;
    private long change;
    public Pagina(String nome, long change) {
        this.nome = nome;
        this.relevancia = 1;
        this.change = change;
        
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public void setPalavra(Palavra palavra) {
        this.palavra = palavra;
    }

    public long getChange() {
        return change;
    }

    public void setChange(long change) {
        this.change = change;
    }

    public int getRepeticao() {
        return relevancia;
    }

    public void repetir() {
        this.relevancia = relevancia +1;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    @Override
    public int compareTo(Object o) {
        Pagina outro = (Pagina) o;
        return   this.relevancia - outro.relevancia;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Comparator;

/**
 *
 * @author Matheus Nascimento
 */
public class Pagina implements  Comparable {
    private String nome;
    private int repeticao;

    public Pagina(String nome) {
        this.nome = nome;
        this.repeticao = 1;
    }

    public int getRepeticao() {
        return repeticao;
    }

    public void repetir() {
        this.repeticao = repeticao +1;
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
        return this.repeticao - outro.repeticao;
    }
    
    
    
}

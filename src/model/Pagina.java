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
public class Pagina {
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
    
    
}

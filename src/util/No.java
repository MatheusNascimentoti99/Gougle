/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;




/**
 *
 * @author Matheus Nascimento
 */
public final class No implements Serializable{
    private Comparable date;
    private No esquerda;
    private No direita;
    private No pai;
    private int balanceamento;
    

    public No(Comparable date) {
        this.date = date;
        setEsquerda(setDireita(setPai(null)));
        setBalanceamento(0);
        
    }
  

    public Comparable getDate() {
        return date;
    }

    public void setDate(Comparable date) {
        this.date = date;
    }

    



    public int getBalanceamento() {
        return balanceamento;
    }

    public void setBalanceamento(int balanceamento) {
        this.balanceamento = balanceamento;
    }

    public No getPai() {
        return pai;
    }

    public No setPai(No pai) {
        this.pai = pai;
        return pai;
    }

    public No getDireita() {
        return direita;
    }

    public No setDireita(No direita) {
        this.direita = direita;
        return direita;
    }

    public No getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }


    
}

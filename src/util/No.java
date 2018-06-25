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
        setLeft(setRight(setPai(null)));
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

    public No getRight() {
        return direita;
    }

    public No setRight(No direita) {
        this.direita = direita;
        return direita;
    }

    public No getLeft() {
        return esquerda;
    }

    public void setLeft(No esquerda) {
        this.esquerda = esquerda;
    }


    
}

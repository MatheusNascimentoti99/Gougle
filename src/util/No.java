/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;




/**
 * A classe <b>No</b> , é responsável por armazenar os dados para a inserção na árvore.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 */
public final class No implements Serializable{
    private Comparable data;
    private No esquerda;
    private No direita;
    private No pai;
    private int balanceamento;
    
    /**
     *Construtor da classe No, onde é recebido como parâmetro, um objeto Comparable. 
     * @param data Objeto Comparable a ser inserido em um objeto No.
     */
    public No(Comparable data) {
        this.data = data;
        setLeft(setRight(setPai(null)));
        setBalanceamento(0);
        
    }
  
    /**
     *Método que retorna o objeto dentro do No.
     * @return Objeto Comparable.
     */
    public Comparable getData() {
        return data;
    }

    /**
     *Método que designa um novo objeto para o No.
     * @param data Novo objeto a ser inserido no No.
     */
    public void setData(Comparable data) {
        this.data = data;
    }

    /**
     *Método que retorna o valor do balaceamento do No.
     * @return int, valor atual do balaceamento.
     */
    public int getBalanceamento() {
        return balanceamento;
    }

    /**
     * Método que designa um novo valor ao balaceamento do No.
     * @param balanceamento novo valor de balanceamento.
     */
    public void setBalanceamento(int balanceamento) {
        this.balanceamento = balanceamento;
    }

    /**
     *Método que retorna o pai do No.
     * @return No pai do No atual.
     */
    public No getPai() {
        return pai;
    }

    /**
     * Método que designa um novo pai para o No.
     * @param pai No a ser o No pai do No atual.
     * @return No pai.
     */
    public No setPai(No pai) {
        this.pai = pai;
        return pai;
    }

    /**
     *Método que retorna o No filho da direita do No atual.
     * @return No filho.
     */
    public No getRight() {
        return direita;
    }

    /**
     *Método que designa um novo filho da direita para o No atual.
     * @param direita No a ser o filho da direita do No atual.
     * @return No da direita.
     */
    public No setRight(No direita) {
        this.direita = direita;
        return direita;
    }

    /**
     *Método que retorna o No filho da esquerda do No atual.
     * @return No filho da esquerda.
     */
    public No getLeft() {
        return esquerda;
    }

    /**
     * Método que designa um novo filho da esquerda para o No atual.
     * @param esquerda No a ser o filho da esquerda do No atual.
     */
    public void setLeft(No esquerda) {
        this.esquerda = esquerda;
    }


    
}

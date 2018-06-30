/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Matheus Nascimento e Elvis Serafim
 * <b>Pagina</b> Classe modelo para salvar o nome e o <i>lastModificed</i> de
 * uma página.
 */
public class Pagina implements Comparable, Serializable {

    private final String nome;
    private int relevancia;
    private long change;
    private int visit;

    /**
     *
     * @param nome Nome da p�gina considerando tamb�m a enten��o .txt.
     * @param change long utilizado para verifica��o de mudan�as no arquivo.
     */
    public Pagina(String nome, long change) {
        this.nome = nome;
        relevancia = 1;
        this.change = change;
        visit = 0;

    }

    @Override
    public boolean equals(Object obj) {

        final Pagina other = (Pagina) obj;
        return Objects.equals(this.nome, other.nome);
    }

    /**
     *
     * @return Retorna a relev�ncia da p�gina.
     */
    public int getRelevancia() {
        return relevancia;
    }
    public void setRelevancia(int relevancia){
        this.relevancia = relevancia;
    }

    /**
     * Quantidade de vezes que a p�gina foi visitada.
     *
     * @return Retorna a quantidade de vezes que a p�gina foi exibida.
     */
    public int getVisit() {
        return visit;
    }

    @Override
    public String toString() {
        return "P�gina: " + nome + "    Visitas: " + visit;
    }

    /**
     * Incrementa mais 1 ao valor de virita.
     */
    public void visited() {
        visit++;
    }

    /**
     *
     * @return Retorna o valor de modifica��es da p�gina.
     */
    public long getChange() {
        return change;
    }

    /**
     *
     * @param change Alreta o valor de modificação da p�gina
     */
    public void setChange(long change) {
        this.change = change;
    }

    /**
     *
     * @return Retorna o valor de relev�cncia da p�gina a uma determinada Palavra
     * que ele pertence.
     */
    public int getRepeticao() {
        return relevancia;
    }

    /**
     * Incrementa mais 1 a cada vez que a palavra se repetir na p�gina.
     */
    public void repetir() {
        this.relevancia = relevancia + 1;
    }

    /**
     *
     * @return Retorna o nome da p�gina
     */
    public String getNome() {
        return nome;
    }

    @Override
    public int compareTo(Object o) {
        Pagina outro = (Pagina) o;
        return this.relevancia - outro.relevancia;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }
   
}

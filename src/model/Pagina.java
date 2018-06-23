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
 * <b>Pagina</b> Classe modelo para salvar o nome e o <i>lastModificed</i> de uma página.
 */
public class Pagina implements  Comparable, Serializable {
    private String nome;
    private int relevancia;
    private long change;
    private int visit;

    /**
     *
     * @param nome Nome da página considerando também a entenção .txt.
     * @param change long utilizado para verificação de mudanças no arquivo.
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
     * @return Retorna a relevância da página.
     */
    public int getRelevancia() {
        return relevancia;
    }

    /**
     *
     * @param relevancia Valor dado para alteração da relevancia da página.
     */
    public void setRelevancia(int relevancia) {
        this.relevancia = relevancia;
    }

    /**
     *Quantidade de vezes que a página foi visitada.
     * @return Retorna a quantidade de vezes que a página foi exibida.
     */
    public int getVisit() {
        return visit;
    }
    @Override
    public String toString(){
        return "Página: "+nome+"    Visitas: "+visit;
    }

    /**
     *Altera o nome do arquivo.
     * @param nome Parâmetro dado para alteração do nome do arquivo.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *Altera a quantidade de visistas.
     * @param visit
     */
    public void setVisit(int visit) {
        this.visit = visit;
    }


    
    /**
     * Incrementa mais 1 ao valor de virita.
     */
    public void visited(){
        visit++;
    }

  

    /**
     *
     * @return  Retorna o valor de modificação da página.
     */
    public long getChange() {
        return change;
    }

    /**
     *
     * @param change Alreta o valor de modificação da página
     */
    public void setChange(long change) {
        this.change = change;
    }

    /**
     *
     * @return Retorna o valor de relevância da página a uma determinada Palavra que ele pertence.
     */
    public int getRepeticao() {
        return relevancia;
    }

    /**
     * Incrementa mais 1 a cada vez que a palavra se repetir na página.
     */
    public void repetir() {
        this.relevancia = relevancia +1;
    }
    
    /**
     *
     * @return Retorna o nome da página
     */
    public String getNome() {
        return nome;
    }


    
    @Override
    public int compareTo(Object o) {
        Pagina outro = (Pagina) o;
        return   this.relevancia - outro.relevancia;
    }

}

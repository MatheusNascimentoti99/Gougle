/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import model.Pagina;
import model.Palavra;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de testes para a classe Arvore.
  * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 **/
public class ArvoreTest {
    
    private Arvore arvore;
    private Palavra p, p2, p3;
    private Comparable b, b2, b3;
    public ArvoreTest() {
        arvore = new Arvore();
        p = new Palavra("palavra1", new Pagina("page1", 1111));
        p2 = new Palavra("palavra2", new Pagina("page2", 1113));
        p3 = new Palavra("palavra3", new Pagina("page3", 1112));
        
    }

    /**
     * Teste do método de inserção da árvore AVL.
     */
    @Test
    public void testInserir() {
      
        b = (Comparable) p;
        b2 = (Comparable) p2;
        b3 = (Comparable) p3;
        
        assertEquals(arvore.isEmpty(), true);
        arvore.inserir(b);
        assertEquals(arvore.isEmpty(), false);
        assertEquals(arvore.getRaiz().getBalanceamento(), 0);
        arvore.inserir(b2);
        assertEquals(arvore.isEmpty(), false);
        assertEquals(arvore.getRaiz().getBalanceamento(), 1);
        arvore.inserir(b3);
        assertEquals(arvore.getRaiz().getBalanceamento(), 0);
        arvore.remover(p);
        arvore.remover(p2);
        arvore.remover(p3);
        assertEquals(arvore.isEmpty(), true);
    }

    /**
     * Teste do método de remoção da árvore AVL.
     */
    @Test
    public void testRemover() {
        b = (Comparable) p;
        b2 = (Comparable) p2;
        b3 = (Comparable) p3;
        
        arvore.inserir(b);
        arvore.inserir(b2);
        arvore.inserir(b3);
        
        arvore.remover(p3);
        assertEquals(arvore.getRaiz().getRight(), null);
        arvore.remover(p2);
        assertEquals(arvore.getRaiz().getLeft(), null);
        arvore.remover(p);
        arvore.inserir(b);
        arvore.inserir(b2);
        arvore.inserir(b3);
        
        assertEquals(arvore.getRaiz().getData().compareTo(p), 1);
        assertEquals(arvore.getRaiz().getData().compareTo(p2), 0);
        assertEquals(arvore.getRaiz().getData().compareTo(p3), -1);
        arvore.remover(b2);
        assertEquals(arvore.getRaiz().getData().compareTo(p), 0);
        assertEquals(arvore.getRaiz().getRight().getData().compareTo(p3), 0);
//        assertEquals(arvore.getRaiz(), null);
//        assertEquals(arvore.isEmpty(), true);
        
    }
    /**
     * Teste do método que verifica se a árvore está vazia.
     */
    @Test
    public void testIsEmpty() {
        b = (Comparable) p;
        assertEquals(arvore.isEmpty(), true);
        arvore.inserir(b);
        assertEquals(arvore.isEmpty(), false);
        arvore.remover(p);
        assertEquals(arvore.isEmpty(), true);
    }
}

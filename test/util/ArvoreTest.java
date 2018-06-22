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
 *
 * @author Usuário 01
 */
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
     * Test of inserir method, of class Arvore.
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
     * Test of remover method, of class Arvore.
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
        assertEquals(arvore.getRaiz().getDireita(), null);
        arvore.remover(p2);
        assertEquals(arvore.getRaiz().getEsquerda(), null);
        arvore.remover(p);
        assertEquals(arvore.getRaiz(), null);
        assertEquals(arvore.isEmpty(), true);
        
    }
    /**
     * Test of rotacaoEsquerda method, of class Arvore.
     */
    @Test
    public void testRotacaoEsquerda() {
    }

    /**
     * Test of rotacaoDireita method, of class Arvore.
     */
    @Test
    public void testRotacaoDireita() {
    }

    /**
     * Test of duplaRotacaoEsquerdaDireita method, of class Arvore.
     */
    @Test
    public void testDuplaRotacaoEsquerdaDireita() {
    }

    /**
     * Test of duplaRotacaoDireitaEsquerda method, of class Arvore.
     */
    @Test
    public void testDuplaRotacaoDireitaEsquerda() {
    }

    /**
     * Test of sucessor method, of class Arvore.
     */
    @Test
    public void testSucessor() {
    }

    /**
     * Test of isEmpty method, of class Arvore.
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

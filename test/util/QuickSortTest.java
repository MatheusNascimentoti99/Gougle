/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.LinkedList;
import java.util.Queue;
import model.Pagina;
import model.Palavra;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

 /**
  * Classe de Testes da classe QuickSort.
  * 
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 **/
public class QuickSortTest {
     private Queue fila;
     private Palavra p,p2, p3;
     private QuickSort quick;
     private Crescente crescente;
     private Decrescente decrescente;
     public QuickSortTest() {
        p = new Palavra("palavra1", new Pagina("page1", 1111));
        p2 = new Palavra("palavra2", new Pagina("page2", 1113));
        p3 = new Palavra("palavra3", new Pagina("page3", 1112));
       fila = new LinkedList();
       quick = new QuickSort();
       crescente = new Crescente();
       decrescente = new Decrescente();    
     }
    
    @Before
    public void setUp() {
    
    }

    @Test
    public void testQuickSort() {
        
        p.setSearch(2);
        p2.setSearch(1);
        p3.setSearch(3);
        
        fila.add(p);
        fila.add(p2);
        fila.add(p3);
        
        assertEquals(false, fila.isEmpty());
        
        quick.quickSort(fila, crescente);
        
        assertEquals(p3, fila.remove());
        
        assertEquals(p, fila.remove());
        
        assertEquals(p2, fila.remove());
        
        assertEquals(true, fila.isEmpty());
        
        fila.add(p);
        fila.add(p2);
        fila.add(p3);
        
        assertEquals(false, fila.isEmpty());
        
        quick.quickSort(fila, decrescente);
        
        assertEquals(p2, fila.remove());
        
        assertEquals(p, fila.remove());
        
        assertEquals(p3, fila.remove());
        
        assertEquals(true, fila.isEmpty());
    }
    
}

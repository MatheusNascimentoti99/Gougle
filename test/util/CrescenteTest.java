/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import model.Pagina;
import model.Palavra;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

 /**
  * Classe de Testes da classe Crescente.
  * 
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 **/
public class CrescenteTest {
    
    private Crescente crescente;
    private Palavra p, p2;
    private Pagina pag, pag2;
   
    public CrescenteTest() {
        
        crescente = new Crescente();
         p = new Palavra("palavra1", new Pagina("page1", 1111));
        p2 = new Palavra("palavra2", new Pagina("page2", 1113));
        pag = new Pagina("pagina", 1122);
        pag2 = new Pagina("pagina", 1322);        
    }
    
    @Test
    public void testCompare() {
        p.setSearch(2);
        p2.setSearch(3);
        
        assertEquals(1, crescente.compare(p, p2));
        p.setSearch(3);
        assertEquals(0, crescente.compare(p, p2));
        p.setSearch(4);
        assertEquals(-1, crescente.compare(p, p2));
        
        pag.setVisit(3);
        pag2.setVisit(2);
        
        assertEquals(-1, crescente.compare(pag, pag2));
        pag2.setVisit(3);
        assertEquals(0, crescente.compare(pag, pag2));
        pag2.setVisit(4);
        assertEquals(1, crescente.compare(pag, pag2));
    }
    
}
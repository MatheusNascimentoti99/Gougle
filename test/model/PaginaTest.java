package model;

import model.Pagina;
import model.Palavra;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de Teste para a classe Pagina.
 */
public class PaginaTest {

	private Pagina p;
	
     /**
     * Este método é executado antes de cada teste de unidade (testes a seguir), 
     * e serve para inicializar objetos que são utilizados nos testes.
     */
    @Before
    public void setUp() throws Exception {
        p = new Pagina("pagina", 1122);
    }

    /**
     * Teste de unidade que verifica se os atributos de uma Pagina são atribuidos e 
     * modificados corretamente.
     */
    @Test
    public void testBasic() {
        
        assertTrue(p.getNome().equals("pagina"));
        
        assertEquals(p.getRelevancia(), 1);
        p.setRelevancia(2);
        assertEquals(p.getRelevancia(), 2);
        p.setRelevancia(0);
        assertEquals(p.getRelevancia(), 0);
        p.repetir();
        assertEquals(p.getRelevancia(), 1);
        
        assertEquals(p.getChange(), 1122);
        p.setChange(1112);
        assertEquals(p.getChange(), 1112);
        p.setChange(1111);
        assertEquals(p.getChange(), 1111);
        
        assertEquals(p.getVisit(), 0);
        p.visited();
        p.visited();
        assertEquals(p.getVisit(), 2);
        p.visited();
        assertEquals(p.getVisit(), 3);
        p.visited();
        assertEquals(p.getVisit(), 4);
    }
}

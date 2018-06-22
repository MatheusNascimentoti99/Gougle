package model;

import model.Pagina;
import model.Palavra;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

/**
 * Testes de unidade para a classe {@link Foliao}
 */
public class PaginaTest {

	private Pagina p;
	
	
	/**
     * Este m�todo � executado antes de cada teste de unidade (testes a seguir), 
     * e serve para inicializar objetos que s�o utilizados nos testes.
     */
    @Before
    public void setUp() throws Exception {
        p = new Pagina("pagina", 1122);
    }

    /**
     * Teste de unidade que verifica se os atributos de um foli�o s�o atribuidos e 
     * modificados corretamente. Al�m disso, ele checa se o m�todo equals que 
     * compara dois foli�es foi implementado corretamente.
     */
    @Test
    public void testBasic() {
        
        assertTrue(p.getNome().equals("pagina"));
        p.setNome("pagina2");
        assertTrue(p.getNome().equals("pagina2"));
        assertFalse(p.getNome().equals("pagina"));
        
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
        p.setVisit(2);
        assertEquals(p.getVisit(), 2);
        p.setVisit(3);
        assertEquals(p.getVisit(), 3);
        p.visited();
        assertEquals(p.getVisit(), 4);
    }
}

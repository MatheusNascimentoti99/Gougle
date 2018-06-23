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
public class PalavraTest {

	private Palavra p;
	
	
	/**
     * Este m�todo � executado antes de cada teste de unidade (testes a seguir), 
     * e serve para inicializar objetos que s�o utilizados nos testes.
     */
    @Before
    public void setUp() throws Exception {
        p = new Palavra("José", new Pagina("pag1", 1111));        
    }

    /**
     * Teste de unidade que verifica se os atributos de um foli�o s�o atribuidos e 
     * modificados corretamente. Al�m disso, ele checa se o m�todo equals que 
     * compara dois foli�es foi implementado corretamente.
     */
    @Test
    public void testBasic() {
        
        assertEquals(0, p.getSearch());
        p.setSearch(2);
        assertEquals(2, p.getSearch());
        p.setSearch(3);
        assertEquals(3, p.getSearch());
        p.moreSearch();
        assertEquals(4, p.getSearch());
        
        assertEquals(p.getPalavra(), "José");

        
        Pagina page = (Pagina) p.getPaginas().getFirst();
        assertEquals("pag1", page.getNome());
        
        Pagina page2 = new Pagina("pagina2", 1234);
        p.getPaginas().add(page2);
        
        Pagina c = (Pagina) p.getPaginas().getLast();
        assertEquals("pagina2", c.getNome());
        
        Palavra d = new Palavra("Antonio", new Pagina("pag2", 1113));
        
        assertFalse(d.equals(p));
        
    }
}

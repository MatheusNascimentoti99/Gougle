package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de Testes para a classe Palavra.
 */
public class PalavraTest {

	private Palavra p;
	
     /**
     * Este método é executado antes de cada teste de unidade (testes a seguir), 
     * e serve para inicializar objetos que são utilizados nos testes.
     */
    @Before
    public void setUp() throws Exception {
        p = new Palavra("José", new Pagina("pag1", 1111));        
    }

    /**
     * Teste de unidade que verifica se os atributos de uma Palavra são atribuidos e 
     * modificados corretamente.
     * **/
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

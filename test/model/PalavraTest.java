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
        p.getSearch();
    }
}

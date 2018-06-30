/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.LinkedList;
import model.Pagina;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

 /**
  * Classe de Testes da classe ControllerPaginas.
  * 
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 **/
public class ControllerPaginasTest {

    ControllerPaginas controlPages;
    Pagina p1, p2, p3;

    @Before
    public void setUp() {
        controlPages = new ControllerPaginas();
        p1 = new Pagina("teste1.txt", 123456);
        p2 = new Pagina("teste2.txt", 987654);
        p3 = new Pagina("teste3.txt", 654123);
    }

    @After
    public void tearDown() {
        File listPages = new File("resources\\ListPages.date");
        listPages.delete();
    }

    @Test
    public void testGetFiles() {
        LinkedList files = controlPages.getFiles();
        assertEquals(true, controlPages.getFiles().equals(files));
        files.remove();
        assertEquals(false, controlPages.getFiles().equals(files));
        assertTrue(controlPages.getFiles().size() > files.size());
        assertFalse(controlPages.getFiles().equals(files));
    }

    @Test
    public void testGetPaginas() throws Exception {
        assertEquals(false, controlPages.getPaginas().isEmpty());
    }

    @Test
    public void testReadListFiles() throws Exception {
        controlPages.atualize();                            //Atualiza o arquivo que contém a lista de arquivos.
        LinkedList files = new LinkedList();
        assertFalse(controlPages.readListFiles().isEmpty());
        assertFalse(controlPages.readListFiles().equals(files));
        files = controlPages.readListFiles();
        assertTrue(controlPages.readListFiles().equals(files));

    }

    @Test
    public void testSaveListFiles() throws Exception {
        controlPages.atualize();
        controlPages.saveListFiles();
        File file = new File("resources\\ListPages.date");
        assertTrue(file.exists());
        LinkedList files = controlPages.readListFiles();
        assertEquals(files, controlPages.readListFiles());

    }

    @Test
    public void testModificedFiles() throws Exception {
        LinkedList paginas = new LinkedList();
        paginas.add(p1);
        assertEquals(true, controlPages.modificedFiles(paginas));          //Método só considera as páginas que estão no repositório.
        File file = new File("repositorio\\Files.txt");
        ControllerFile.save(file, "repositorio");
        assertEquals(true, controlPages.modificedFiles(paginas));
    }

    @Test
    public void testAtualize() throws Exception {
        File file = new File("resources\\ListPages.date");
        file.delete();
        assertFalse(file.exists());
        assertEquals(null, controlPages.readListFiles());
        
        controlPages.atualize();
        assertTrue(file.exists());
        LinkedList files = controlPages.readListFiles();
        assertEquals(files, controlPages.readListFiles());
        
    }


    @Test
    public void testSort() {
        LinkedList paginas = new LinkedList();
        p1.setRelevancia(5);
        p2.setRelevancia(3);
        p3.setRelevancia(6);
        
        paginas.add(p1);
        paginas.add(p2);
        paginas.add(p3);
        
        assertTrue(p1.equals(paginas.getFirst()));
        controlPages.sort(paginas, new ControllerBusca());
        assertFalse(p1.equals(paginas.getFirst()));
        assertTrue(p3.equals(paginas.getFirst()));
        
    }

}

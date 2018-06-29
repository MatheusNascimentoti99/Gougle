/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.LinkedList;
import model.Pagina;
import model.Palavra;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import util.ArvorePalavra;
import util.QuickSort;

/**
 *
 * @author Matheus Nascimento
 */
public class ControllerBuscaTest {

    ControllerBusca search;
    LinkedList paginas;
    Palavra p1;
    Pagina pag1, pag2;

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        search = new ControllerBusca();
        paginas = new LinkedList();
        pag1 = new Pagina("teste1.txt", 123456);
        p1 = new Palavra("Palavra teste", pag1);
        pag2 = new Pagina("Pagina 2 teste", 654321);
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("resources\\Tree.date");
        file.delete();
        File file2 = new File("resources\\Files.date");
        file2.delete();
        File file3 = new File("resources\\ListPages.date");
        file3.delete();

    }


    @Test
    public void testGetBuscaRapida() {
        ArvorePalavra tree1 = new ArvorePalavra();
        assertFalse(search.getBuscaRapida().equals(tree1));
        search.setBuscaRapida(tree1);
        assertTrue(search.getBuscaRapida().equals(tree1));
    }

    @Test
    public void testAddPalavra() throws Exception {
        String palavra = "testando";
        assertEquals(true, search.addPalavra(palavra));
        palavra = "blablablabla";
        assertEquals(false, search.addPalavra(palavra));
    }

    @Test
    public void testSetBuscaRapida() {
        ArvorePalavra tree1 = new ArvorePalavra();
        assertFalse(search.getBuscaRapida().equals(tree1));
        search.setBuscaRapida(tree1);
        assertTrue(search.getBuscaRapida().equals(tree1));
    }

    @Test
    public void testGetControlPages() {
        ControllerPaginas control = new ControllerPaginas();
        assertFalse(search.getControlPages().equals(control));
        search.setControlPages(control);
        assertTrue(search.getControlPages().equals(control));
    }

    @Test
    public void testSetControlPages() {
        ControllerPaginas control = new ControllerPaginas();
        assertFalse(search.getControlPages().equals(control));
        search.setControlPages(control);
        assertTrue(search.getControlPages().equals(control));
    }

    @Test
    public void testGetSort() {
        QuickSort sort = new QuickSort();
        assertFalse(search.getSort().equals(sort));
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matheus Nascimento
 */
public class ControllerPaginasTest {
    ControllerPaginas controlPages;

    
 
    
    @Before
    public void setUp() {
        controlPages = new ControllerPaginas();
    }
    
    @After
    public void tearDown() {
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
        assertEquals(false,controlPages.getPaginas().isEmpty());
    }

    @Test
    public void testReadListFiles() throws Exception {
    }

    @Test
    public void testSaveListFiles() throws Exception {
    }

    @Test
    public void testModificedFiles() throws Exception {
    }

    @Test
    public void testAtualize() throws Exception {
    }

    @Test
    public void testReadFilesWords() throws Exception {
    }

    @Test
    public void testReadFileWord() throws Exception {
    }

    @Test
    public void testSort() {
    }
    
}

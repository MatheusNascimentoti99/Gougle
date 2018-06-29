package controller;


import java.io.File;
import model.Pagina;
import model.Palavra;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matheus Nascimento
 */
public class ControllerFileTest {
    
    ControllerFile controleFile;
    String diretorio;
    Palavra palavra, palavra2;
    

    @Before
    public void setUp() {
        controleFile = new ControllerFile();
        diretorio = "Resources/teste.teste";
        palavra = new Palavra("Primeira PalavraTest", new Pagina("Pagina Teste", 123456));
        palavra2 = new Palavra("Segunda PalavraTest", new Pagina("Pagina Teste", 123456));
    }

    @Test
    public void salvarArquivo() throws Exception {
        controleFile.save(palavra, diretorio);
        File arquivo = new File(diretorio);
        assertEquals(true, arquivo.exists());
        arquivo.delete();
    }

    @Test
    public void lerArquivo() throws Exception {
        controleFile.save(palavra, diretorio);
        palavra2 = (Palavra) controleFile.readDate(diretorio);
        assertEquals(false ,palavra.equals(palavra2));
        File arquivo = new File(diretorio);
        arquivo.delete();
    }
}

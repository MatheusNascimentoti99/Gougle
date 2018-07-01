package Gougle;


import controller.ControllerBusca;
import controller.ControllerFileTest;
import controller.ControllerPaginasTest;
import model.PaginaTest;
import model.PalavraTest;
import org.junit.runner.*;
import org.junit.runners.*;
import util.ArvoreTest;
import util.CrescenteTest;
import util.DecrescenteTest;
import util.QuickSortTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ArvoreTest.class,
    CrescenteTest.class,
    DecrescenteTest.class,
    QuickSortTest.class,
    PaginaTest.class,
    PalavraTest.class,
    ControllerFileTest.class,
    ControllerBusca.class,
    ControllerPaginasTest.class

})
public class AllTests {
}
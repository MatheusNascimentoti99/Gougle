/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Comparator;
import model.Pagina;
import model.Palavra;

/**
 * A classe <b>Crescente</b> , é responsável pela ordenação dos objetos Páginas e Palavras, de forma crescente.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 */
public class Crescente implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Pagina && o2 instanceof Pagina) {
            Pagina p1 = (Pagina) o1;
            Pagina p2 = (Pagina) o2;
            return p1.getVisit() > p2.getVisit() ? -1 : p1.getVisit() == p2.getVisit() ? 0 : 1;
        }
        else if(o1 instanceof Palavra && o2 instanceof Palavra){
            Palavra p1 = (Palavra) o1;
            Palavra p2 = (Palavra) o2;
            return p1.getSearch() > p2.getSearch() ? -1 : p1.getSearch() == p2.getSearch() ? 0 : 1;
        }
        return 0;
    }
}

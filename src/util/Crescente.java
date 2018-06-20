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
 *
 * @author Matheus Nascimento
 */
public class Crescente implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Pagina && o2 instanceof Pagina) {
            Pagina p1 = (Pagina) o1;
            Pagina p2 = (Pagina) o2;
            return -p1.compareTo(p2);
        }
        else if(o1 instanceof Palavra && o2 instanceof Palavra){
            Palavra p1 = (Palavra) o1;
            Palavra p2 = (Palavra) o2;
            return -p1.compareTo(p2);
        }
        return 0;
    }
}

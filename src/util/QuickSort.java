/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A classe <b>QuickSort</b> é a classe para armazenar o algoritmo de ordenação de mesmo nome,
 * para a ordenação das páginas e palavras.
 *
 * @author Matheus Nascimento e Elvis Serafim
 * @since Jul 2018
 * @version 1.0
 */
public class QuickSort {

    /**
     * algoritmo de ordenação quickSort.
     * @param fila Fila a ser ordenada.
     * @param compare Objeto a ser comparado.
     * @throws NullPointerException
     */
    public void quickSort(Queue fila, Comparator compare) throws  NullPointerException{
        try{
            fila.isEmpty();
        }catch(NullPointerException ex){
             throw new NullPointerException();
        }
        if (fila.size() > 1) {
            Comparable pivot = (Comparable) fila.peek();
            Queue left = new LinkedList();
            Queue equal = new LinkedList();
            Queue right = new LinkedList();
            while (!fila.isEmpty()) {
                Comparable next = (Comparable) fila.remove();
                if (compare.compare(next, pivot) < 0) {
                    left.add(next);
                } else if (compare.compare(next, pivot) > 0) {
                    right.add(next);
                } else {
                    equal.add(next);
                }
            }
            quickSort(left, compare);
            quickSort(right, compare);
            fila.addAll(left);
            fila.addAll(equal);
            fila.addAll(right); 
            
            
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Matheus Nascimento
 */
public class QuickSort {

    public void quickSort(Queue fila) {
        if (fila.size() > 1) {
            Comparable pivot = (Comparable) fila.peek();
            Queue left = new LinkedList();
            Queue equal = new LinkedList();
            Queue right = new LinkedList();
            while (!fila.isEmpty()) {
                Comparable next = (Comparable) fila.remove();
                if (next.compareTo(pivot) < 0) {
                    left.add(next);
                } else if (next.compareTo(pivot) > 0) {
                    right.add(next);
                } else {
                    equal.add(next);
                }
            }
            quickSort(left);
            quickSort(right);
            fila.addAll(right); 
            fila.addAll(equal);
            fila.addAll(left);
            
        }
    }
}

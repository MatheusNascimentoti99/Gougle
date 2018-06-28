/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*

    Arvore  baseada na Arvore implementada por Rodrigo Vilar disponivel no GitHub: https://gist.github.com/rodrigovilar
*/
package util;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import model.Palavra;

/**
 *
 * @author Matheus Nascimento
 */
public class Arvore implements Serializable {

    protected No raiz;

    public void inserir(Comparable date) {
        No n = new No(date);
        inserirAVL(this.raiz, n);
    }

    public No getRaiz() {
        return raiz;
    }

    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }

    public void inserirAVL(No aComparar, No aInserir) {

        if (aComparar == null) {
            this.raiz = aInserir;

        } else {

            if (aInserir.getDate().compareTo(aComparar.getDate()) < 0) {

                if (aComparar.getLeft() == null) {
                    aComparar.setLeft(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

                } else {
                    inserirAVL(aComparar.getLeft(), aInserir);
                }

            } else if (aInserir.getDate().compareTo(aComparar.getDate()) > 0) {

                if (aComparar.getRight() == null) {
                    aComparar.setRight(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

                } else {
                    inserirAVL(aComparar.getRight(), aInserir);
                }

            }

        }

    }

    public void verificarBalanceamento(No atual) {
        setBalanceamento(atual);
        int balanceamento = atual.getBalanceamento();

        if (balanceamento == -2) {

            if (altura(atual.getLeft().getRight()) < altura(atual.getLeft().getLeft())) {
                atual = rotacaoDireita(atual);

            } else {
                atual = duplaRotacaoEsquerdaDireita(atual);
            }

        } else if (balanceamento == 2) {

            if (altura(atual.getRight().getLeft()) < altura(atual.getRight().getRight())) {
                atual = rotacaoEsquerda(atual);

            } else {
                atual = duplaRotacaoDireitaEsquerda(atual);
            }
        }

        if (atual.getPai() != null) {
            verificarBalanceamento(atual.getPai());
        } else {
            this.raiz = atual;
        }
    }

    public Comparable remover(Palavra palavra) {
        return removerAVL(this.raiz, palavra);
    }

    private Comparable removerAVL(No atual, Palavra palavra) {
        Comparable removido = null;
        if (atual != null) {

            if (atual.getDate().compareTo(palavra) > 0) {
                removerAVL(atual.getLeft(), palavra);

            } else if (atual.getDate().compareTo(palavra) < 0) {
                removerAVL(atual.getRight(), palavra);

            } else if (atual.getDate().compareTo(palavra) == 0) {
                removido = removerNoEncontrado(atual);
            }
        }
        return removido;
    }

    //Código retirado da aula 09 de Estrutura de Dados do Professora João Rocha.
    public Queue preOrder() {
        Queue fila = new LinkedList();
        Stack stack = new Stack();
        stack.push(raiz);
        while (!stack.isEmpty()) {
            No n = (No) stack.pop();
            fila.add(n.getDate());
            if (n.getRight() != null) {
                stack.push(n.getRight());
            }
            if (n.getLeft() != null) {
                stack.push(n.getLeft());
            }
        }
        return fila;
    }

    public Comparable removerNoEncontrado(No aRemover) {
        No children;
        Comparable temp = aRemover.getDate();
        if (aRemover.getLeft() == null || aRemover.getRight() == null) {

            if (aRemover.getPai() == null) {
                this.raiz = null;
                return temp;
            }
            children = aRemover;

        } else {
            children = sucessor(aRemover);
        }

        No remov;
        if (children.getLeft() != null) {
            remov = children.getLeft();
        } else {
            remov = children.getRight();
        }

        if (remov != null) {
            remov.setPai(children.getPai());
        }

        if (children.getPai() == null) {
            this.raiz = remov;
        } else {
            if (children == children.getPai().getLeft()) {
                children.getPai().setLeft(remov);
            } else {
                children.getPai().setRight(remov);
            }
            verificarBalanceamento(children.getPai());
        }
        return temp;
    }

    public No rotacaoEsquerda(No inicial) {

        No right = inicial.getRight();
        right.setPai(inicial.getPai());

        inicial.setRight(right.getLeft());

        if (inicial.getRight() != null) {
            inicial.getRight().setPai(inicial);
        }

        right.setLeft(inicial);
        inicial.setPai(right);

        if (right.getPai() != null) {

            if (right.getPai().getRight() == inicial) {
                right.getPai().setRight(right);

            } else if (right.getPai().getLeft() == inicial) {
                right.getPai().setLeft(right);
            }
        }

        setBalanceamento(inicial);
        setBalanceamento(right);

        return right;
    }

    public No rotacaoDireita(No inicial) {

        No left = inicial.getLeft();
        left.setPai(inicial.getPai());

        inicial.setLeft(left.getRight());

        if (inicial.getLeft() != null) {
            inicial.getLeft().setPai(inicial);
        }

        left.setRight(inicial);
        inicial.setPai(left);

        if (left.getPai() != null) {

            if (left.getPai().getRight() == inicial) {
                left.getPai().setRight(left);

            } else if (left.getPai().getLeft() == inicial) {
                left.getPai().setLeft(left);
            }
        }

        setBalanceamento(inicial);
        setBalanceamento(left);

        return left;
    }

    public No duplaRotacaoEsquerdaDireita(No inicial) {
        inicial.setLeft(rotacaoEsquerda(inicial.getLeft()));
        return rotacaoDireita(inicial);
    }

    public No duplaRotacaoDireitaEsquerda(No inicial) {
        inicial.setRight(rotacaoDireita(inicial.getRight()));
        return rotacaoEsquerda(inicial);
    }

    public No sucessor(No aux) {
        if (aux.getRight() != null) {
            No r = aux.getRight();
            while (r.getLeft() != null) {
                r = r.getLeft();
            }
            return r;
        } else {
            No suscessor = aux.getPai();
            while (suscessor != null && aux == suscessor.getRight()) {
                aux = suscessor;
                suscessor = aux.getPai();
            }
            return suscessor;
        }
    }

    private int altura(No atual) {
        if (atual == null) {
            return -1;
        }

        if (atual.getLeft() == null && atual.getRight() == null) {
            return 0;

        } else if (atual.getLeft() == null) {
            return 1 + altura(atual.getRight());

        } else if (atual.getRight() == null) {
            return 1 + altura(atual.getLeft());

        } else {
            return 1 + Math.max(altura(atual.getLeft()), altura(atual.getRight()));
        }
    }

    private void setBalanceamento(No no) {
        no.setBalanceamento(altura(no.getRight()) - altura(no.getLeft()));
    }

    public boolean isEmpty() {
        return raiz == null;
    }

}

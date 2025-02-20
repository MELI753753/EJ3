package org.example.model;

import org.example.model.nodes.Node;

public class DynamicQueue implements Queue {

    private Node first;

    @Override
    public int getFirst() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede obtener el primero de una cola vacía");
        }
        return first.getValue();
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void add(int a) {
        if (first == null) {
            first = new Node(a, null);
            return;
        }
        Node candidate = first;
        while (candidate.getNext() != null) {
            candidate = candidate.getNext();
        }
        candidate.setNext(new Node(a, null));
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede desacolar de una cola vacía");
        }
        first = first.getNext();
    }
}

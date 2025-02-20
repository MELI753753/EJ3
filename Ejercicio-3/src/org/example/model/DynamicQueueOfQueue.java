package org.example.model;

import org.example.util.QueueUtil;

public class DynamicQueueOfQueue implements QueueOfQueue {

    private NodeQQ first; // Referencia al primer nodo de la cola de colas

    // Clase interna para representar cada nodo que almacena una Queue
    private static class NodeQQ {
        private Queue value;
        private NodeQQ next;

        public NodeQQ(Queue value, NodeQQ next) {
            this.value = value;
            this.next = next;
        }

        public Queue getValue() {
            return value;
        }

        public NodeQQ getNext() {
            return next;
        }

        public void setNext(NodeQQ next) {
            this.next = next;
        }
    }

    // Métodos básicos

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void add(Queue q) {
        if (isEmpty()) {
            first = new NodeQQ(q, null);
        } else {
            NodeQQ current = first;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(new NodeQQ(q, null));
        }
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede remover de una QueueOfQueue vacía.");
        }
        first = first.getNext();
    }

    @Override
    public Queue getFirst() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede obtener el primero de una QueueOfQueue vacía.");
        }
        return first.getValue();
    }

    // Método 1: concatenate
    @Override
    public QueueOfQueue concatenate(QueueOfQueue... others) {
        DynamicQueueOfQueue result = new DynamicQueueOfQueue();

        // Copiamos las colas de esta instancia
        NodeQQ current = first;
        while (current != null) {
            result.add(QueueUtil.copy(current.getValue()));
            current = current.getNext();
        }

        // Para cada QueueOfQueue adicional, copiamos sus colas
        for (QueueOfQueue qoq : others) {
            if (qoq instanceof DynamicQueueOfQueue) {
                DynamicQueueOfQueue dyn = (DynamicQueueOfQueue) qoq;
                NodeQQ otherCurrent = dyn.first;
                while (otherCurrent != null) {
                    result.add(QueueUtil.copy(otherCurrent.getValue()));
                    otherCurrent = otherCurrent.getNext();
                }
            } else {
                throw new RuntimeException("QueueOfQueue no soportada en concatenate");
            }
        }
        return result;
    }

    // Método 2: flat
    @Override
    public Queue flat() {
        Queue result = new DynamicQueue();
        NodeQQ current = first;
        while (current != null) {
            // Se copia la cola interna para preservar la original
            Queue copyQ = QueueUtil.copy(current.getValue());
            // Transfiere sus elementos a result
            while (!copyQ.isEmpty()) {
                result.add(copyQ.getFirst());
                copyQ.remove();
            }
            current = current.getNext();
        }
        return result;
    }

    // Método 3: reverseWithDepth
    @Override
    public void reverseWithDepth() {
        reverseWithDepthRecursive();
    }

    private void reverseWithDepthRecursive() {
        if (isEmpty()) {
            return;
        }
        // Extraemos la primera cola
        Queue front = getFirst();
        remove();
        // Invertimos recursivamente el resto de la cola de colas
        reverseWithDepthRecursive();
        // Invertimos la cola interna
        reverseQueue(front);
        // Agregamos la cola invertida al final, logrando invertir el orden general
        add(front);
    }

    private void reverseQueue(Queue q) {
        if (q.isEmpty()) {
            return;
        }
        int firstElem = q.getFirst();
        q.remove();
        reverseQueue(q);
        q.add(firstElem);
    }
}

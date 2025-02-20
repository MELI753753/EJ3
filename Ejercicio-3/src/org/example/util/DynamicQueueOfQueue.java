package org.example.util;

import org.example.model.DynamicQueue;
import org.example.model.Queue;
import org.example.model.QueueOfQueue;

public class DynamicQueueOfQueue implements QueueOfQueue {

    private NodeQQ first; // Referencia al primer nodo de la cola de colas

    /**
     * Clase interna para representar cada nodo que almacena una Queue.
     */
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

    // ----- Métodos básicos de la cola de colas -----

    @Override
    public boolean isEmpty() {
        return this.first == null;
    }

    @Override
    public void add(Queue q) {
        if (this.isEmpty()) {
            this.first = new NodeQQ(q, null);
        } else {
            NodeQQ current = this.first;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new NodeQQ(q, null);
        }
    }

    @Override
    public void remove() {
        if (this.isEmpty()) {
            throw new RuntimeException("No se puede remover de una QueueOfQueue vacía.");
        }
        this.first = this.first.next;
    }

    @Override
    public Queue getFirst() {
        if (this.isEmpty()) {
            throw new RuntimeException("No se puede obtener el primero de una QueueOfQueue vacía.");
        }
        return this.first.value;
    }

    // ----- Método 1: concatenate -----

    @Override
    public QueueOfQueue concatenate(QueueOfQueue... others) {
        // Creamos una nueva instancia de DynamicQueueOfQueue para el resultado
        DynamicQueueOfQueue result = new DynamicQueueOfQueue();

        // Copiamos las colas de la instancia actual
        NodeQQ current = this.first;
        while (current != null) {
            // Copiamos la cola para preservar la original
            Queue copyQ = QueueUtil.copy(current.value);
            result.add(copyQ);
            current = current.next;
        }

        // Recorremos cada uno de los QueueOfQueue adicionales
        for (QueueOfQueue qoq : others) {
            if (qoq instanceof DynamicQueueOfQueue) {
                DynamicQueueOfQueue dyn = (DynamicQueueOfQueue) qoq;
                NodeQQ otherCurrent = dyn.first;
                while (otherCurrent != null) {
                    Queue copyQ = QueueUtil.copy(otherCurrent.value);
                    result.add(copyQ);
                    otherCurrent = otherCurrent.next;
                }
            } else {
                throw new RuntimeException("QueueOfQueue no soportada en concatenate");
            }
        }
        return result;
    }

    // ----- Método 2: flat -----

    @Override
    public Queue flat() {
        // Creamos una sola cola para almacenar todos los elementos
        Queue result = new DynamicQueue();  // O puedes usar StaticQueue si lo prefieres

        NodeQQ current = this.first;
        while (current != null) {
            // Copiamos la cola interna para no modificar la original
            Queue copyQ = QueueUtil.copy(current.value);
            // Transferimos todos los elementos de copyQ a result
            while (!copyQ.isEmpty()) {
                result.add(copyQ.getFirst());
                copyQ.remove();
            }
            current = current.next;
        }
        return result;
    }

    // ----- Método 3: reverseWithDepth -----

    @Override
    public void reverseWithDepth() {
        // Método recursivo para invertir la QueueOfQueue y cada una de sus colas
        reverseWithDepthRecursive();
    }

    private void reverseWithDepthRecursive() {
        if (this.isEmpty()) {
            return;
        }
        // Extraemos la primera cola
        Queue front = this.getFirst();
        this.remove();
        // Invertimos recursivamente el resto de la cola de colas
        reverseWithDepthRecursive();
        // Invertimos la cola interna 'front'
        reverseQueue(front);
        // Agregamos la cola invertida al final, logrando así invertir el orden general
        this.add(front);
    }

    /**
     * Método recursivo para invertir una cola de enteros.
     * @param q Cola a invertir.
     */
    private void reverseQueue(Queue q) {
        if (q.isEmpty()) {
            return;
        }
        int first = q.getFirst();
        q.remove();
        reverseQueue(q);
        q.add(first);
    }
}

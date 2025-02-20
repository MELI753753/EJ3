package org.example;

import org.example.model.*;
import org.example.util.QueueUtil;

public class Main {
    public static void main(String[] args) {
        // --- Prueba de DynamicQueue ---
        Queue q1 = new DynamicQueue();
        q1.add(1);
        q1.add(2);
        q1.add(3);
        System.out.println("Contenido de q1:");
        printQueue(q1);

        // --- Prueba de QueueOfQueue ---
        DynamicQueueOfQueue qoq1 = new DynamicQueueOfQueue();
        qoq1.add(q1); // agrega q1

        Queue q2 = new DynamicQueue();
        q2.add(4);
        q2.add(5);
        q2.add(6);
        qoq1.add(q2);

        Queue q3 = new DynamicQueue();
        q3.add(7);
        q3.add(8);
        q3.add(9);
        qoq1.add(q3);

        System.out.println("\nQueueOfQueue original (flat):");
        printQueue(qoq1.flat());

        // --- Prueba de concatenate ---
        DynamicQueueOfQueue qoq2 = new DynamicQueueOfQueue();
        Queue q4 = new DynamicQueue();
        q4.add(10);
        q4.add(11);
        qoq2.add(q4);

        QueueOfQueue concatenado = qoq1.concatenate(qoq2);
        System.out.println("\nQueueOfQueue concatenado (flat):");
        printQueue(concatenado.flat());

        // --- Prueba de reverseWithDepth ---
        qoq1.reverseWithDepth();
        System.out.println("\nQueueOfQueue original invertido (flat):");
        printQueue(qoq1.flat());
    }

    // Método auxiliar para imprimir el contenido de una Queue sin modificarla
    private static void printQueue(Queue q) {
        Queue copy = QueueUtil.copy(q);
        while (!copy.isEmpty()) {
            System.out.print(copy.getFirst() + " ");
            copy.remove();
        }
        System.out.println();
    }
}

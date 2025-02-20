package org.example.util;

import org.example.model.Queue;
import org.example.model.DynamicQueue;

public class QueueUtil {
    public static Queue copy(Queue queue) {
        Queue aux = new DynamicQueue();
        Queue aux2 = new DynamicQueue();

        // Vacía la cola original en dos auxiliares para poder restaurarla
        while (!queue.isEmpty()) {
            aux.add(queue.getFirst());
            aux2.add(queue.getFirst());
            queue.remove();
        }
        // Restaura la cola original
        while (!aux2.isEmpty()) {
            queue.add(aux2.getFirst());
            aux2.remove();
        }
        return aux;
    }
}

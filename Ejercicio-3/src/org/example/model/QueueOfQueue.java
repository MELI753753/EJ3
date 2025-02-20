package org.example.model;

public interface QueueOfQueue {
    boolean isEmpty();
    void add(Queue q);
    void remove();
    Queue getFirst();

    QueueOfQueue concatenate(QueueOfQueue... others);
    Queue flat();
    void reverseWithDepth();
}

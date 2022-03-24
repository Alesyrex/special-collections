package com.efimchick.ifmo.collections;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class MedianQueue extends AbstractQueue<Integer> {

    public static final int DENOMINATOR = 2;
    private Integer[] queue = new Integer[]{};
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer added) {
        if (added == null) {
            throw new IllegalArgumentException();
        }
        Integer[] newQueue = new Integer[queue.length + 1];
        System.arraycopy(queue, 0, newQueue, 0, queue.length);
        newQueue[size] = added;
        queue = newQueue;
        size++;
        setMedian();
        return true;
    }

    private void setMedian() {
        List<Integer> medians = new ArrayList<>();

        Arrays.sort(queue);
        int amountElements = size;

        int leftOfMedian = (amountElements - 1) / DENOMINATOR;
        int rightOfMedian = amountElements - amountElements / DENOMINATOR;

        while (0 <= leftOfMedian || rightOfMedian <= amountElements - 1) {
            if (leftOfMedian + 1 > amountElements - rightOfMedian) {
                medians.add(queue[leftOfMedian]);
                --leftOfMedian;
            } else if (leftOfMedian + 1 < amountElements - rightOfMedian) {
                medians.add(queue[rightOfMedian]);
                ++rightOfMedian;
            } else {
                if (queue[leftOfMedian] <= queue[rightOfMedian]) {
                    medians.add(queue[leftOfMedian]);
                    --leftOfMedian;
                } else {
                    medians.add(queue[rightOfMedian]);
                    ++rightOfMedian;
                }
            }
        }
        queue = medians.toArray(new Integer[queue.length]);
    }

    @Override
    public Integer poll() {
        Integer[] newQueue = new Integer[queue.length - 1];
        size--;
        Integer oldFirst = queue[0];
        System.arraycopy(queue, 1, newQueue, 0, size);
        queue = newQueue;
        return oldFirst;
    }

    @Override
    public Integer peek() {
        return queue[0];
    }

    @Override
    public Iterator<Integer> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Integer> {

        private int currentIndex;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int nextIndex = currentIndex;
            currentIndex++;
            return queue[nextIndex];
        }
    }
}

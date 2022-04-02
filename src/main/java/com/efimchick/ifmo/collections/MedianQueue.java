package com.efimchick.ifmo.collections;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MedianQueue extends AbstractQueue<Integer> {

    public static final int DENOMINATOR = 2;
    private final List<Integer> queueList = new ArrayList<>();

    @Override
    public int size() {
        return queueList.size();
    }

    @Override
    public boolean offer(Integer added) {
        if (added == null) {
            throw new IllegalArgumentException();
        }
        queueList.add(added);
        queueList.sort(Integer::compareTo);
        return true;
    }

    private int getMedian() {
        int median;
        if (queueList.size() % DENOMINATOR == 0) {
            median = queueList.size() / DENOMINATOR - 1;
        } else {
            median = queueList.size() / DENOMINATOR;
        }
        return median;
    }

    @Override
    public Integer poll() {
        return queueList.remove(getMedian());
    }

    @Override
    public Integer peek() {
        return queueList.get(getMedian());
    }

    @Override
    public Iterator<Integer> iterator() {
        return new MedianQueueIterator();
    }

    private class MedianQueueIterator implements Iterator<Integer> {

        private int currentIndex = getMedian();
        private int index;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if ((size() - index) % DENOMINATOR == 0 && index > 0) {
                currentIndex -= index;
            } else {
                currentIndex += index;
            }
            index++;
            return queueList.get(currentIndex);
        }
    }
}

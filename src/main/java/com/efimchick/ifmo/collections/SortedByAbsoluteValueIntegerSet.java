package com.efimchick.ifmo.collections;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedByAbsoluteValueIntegerSet extends AbstractSet<Integer> {

    private Integer[] set = new Integer[]{};
    private final SortedByAbsoluteValueComparator comparator = new SortedByAbsoluteValueComparator();
    private int size;

    @Override
    public boolean add(Integer element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        boolean addElement = false;
        if (!contains(element)) {
            Integer[] newSet = new Integer[size + 1];
            System.arraycopy(set, 0, newSet, 0, size);
            newSet[size] = element;
            set = newSet;
            Arrays.sort(set, comparator);
            size++;
            addElement = true;
        }
        return addElement;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        Integer[] array = c.toArray(new Integer[0]);
        for (Integer x : array) {
            add(x);
        }
        return true;
    }

    @Override
    public boolean remove(Object element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        boolean removeElement = false;
        for (int index = 0; index < size; index++) {
            if (Math.abs((Integer) element) == Math.abs(set[index])) {
                fastRemove(index);
                removeElement = true;
                break;
            }
        }
        return removeElement;
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(set, index + 1, set, index, numMoved);
        }
        size--;
        set = Arrays.copyOf(set, size);
    }

    @Override
    public boolean contains(Object element) {
        boolean findElement = false;
        for (Integer x : set) {
            if (Math.abs(x) == Math.abs((Integer) element)) {
                findElement = true;
                break;
            }
        }
        return findElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new SortedByAbsoluteValueIterator();
    }

    private class SortedByAbsoluteValueIterator implements Iterator<Integer> {

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
            return set[nextIndex];
        }
    }

    private static class SortedByAbsoluteValueComparator implements Comparator<Integer>, Serializable {
        private static final long serialVersionUID = 1;

        @Override
        public int compare(Integer o1, Integer o2) {
            return Math.abs(o1) - Math.abs(o2);
        }
    }
}

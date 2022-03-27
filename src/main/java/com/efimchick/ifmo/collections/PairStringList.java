package com.efimchick.ifmo.collections;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PairStringList extends AbstractList<String> {
    private static final int NUMBER_OF_COPIES = 2;
    private static final int INIT_CAPACITY = 10;
    private static final int ARRAY_EXPANSION_FACTOR = 2;
    private static final String OUT_OF_BOUND_FORMAT = "Index: %d, Size: %d";
    private String[] stringElements = new String[INIT_CAPACITY];
    private int size;

    @Override
    public boolean add(String s) {
        ensureCapacity(NUMBER_OF_COPIES);
        for (int i = 0; i < NUMBER_OF_COPIES; ++i) {
            stringElements[size] = s;
            size++;
        }
        return true;
    }

    @Override
    public void add(int index, String element) {
        rangeCheckAdd(index);
        ensureCapacity(NUMBER_OF_COPIES);
        int indexTemp = index + (index % NUMBER_OF_COPIES);
        System.arraycopy(stringElements, indexTemp, stringElements,
                indexTemp + NUMBER_OF_COPIES, size - indexTemp);
        for (int i = 0; i < NUMBER_OF_COPIES; ++i) {
            stringElements[indexTemp + i] = element;
            size++;
        }
    }

    @Override
    public boolean addAll(Collection<? extends String> strings) {
        String[] tempStringElements = getStringArray(strings);
        int numNew = tempStringElements.length;
        ensureCapacity(size + numNew);
        System.arraycopy(tempStringElements, 0, stringElements, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> strings) {
        rangeCheckAdd(index);

        String[] tempStringElements = getStringArray(strings);
        int numNew = tempStringElements.length;
        ensureCapacity(size + numNew);

        int indexTemp = index + index % NUMBER_OF_COPIES;
        int numMoved = size - indexTemp;
        if (numMoved > 0) {
            System.arraycopy(stringElements, indexTemp, stringElements, indexTemp + numNew,
                    numMoved);
        }
        System.arraycopy(tempStringElements, 0, stringElements, indexTemp, numNew);
        size += numNew;
        return numNew != 0;
    }

    private String[] getStringArray(Collection<? extends String> c) {
        Object[] o = c.toArray();
        String[] tempStringElements = new String[o.length * NUMBER_OF_COPIES];
        int j = 0;
        for (Object value : o) {
            tempStringElements[j] = value.toString();
            tempStringElements[j + 1] = value.toString();
            j += NUMBER_OF_COPIES;
        }
        return tempStringElements;
    }

    private void resize(int length) {
        String[] newStringElements = new String[length];
        System.arraycopy(stringElements, 0, newStringElements, 0, size);
        stringElements = newStringElements;
    }

    private void ensureCapacity(int minCapacity) {
        if (size >= stringElements.length - minCapacity) {
            resize((stringElements.length + minCapacity) * ARRAY_EXPANSION_FACTOR);
        }
    }

    @Override
    public String remove(int index) {
        rangeCheck(index);
        String oldValue = stringElements[index];
        fastRemove(index);

        return oldValue;
    }

    @Override
    public boolean remove(Object o) {
        boolean remove = false;
        for (int index = 0; index < size; index++) {
            if (o.equals(stringElements[index])) {
                fastRemove(index);
                remove = true;
            }
        }
        return remove;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(stringElements, size);
    }

    private void fastRemove(int index) {
        int indexTemp = index - index % NUMBER_OF_COPIES;
        int numMoved = size - indexTemp - NUMBER_OF_COPIES;
        if (numMoved > 0) {
            System.arraycopy(stringElements, indexTemp + NUMBER_OF_COPIES, stringElements, indexTemp,
                    numMoved);
        }
        for (int i = 0; i < NUMBER_OF_COPIES; ++i) {
            --size;
            stringElements[size] = null;
        }
    }

    @Override
    public String get(int index) {
        return stringElements[index];
    }

    @Override
    public String set(int index, String element) {
        rangeCheck(index);
        String oldValue = stringElements[index];
        int indexTemp = index - index % NUMBER_OF_COPIES;
        for (int i = 0; i < NUMBER_OF_COPIES; ++i) {
            stringElements[indexTemp + i] = element;
        }
        return oldValue;
    }

    @Override
    public void clear() {
        stringElements = new String[]{};
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundMsg(index));
        }
    }

    private void rangeCheckAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundMsg(index));
        }
    }

    private String outOfBoundMsg(int index) {
        return String.format(OUT_OF_BOUND_FORMAT, index, size);
    }

    @Override
    public Iterator<String> iterator() {
        return new PairStringListIterator();
    }

    private class PairStringListIterator implements Iterator<String> {

        private int currentIndex;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int nextIndex = currentIndex;
            currentIndex++;
            return stringElements[nextIndex];
        }
    }
}

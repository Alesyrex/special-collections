package com.efimchick.ifmo.collections;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;

class SortedByAbsoluteValueIntegerSet extends AbstractSet<Integer> {

    private final NavigableMap<Integer, Object> map;
    private final Object present = new Object();

    public SortedByAbsoluteValueIntegerSet(NavigableMap<Integer, Object> map) {
        this.map = map;
    }

    public SortedByAbsoluteValueIntegerSet() {
        this(new TreeMap<>(new SortedByAbsoluteValueIntegerSetComparator()));
    }

    @Override
    public boolean contains(Object object) {
        return map.containsKey((Integer) object);
    }

    @Override
    public boolean add(Integer integer) {
        return map.put(integer, present) == null;
    }

    @Override
    public boolean remove(Object object) {
        return map.remove(object) == present;
    }

    @Override
    public Iterator<Integer> iterator() {
        return map.navigableKeySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    private static class SortedByAbsoluteValueIntegerSetComparator implements Comparator<Integer>, Serializable {
        private static final long serialVersionUID = 1;

        @Override
        public int compare(Integer o1, Integer o2) {
            return Math.abs(o1) - Math.abs(o2);
        }
    }
}

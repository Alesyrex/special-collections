package com.efimchick.ifmo.collections;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Consumer;

class SortedByAbsoluteValueIntegerSet extends AbstractSet<Integer> {

    private final NavigableMap<Integer, Object> map;
    private final Object present = new Object();

    public SortedByAbsoluteValueIntegerSet(NavigableMap<Integer, Object> map) {
        this.map = map;
    }

    public SortedByAbsoluteValueIntegerSet() {
        this(new TreeMap<>(new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Math.abs(o1) - Math.abs(o2);
            }
        }));
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
    public void forEach(Consumer<? super Integer> action) {
        super.forEach(action);
    }

    @Override
    public Iterator<Integer> iterator() {
        return map.navigableKeySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }
}

package com.example.model;

import com.example.interfaces.Keyable;
import com.example.interfaces.Operable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Contenedor<T extends Keyable> implements Operable<T> {

    ObservableList<T> contenedor;

    public Contenedor() {
        super();
        this.contenedor = FXCollections.observableArrayList();
    }

    @Override
    public boolean add(T item) {
        if(item == null || contains(item)) return false;
        return contenedor.add(item);
    }

    @Override
    public boolean addAll(List<? extends T> items) {
        boolean changed = false;
        if (items != null) {
            contenedor.addAll(items);
            changed = true;
        }
        return changed;
    }

    public boolean remove(String key) {
        T itemToRemove = null;
        for (T item : contenedor) {
            if (item.getKey().equals(key)) {
                itemToRemove = item;
                break;
            }
        }
        return contenedor.remove(itemToRemove);
    }

    public boolean update(T oldItem, T newItem) {
        boolean changed = false;
        for (T item : contenedor) {
            if (item.getKey().equals(oldItem.getKey())) {
                contenedor.set(getPosition(oldItem), newItem);
                changed = true;
                break;
            }
        }
        return changed;
    }

    @Override
    public boolean remove(T item) {
        return contenedor.remove(item);
    }

    @Override
    public boolean contains(T item) {
        if (item == null) {
            return false;
        }
        return contenedor.stream().anyMatch(e -> e.getKey().equals(item.getKey()));
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < contenedor.size()) {
            return contenedor.get(index);
        }
        return null;
    }

    @Override
    public int getPosition(T item) {
        return contenedor.indexOf(item);
    }

    public int getPosition(String key) {
        for (int i = 0; i < contenedor.size(); i++)
            if (contenedor.get(i).getKey().equals(key))
                return i;
        return -1;
    }

    @Override
    public void sort() {
        contenedor.sort(Comparator.comparing(Keyable::getKey));
    }

    @Override
    public ObservableList<T> getAll() {
        return contenedor;
    }

    @Override
    public boolean isEmpty() {
        return contenedor.isEmpty();
    }
}

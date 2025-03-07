package com.online_shop.project.utilities;

public class CustomPair<K, V> {
    private final K key;
    private final V value;

    public CustomPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
package org.example.c3;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/6/18 22:01
 **/
public interface STTemplateInterface<Key extends Comparable<Key>, Value> extends Iterable<Key> {

    void put(Key key, Value val);

    Value get(Key key);

    void delete(Key key);

    boolean contains(Key key);

    int size();

    boolean isEmpty();
}

package yujin;

public class MyArrayList {
    private Object[] data;
    private int size;

    public MyArrayList() {
        data = new Object[100];
        size = 0;
    }

    public Object get(int i) {
        return data[i];
    }

    public Object set(int i, Object e) {
        Object old = data[i];
        data[i] = e;
        return old;
    }

    public void add(int i, Object e) {
        for (int j = size - 1; j >= i; j--) {
            data[j + 1] = data[j];
        }
        data[i] = e;
        size++;
    }

    public Object remove(int i) {
        Object old = data[i];
        for (int j = i; j < size - 1; j++) {
            data[j] = data[j + 1];
        }
        data[size - 1] = null;
        size--;
        return old;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}


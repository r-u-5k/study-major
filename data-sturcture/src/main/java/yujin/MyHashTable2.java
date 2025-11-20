package yujin;

import java.util.ArrayList;

public class MyHashTable2 {

    private ArrayList bucketArray;
    private int bucketCapacity;
    private float loadFactor;

    MyHashTable2(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    MyHashTable2(int initialCapacity, float threshold) {
        this.bucketCapacity = initialCapacity;
        this.loadFactor = threshold;

        bucketArray = new ArrayList(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            bucketArray.add(new ArrayList());
        }
    }

    public int size() {
        int count = 0;
        for (Object b : bucketArray) {
            ArrayList bucket = (ArrayList) b;
            count += bucket.size();
        }
        return count;
    }

    private int hashFunc(String k) {
        long hash = 0;
        int p = 33;
        for (char c : k.toCharArray()) {
            hash = (hash * p + c) % bucketCapacity;
        }
        return (int) hash;
    }

    private void rehash(int capacity) {
        ArrayList old = bucketArray;

        bucketCapacity = capacity;
        bucketArray = new ArrayList(capacity);
        for (int i = 0; i < capacity; i++) {
            bucketArray.add(new ArrayList());
        }

        for (Object b : old) {
            ArrayList bucket = (ArrayList) b;
            for (Object obj : bucket) {
                StudentInfo s = (StudentInfo) obj;
                put(s.getStudentID(), s.getStudentName());
            }
        }
    }

    public float getLoadFactor() {
        return (float) size() / bucketCapacity;
    }

    public String get(String k) {
        int idx = hashFunc(k);
        ArrayList bucket = (ArrayList) bucketArray.get(idx);
        for (Object obj : bucket) {
            StudentInfo s = (StudentInfo) obj;
            if (s.getStudentID().equals(k)) {
                return s.getStudentName();
            }
        }
        return null;
    }

    public String put(String k, String v) {
        int idx = hashFunc(k);
        ArrayList bucket = (ArrayList) bucketArray.get(idx);

        for (Object obj : bucket) {
            StudentInfo s = (StudentInfo) obj;
            if (s.getStudentID().equals(k)) {
                String old = s.getStudentName();
                s.setStudentName(v);
                return old;
            }
        }

        StudentInfo info = new StudentInfo();
        info.setStudentID(k);
        info.setStudentName(v);
        bucket.add(info);

        float currentLoadFactor = getLoadFactor();
        if (currentLoadFactor >= loadFactor) {
            rehash(bucketCapacity * 2);
        }

        return null;
    }

    public String remove(String k) {
        int idx = hashFunc(k);
        ArrayList bucket = (ArrayList) bucketArray.get(idx);

        for (int i = 0; i < bucket.size(); i++) {
            StudentInfo s = (StudentInfo) bucket.get(i);
            if (s.getStudentID().equals(k)) {
                bucket.remove(i);
                return s.getStudentName();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        MyHashTable2 table = new MyHashTable2(13, 0.9f);

        for (int i = 1; i <= 15; i++) {
            String id = String.format("CS2025%03d", i);
            String name = "Student" + i;
            System.out.println("Putting " + id + ", " + name);
            table.put(id, name);

            System.out.println("Capacity=" + table.bucketArray.size()
                    + ", Size=" + table.size()
                    + ", LoadFactor=" + table.getLoadFactor());

            for (int j = 0; j < table.bucketArray.size(); j++) {
                ArrayList bucket = (ArrayList) table.bucketArray.get(j);
                System.out.print("Bucket " + j + ": ");
                for (Object obj : bucket) {
                    StudentInfo s = (StudentInfo) obj;
                    System.out.print("[" + s.getStudentID() + "," + s.getStudentName() + "] ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}

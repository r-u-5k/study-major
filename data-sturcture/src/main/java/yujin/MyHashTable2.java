package yujin;

import java.util.ArrayList;

class MyHashTable2 {
    private ArrayList bucketArray;
    private int bucketCapacity;
    private int size;
    private float loadFactorThreshold;
    private float loadFactor;

    public MyHashTable2(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public MyHashTable2(int initialCapacity, float loadFactorThreshold) {
        this.bucketCapacity = initialCapacity;
        this.loadFactorThreshold = loadFactorThreshold;
        this.bucketArray = new ArrayList(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            bucketArray.add(new ArrayList());
        }
        this.size = 0;
        this.loadFactor = 0;
    }

    private int hashFunc(String k) {
        int hash = 0;
        int p = 31;
        for (char c : k.toCharArray()) {
            hash = (int) ((hash * (long) p + c) % bucketCapacity);
        }
        return hash;
    }

    private void rehash(int newCapacity) {
        ArrayList oldBuckets = this.bucketArray;
        this.bucketCapacity = newCapacity;
        this.bucketArray = new ArrayList(newCapacity);
        for (int i = 0; i < newCapacity; i++) {
            bucketArray.add(new ArrayList());
        }
        this.size = 0;
        for (Object b : oldBuckets) {
            ArrayList bucket = (ArrayList) b;
            for (Object obj : bucket) {
                StudentInfo s = (StudentInfo) obj;
                put(s.getStudentID(), s.getStudentName());
            }
        }
    }

    public float getLoadFactor() {
        return loadFactor;
    }

    public int size() {
        return size;
    }

    public String get(String k) {
        int index = hashFunc(k);
        ArrayList bucket = (ArrayList) bucketArray.get(index);
        for (Object obj : bucket) {
            StudentInfo s = (StudentInfo) obj;
            if (s.getStudentID().equals(k)) {
                return s.getStudentName();
            }
        }
        return null;
    }

    public String put(String k, String v) {
        int index = hashFunc(k);
        ArrayList bucket = (ArrayList) bucketArray.get(index);
        for (Object obj : bucket) {
            StudentInfo s = (StudentInfo) obj;
            if (s.getStudentID().equals(k)) {
                String old = s.getStudentName();
                s.setStudentName(v);
                return old;
            }
        }
        StudentInfo newInfo = new StudentInfo();
        newInfo.setStudentID(k);
        newInfo.setStudentName(v);
        bucket.add(newInfo);
        size++;
        loadFactor = (float) size / bucketCapacity;
        if (loadFactor >= loadFactorThreshold) {
            rehash(bucketCapacity * 2);
            loadFactor = (float) size / bucketCapacity;
        }
        return null;
    }

    public String remove(String k) {
        int index = hashFunc(k);
        ArrayList bucket = (ArrayList) bucketArray.get(index);
        for (int i = 0; i < bucket.size(); i++) {
            StudentInfo s = (StudentInfo) bucket.get(i);
            if (s.getStudentID().equals(k)) {
                bucket.remove(i);
                size--;
                loadFactor = (float) size / bucketCapacity;
                return s.getStudentName();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        MyHashTable2 ht = new MyHashTable2(13, 0.9f);
        for (int i = 1; i <= 15; i++) {
            String id = String.format("CS2025%03d", i);
            String name = "Student" + i;
            System.out.println("Putting " + id + ", " + name);
            ht.put(id, name);
            System.out.println("Capacity=" + ht.bucketArray.size() + ", Size=" + ht.size() + ", LoadFactor=" + ht.getLoadFactor());
            for (int j = 0; j < ht.bucketArray.size(); j++) {
                ArrayList bucket = (ArrayList) ht.bucketArray.get(j);
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




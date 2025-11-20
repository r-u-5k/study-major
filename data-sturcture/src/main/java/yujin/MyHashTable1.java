package yujin;

import java.util.ArrayList;


public class MyHashTable1 {
    private ArrayList bucketArray;
    private int bucketCapacity;

    MyHashTable1(int initialCapacity) {
        this.bucketCapacity = initialCapacity;
        bucketArray = new ArrayList(initialCapacity);

        for (int i = 0; i < initialCapacity; i++) {
            bucketArray.add(new ArrayList());
        }
    }

    private int hashFunc(String key) {
        int p = 33;
        long hash = 0;

        for (int i = 0; i < key.length(); i++) {
            hash = hash * p + key.charAt(i);
        }

        hash = hash % bucketCapacity;
        return (int) hash;
    }

    public int size() {
        int count = 0;
        for (Object b : bucketArray) {
            ArrayList bucket = (ArrayList) b;
            count += bucket.size();
        }
        return count;
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
        MyHashTable1 ht = new MyHashTable1(13);
        ht.put("EE2025001", "Yujin");
        ht.put("ME2024567", "Taeho");
        ht.put("EE2025123", "Minchan");
        ht.put("CS2025345", "Yijoon");
        ht.put("ME2024987", "Youngbin");
        ht.put("EE2025011", "Sumin");
        ht.put("CS2025234", "Hyunwoo");
        ht.put("ME2025432", "Hyunho");
        ht.put("EE2025122", "Yejoon");
        ht.put("CS2025678", "Jihoon");
    }
}
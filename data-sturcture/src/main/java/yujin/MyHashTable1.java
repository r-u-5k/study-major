package yujin;

import java.util.ArrayList;


class MyHashTable1 {
    private ArrayList bucketArray;
    private int bucketCapacity;
    private int size;

    public MyHashTable1(int initialCapacity) {
        this.bucketCapacity = initialCapacity;
        bucketArray = new ArrayList(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            bucketArray.add(new ArrayList());
        }
        size = 0;
    }

    private int hashFunc(String k) {
        int hash = 0;
        int p = 31;
        for (char c : k.toCharArray()) {
            hash = (int) ((hash * (long) p + c) % bucketCapacity);
        }
        return hash;
    }

    public int size() {
        return size;
    }

    public String get(String k) {
        int index = hashFunc(k);
        ArrayList bucket = (ArrayList) bucketArray.get(index);
        for (int i = 0; i < bucket.size(); i++) {
            StudentInfo s = (StudentInfo) bucket.get(i);
            if (s.getStudentID().equals(k)) {
                return s.getStudentName();
            }
        }
        return null;
    }

    public String put(String k, String v) {
        int index = hashFunc(k);
        ArrayList<StudentInfo> bucket = (ArrayList<StudentInfo>) bucketArray.get(index);

        for (StudentInfo s : bucket) {
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
package com.techapp.james.stickyrecyclerview;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test {
    TreeMap<String, TreeSet<String>> titleMap = new TreeMap<>();

    public void insertOrUpdate(String title, String item) {
        if (titleMap.containsKey(title)) {
            TreeSet<String> set = titleMap.get(title);
            set.add(item);
            titleMap.put(title, set);
        } else {
            TreeSet<String> set = new TreeSet<>();
            set.add(item);
            titleMap.put(title, set);
        }
    }

    protected String getItem(int _pos) {
        int pos = _pos;

        for (String key : titleMap.keySet()) {
            String res = find(key, titleMap.get(key), pos);
            if (res != null) {
                return res;
            }
            pos -= titleMap.get(key).size() + 1;
        }
        throw new RuntimeException("not found");
    }

    public String getTitle(int pos) {
        for (String key : titleMap.keySet()) {
            String res = find(key, titleMap.get(key), pos);
            if (res != null) {
                return key;
            }
            pos -= titleMap.get(key).size() + 1;
        }
        throw new RuntimeException("not found");
    }

    private String find(String key, TreeSet<String> set, int pos) {
        if (pos == 0) {
            return key;
        }
        if (pos > set.size()) {
            return null;
        }
        pos--;
        for (String res : set) {
            if (pos == 0) {
                return res;
            }
            pos--;
        }
        throw new RuntimeException("oops!");
    }

    public boolean isTitle(String key) {
        return titleMap.containsKey(key);
    }

    public int count() {
        int size = 0;
        for (TreeSet<String> item : titleMap.values()) {
            size += item.size() + 1;
        }
        return size;
    }

    public ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<String>();
        for (String title : titleMap.keySet()) {
            titles.add(title);
        }
        return titles;
    }
}

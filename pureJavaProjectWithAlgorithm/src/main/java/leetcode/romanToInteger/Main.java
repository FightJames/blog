package leetcode.romanToInteger;

import java.util.HashMap;
import java.util.Stack;

//https://leetcode.com/problems/roman-to-integer/
public class Main {
    HashMap<Character, Integer> map = new HashMap<Character, Integer>();

    public int romanToInt(String s) {
        Stack<String> tmp = new Stack();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            Integer curNum = map.get(cur);
        }
        return Integer.valueOf(tmp.pop());
    }
}

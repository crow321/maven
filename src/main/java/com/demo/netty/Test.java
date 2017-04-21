package com.demo.netty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crow on 2017/4/13.
 */
public class Test {
    public static void main(String[] args) {
        byte[] bytes = {1, 2, 3, 4, 5};
        String keyID = "keyID";
        byte[] bytes1 = keyID.getBytes();

        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        String t = new String(bytes1);

        String s1 = new String(new String(bytes) + "_" + new String(bytes1));
        String s2 = new String(chars);

        System.out.println("bytes  =" + bytes);
        System.out.println("bytes1 =" + bytes1);
        System.out.println("t =" + t);
        System.out.println("t = keyID ?" + t.equals(keyID));
        System.out.println("s1 =" + s1);
        System.out.println("s2 =" + s2);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("test" + i);
        }
      /*  System.out.println(list.toString());
        for (int j = 0; j < 10; j++) {
            System.out.println(list.get(j));
        }*/
    }
}

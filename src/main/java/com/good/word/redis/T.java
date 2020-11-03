package com.good.word.redis;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class T {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
        }};
        System.out.println(StringUtils.collectionToDelimitedString(list, ","));
    }
}

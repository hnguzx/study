package main.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SearchTest {

    private List<String> list;
    private int size = 0;
    private String result = "12";

    @BeforeEach
    void init() {
        list = new ArrayList<String>();
        list.add("1");
        list.add("12");
        list.add("123");
        list.add("1234");
        list.add("12345");
        //list.add("123456");
        size = list.size();
        log.info("list size: " + size);
    }

    @Test
    void testLine() {

        for (int i = 0; i < size; i++) {
            if (list.get(i).equals(result)) {
                log.info("result:{}", i);
            }
        }
    }

    int binary(List<String> list, int begin, int end) {
        if (begin > end) {
            return -1;
        }
        int mid = (begin + (end - begin)) / 2;
        if (list.get(mid).equals(result)) {
            return mid;
        }
        if (list.get(mid).length() > result.length()) {
            return binary(list, begin, mid);
        } else {
            return binary(list, mid, end);
        }

    }

    @Test
    void testBinary() {
        int binary = binary(list, 0, size - 1);
        log.info("result:{}", binary);
    }


    int insert(List<String> list, int begin, int end) {
        if (begin > end) {
            return -1;
        }
        if (begin == end) {
            if (list.get(begin).length() == result.length()) {
                return begin;
            } else {
                return -1;
            }
        }

        int mid = begin + ((end - begin) / (list.get(end).length() - list.get(begin).length())) * (result.length() - list.get(begin).length());
        if (list.get(mid).length() == result.length()) {
            return mid;
        }
        if (list.get(mid).length() < result.length()) {
            return insert(list, begin, mid - 1);
        } else {
            return insert(list, mid + 1, end);
        }
    }

    @Test
    void testInsert() {
        int insert = insert(list, 0, size - 1);
        log.info("result:{}", insert);
    }
}

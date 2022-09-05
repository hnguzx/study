package main.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.ibatis.ognl.DynamicSubscript.mid;

@Slf4j
public class SortTest {
    private List<String> list;
    private int size = 0;
    private String temp;

    @BeforeEach
    void init() {
        list = new ArrayList<String>();
        list.add("123");
        list.add("1234");
        list.add("1");
        list.add("12");
        list.add("12345");
        list.add("");
        list.add("123456");
        size = list.size();
        log.info("list size: " + size);
    }

    @Test
    void testSort() {
        List<String> collect = list.parallelStream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
        log.info("Collectors: {}", collect);
    }

    @Test
    void testBubble() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (list.get(j).length() >= list.get(j + 1).length()) {
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        log.info("Collectors: {}", list);
    }

    @Test
    void testInsert() {
        int currentIndex;
        for (int i = 1; i < size; i++) {
            currentIndex = i;
            temp = list.get(currentIndex);
            while (currentIndex > 0 && list.get(currentIndex - 1).length() > temp.length()) {
                list.set(currentIndex, list.get(currentIndex - 1));
                currentIndex--;
            }
            if (currentIndex != i) {
                list.set(currentIndex, temp);
            }
        }
        log.info("Collectors: {}", list);
    }

    @Test
    void testSelect() {
        int minIndex = 0;
        for (int i = 0; i < size - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (list.get(j).length() < list.get(minIndex).length()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                temp = list.get(minIndex);
                list.set(minIndex, list.get(i));
                list.set(i, temp);
            }
        }
        log.info("Collectors: {}", list);
    }

    int testFastPart(List<String> list, int left, int right) {
        int midLength = list.get(right).length();
        int l = left, r = right - 1;
        while (true) {
            while (list.get(l).length() < midLength) {
                l++;
            }
            while (r > 0 && list.get(r).length() > midLength) {
                r--;
            }
            if (l >= r) {
                break;
            } else {
                temp = list.get(l);
                list.set(l, list.get(r));
                list.set(r, temp);
                l++;
                r--;
            }
        }
        temp = list.get(l);
        list.set(l, list.get(right));
        list.set(right, temp);

        return l;
    }

    void fast(List<String> list, int left, int right) {
        if (right < left) {
            return;
        }
        int mid = testFastPart(list, left, right);
        fast(list, left, mid - 1);
        fast(list, mid + 1, right);
    }

    @Test
    void testFast() {
        fast(list, 0, size - 1);
        log.info("Collectors: {}", list);
    }

    @Test
    void testShell() {
        int interval = 1;
        while (interval < size / 3) {
            interval = interval * 3 + 1;
        }

        while (interval > 0) {
            for (int i = interval; i < size; i++) {
                temp = list.get(i);
                int j = i;
                while (j > interval - 1 && list.get(j - interval).length() >= temp.length()) {
                    list.set(j, list.get(j - interval));
                    j = j - interval;
                }
                if (j != i) {
                    list.set(j, temp);
                }
            }
            interval = (interval - 1) / 3;
        }
        log.info("Collectors: {}", list);
    }
}

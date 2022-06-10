package main.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.*;

@Slf4j
public class Java8Test {

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @SuppressWarnings(value = {"deprecation", "rawtypes", "unused", "unchecked"})
    public void TestDeprecated() {
        Date date = new Date();
        int date1 = date.getDate();

        Calendar instance = Calendar.getInstance();

        List list = new ArrayList();
        list.add(1);
        Object o = list.get(0);
    }

    @Test
    void testLambda() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        /*list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                log.info("{}", integer);
            }
        });*/

//        list.forEach(integer -> log.info("{}", integer));
        list.forEach(System.out::println);
    }

    @Test
    void testFunction() {
        int compute = compute(2, num -> num * num);
        log.info("function compute:{}", compute);

        Function<Integer, Integer> function1 = item -> item * 2;
        Function<Integer, Integer> function2 = item -> item + 2;

        Integer apply = function1.apply(2);
        log.info("predicate.apply:{}", apply);

        Integer apply1 = function1.compose(function2).apply(2);
        log.info("predicate.compose:{}", apply1);

        Integer apply2 = function1.andThen(function2).apply(2);
        log.info("predicate.andThen:{}", apply2);

//        BiFunction
    }

    public int compute(int i, Function<Integer, Integer> function) {
        Integer apply = function.apply(i);
        return apply;
    }

    @Test
    void testSupplier() {
        Supplier<String> supplier = String::new;
        String s = supplier.get();
    }

    @Test
    void testPredicate() {
        Predicate<String> predicate1 = s -> s.length() > 3;
        Predicate<String> predicate2 = s -> s.length() < 5;
        boolean hello = predicate1.test("hello");
        log.info("predicate.test:{}", hello);


        boolean hello1 = predicate1.negate().test("hello");
        log.info("predicate.negate:{}", hello1);

        boolean hello2 = predicate1.and(predicate2).test("hello");
        log.info("predicate.and:{}", hello2);

        boolean hello3 = predicate1.or(predicate2).test("hello");
        log.info("predicate.or:{}", hello3);
    }

    @Test
    void testConsumer() {
        Consumer<String> consumer = item ->
                log.info("consumer:{}", item);
        consumer.accept("guzhixiong");
    }

    @Test
    void testOptional() {
        String name = "guzhixiong";
        Optional<String> optional = Optional.ofNullable(name);
        optional.ifPresent(s -> log.info("name:{}", s));
        System.out.println(optional.orElseGet(() -> "default"));
    }

    @Test
    void testStream() {
        List<String> list = Arrays.asList("gzx", "lh", "gzx");
        list.parallelStream().filter(item -> item.length() > 2).map(item -> item.toUpperCase()).distinct().forEach(System.out::println);
        list.parallelStream().filter(item -> item.length() > 2).map(String::toUpperCase).distinct().forEach(System.out::println);
    }

}

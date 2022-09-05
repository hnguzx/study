package main.java;

import lombok.extern.slf4j.Slf4j;
import main.model.Customer;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Supplier<Customer> supplier = Customer::new;
        Customer s = supplier.get();
        System.out.println(s.getUsername());
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
        Consumer<String> consumer = item -> log.info("consumer:{}", item);
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
        list.stream().filter(item -> item.length() > 2).map(item -> item.toUpperCase()).distinct().forEach(System.out::println);
        list.parallelStream().filter(item -> item.length() > 2).map(String::toUpperCase).distinct().forEach(System.out::println);

        //同时获取最大 最小 平均值等信息
        List<Integer> list1 = Arrays.asList(1, 3, 5, 7, 9, 11);
        IntSummaryStatistics statistics = list1.stream().filter(integer -> integer > 2).mapToInt(i -> i * 2).skip(2).limit(2).summaryStatistics();
        System.out.println(statistics.getMax());//18
        System.out.println(statistics.getMin());//14
        System.out.println(statistics.getAverage());//16
    }

    @Test
    void testDate() {
//        Zoned 时区
//        DateTimeFormatter 格式化
//        Period 日期间隔
//        Duration 时间间隔
        // date LocalDate
        LocalDate now = LocalDate.now();
        log.info("now:{}", now);
        int year = now.getYear();
        Month month = now.getMonth();
        int monthValue = month.getValue();
        int dayOfMonth = now.getDayOfMonth();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int weekValue = dayOfWeek.getValue();
        log.info("year:{},month:{},dayOfMonth:{},dayOfWeek:{}", year, monthValue, dayOfMonth, weekValue);

        LocalDate localDate = now.plusDays(6L);
        log.info("格式化date：{}", localDate.format(DateTimeFormatter.BASIC_ISO_DATE));

        // time LocalTime LocalDateTime
        LocalTime time = LocalTime.now();
        log.info("time:{}", time.toString());
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        log.info("hour:{},minute:{},second:{}", hour, minute, second);
        log.info("格式化time：{}", time.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

        LocalDateTime dateTime = LocalDateTime.now().plusSeconds(10);
        log.info("dateTime:{}", dateTime);
        log.info("格式化dateTime：{}", dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        log.info("格式化dateTime：{}", dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));

        LocalDate parse = LocalDate.parse("2021-06-12");
        log.info("after：{}", parse.isAfter(now));
        log.info("after：{}", now.isAfter(parse));
        log.info("withYear:{}", now.withYear(2025));
        log.info("withMonth:{}", now.withMonth(4));
        log.info("withDayOfYear:{}", now.withDayOfYear(78));

        Date date = new Date();
        LocalDateTime dateTime1 = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        log.info("date to localDateTime:{}", dateTime1);
        Calendar calendar = Calendar.getInstance();
        LocalDateTime dateTime2 = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        log.info("calendar to localDateTime:{}", dateTime2);

        Date from = Date.from(dateTime1.atZone(ZoneId.systemDefault()).toInstant());
        GregorianCalendar from1 = GregorianCalendar.from(ZonedDateTime.of(dateTime1, ZoneId.systemDefault()));
        log.info("localDateTime to date:{}", from);
        log.info("localDateTime to calendar:{}", from1.getTime());

    }

    @Test
    void testBase64() {
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();

        byte[] encode = encoder.encode("guzhixiong".getBytes());
        byte[] decode = decoder.decode(encode);
        log.info("decode:{}", new String(decode, StandardCharsets.UTF_8));
    }

    @Test
    void testLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        Date endTime = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        log.info("now dateTime:{}", endTime);

        Date starTime = Date.from(now.plusHours(-1).atZone(ZoneId.systemDefault()).toInstant());
        log.info("one hours before dateTime:{}", starTime);
    }

}

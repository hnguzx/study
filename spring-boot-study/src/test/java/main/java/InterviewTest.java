package main.java;

import lombok.extern.slf4j.Slf4j;
import main.service.impl.CustomerServiceImpl;
import main.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Math.*;

@Slf4j
public class InterviewTest {

    @Test
    public void testString2() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        int a = 200;
        int b = 200;
        log.info("a==b?{}", a == b);
        Integer c = 200;
        Integer d = 200;
        log.info("c==d?{}", c == d);
        Assertions.assertEquals(a, b);
        Assertions.assertEquals(c, d);


        List list = new ArrayList();
        list.add(1);

        Object[] objects = list.toArray();
        String s = list.toString();
        log.info("obj:{},s:{}", objects, s);

        List list1 = new LinkedList();
        list1.add(2);
        list1.get(0);

        Map map1 = new HashMap();

        map1.containsKey(1);

        Map<Object, Object> hashtable = new Hashtable<>();
        hashtable.containsKey(2);

        Collections.sort(list);

        sin(2.3);

        byte b1 = 127;
        byte b2 = 127;

//        b2 = b1+b2;
        b2 += b1;

        short var1 = 1;
//        var1 = var1 + 1;

        Class<?> aClass = Class.forName("main.service.impl.CustomerServiceImpl");
        Field[] fields = aClass.getFields();
        Method[] methods = aClass.getMethods();
        Field declaredField = aClass.getDeclaredField("");
        Method declaredMethod = aClass.getDeclaredMethod("", null);
        Constructor<?> constructor = aClass.getConstructor(null);
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        CustomerServiceImpl o = (CustomerServiceImpl) constructor.newInstance(null);

        Class<JsonUtil> jsonUtilClass = JsonUtil.class;

        ArrayList arrayList = new ArrayList();
        arrayList.ensureCapacity(20);


    }

    private final StringBuilder finalString = new StringBuilder("guzx");

    class Inner extends InterviewTest {
        public Inner() {
            super();
        }
    }

    @Test
    public void testFinal() {
        log.info(finalString.toString());
        StringBuilder s = finalString;
        s = s.append("@qq.com");
        log.info(finalString.toString());
        log.info(s.toString());

        String a = "1asd";
        String b = "1asd";
        b.equals(a);
        log.info("a==b?{}", a == b);
    }

    @Test
    public void testFor() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 1) {
                    continue;
                }
                log.info("i:{},j:{}", i, j);
            }
        }
    }

    @Test
    public void testValue() {
        StringBuilder a = new StringBuilder("guzx");
        log.info("a:{}", a.toString());
        StringBuilder b = add(a);
        log.info("a:{},b:{}", a, b);
    }

    public StringBuilder add(StringBuilder a) {
        StringBuilder b = new StringBuilder();
        a = new StringBuilder();
        b = a.append("111");
        b = null;
        return b;
    }

    @Test
    public void testIO() {
        /*FileReader fileReader = new FileReader();
        FileWriter fileWriter = new FileWriter();

        FileInputStream fileInputStream = new FileInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream();

        Class<FileInputStream> fileInputStreamClass = FileInputStream.class;*/
    }

    @Test
    public void testChar() throws ClassNotFoundException {
        char a = 'a';
        int i = a + 12;
        log.info("i:{}", i);

        char[] chars = {'a', 'b'};
        int length = chars.length;
        String s = "123";
        int length1 = s.length();

        List<char[]> chars1 = Arrays.asList(chars);
        Object[] objects = chars1.toArray();

        List list = new ArrayList();
        ListIterator listIterator = list.listIterator();
        list.add(1);

        list.add(2.3);
        list.add(false);
        log.info(list.toString());
        for (Object item : list) {
            log.info("取出顺序：{}", item.toString());
        }

        Object[] objects1 = {1, 2.3f, false};
        for (int j = 0; j < objects1.length; j++) {
            log.info("数组取出顺序：{}", objects1[j]);
        }

//        ClassLoader loader = ClassLoader.getSystemClassLoader();
//        loader.loadClass("");

        Map map = new HashMap();

    }
}

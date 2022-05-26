package main.java;

import lombok.extern.slf4j.Slf4j;
import main.service.impl.CustomerServiceImpl;
import main.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
        b2+=b1;

        short var1 = 1;
//        var1 = var1 + 1;

        Class<?> aClass = Class.forName("main.service.impl.CustomerServiceImpl");
        Field[] fields = aClass.getFields();
        Method[] methods = aClass.getMethods();
        Field declaredField = aClass.getDeclaredField("");
        Method declaredMethod = aClass.getDeclaredMethod("",null);
        Constructor<?> constructor = aClass.getConstructor(null);
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        CustomerServiceImpl o = (CustomerServiceImpl) constructor.newInstance(null);

        Class<JsonUtil> jsonUtilClass = JsonUtil.class;

        ArrayList arrayList = new ArrayList();
        arrayList.ensureCapacity(20);


    }
}

package main.java;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import main.model.Customer;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class JavaTest {

    @Test
    public void testStringSplit() {
        List<String> strings = splitAddress("FLAT/RM B3 3/F TAI TAK IND BUILDING 2-12 KWAI FAT ROAD KWAI CHUNG NT");
        for (String s : strings) {
            System.out.println(s.length());
        }
        System.out.println(strings.toString());
    }

    @Test
    public void testStringAndByteArray() {

        String s1 = "hello world";
        byte[] bytes1 = s1.getBytes();

        // 实际调用 bytes1.toString()
        System.out.println(bytes1);
        String s = bytes1.toString();

        // 同样调用 bytes1.toString()
        System.out.println(String.valueOf(bytes1));

        // Constructs a new String by decoding the specified array of bytes
        // 通过方法注释可以看到，该方法是使用字节数组构造一个新的字符串
        String s2 = new String(bytes1);
        System.out.println(s2);
        System.out.println("s1与s2是否为同一个对象：" + (s1 == s2));

        // 指定编码
        byte[] bytes2 = s1.getBytes(StandardCharsets.UTF_8);
        System.out.println(new String(bytes2, StandardCharsets.UTF_8));
    }

    public List<String> splitAddress(String address) {
        List<String> adds = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String[] arr = address.split(" ");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length() >= 35) {
                sb.append(arr[i].substring(0, 34) + " ");
            } else {
                sb.append(arr[i] + " ");
            }

            if (i + 1 < arr.length && sb.length() + arr[i + 1].length() >= 35) {
                adds.add(sb.toString());
                sb.delete(0, sb.length());
            }
        }
        if (sb.length() > 35) {
            adds.add(sb.toString().substring(0, 35));
        } else {
            adds.add(sb.toString());
        }
        return adds;
    }

    @Test
    public void testList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        System.out.println(list.toString());
    }

    @Test
    public void testDom() throws ParserConfigurationException, IOException, SAXException {
        String domString = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><TCPIPMsg><MsgHead><serviceId>10000003</serviceId><serviceChannel/><serviceFlowNo>WY2021122410000001</serviceFlowNo><serviceTime>20211224160116.685</serviceTime><ReturnCode>SUC0000</ReturnCode><ErrorMessage><![CDATA[success]]></ErrorMessage></MsgHead><MsgBody><groupNo>1000000062</groupNo><state>1</state></MsgBody></TCPIPMsg>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        log.info(factory.toString());
        DocumentBuilder builder = factory.newDocumentBuilder();
        log.info(builder.toString());
        Document document = builder.parse(new ByteArrayInputStream(domString.getBytes(StandardCharsets.UTF_8)));
        log.info("当前xml文档中的子节点有：{}", document.getChildNodes().getLength());
        Node item = document.getChildNodes().item(0);
        log.info("二级子节点有：{}", item.getChildNodes().getLength());
        Node item1 = item.getChildNodes().item(1);
        NodeList childNodes = item1.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            log.info("具体返回数据是：{}", childNodes.item(i).getTextContent());
        }
    }

    private static Integer CI_Number = 1;
    private static Integer CP_Number = 1;

    @Test
    public void test() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Integer.toString(CI_Number++));
        }
    }

    @Test
    public void testList2() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        list.add("789");
        list.add(0, "guzhixiong");
        log.info(list.toString());

    }

    @Test
    public void testString() {
        log.info("pDf".toLowerCase());
    }

    private Map<String, Integer> map = new HashMap<String, Integer>() {
        {
            put("guzx", 25);
            put("lh", 24);
        }
    };

    @Test
    public void testMap() {
        String name = "guzx";
        log.info("获取到的age:{}", map.get(name));
    }

    @Test
    public void testIndex() {
//        String fileName = "guzxpng.";
//        log.info("文件名下标啊：{}",fileName.lastIndexOf("."));
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("guzx", 0);
//        Integer int
        int i = map1.get("123");
        log.info("取值为：{}", i);
    }

    @Test
    void testIf() {
        int i = 10;
        if (i > 1) {
            log.info("1");
        } else if (i > 2) {
            log.info("2");
        } else if (i > 3) {
            log.info("3");
        } else if (i > 10) {
            log.info("10");

        } else {
            log.info("大于10");
        }
    }

    @Test
    void testList3() {
        List<Customer> list = new ArrayList<>();
        Customer customer1 = new Customer(1, "guzx", "123");
        Customer customer2 = new Customer(2, "guzx2", "12322");

        list.add(customer1);
        list.add(customer2);
        log.info("before change list:{}", list.toString());
        for (Customer customer : list) {
            customer.setUserName("123");
        }
        log.info("after change list:{}", list.toString());
    }

    @Test
    void testCompareTo() {
        int i = "1.2".compareTo("3.4");
        log.info("compareTo:{}", i);
    }

    @Test
    void testString2() {
        //String format = String.format("%,.2f", 123456.789);
        //log.info("format:{}", format);
        String colorStr = "1234567";
        colorStr.replaceAll("#", "1");
        if (colorStr.length() > 5) {
            log.info("colorStr长度大于五");
            colorStr = colorStr.substring(colorStr.length() - 6);
        }
        log.info("colorStr:{}", colorStr);
    }

    @Test
    void testObject2() {
        Customer customer = new Customer();
        setCustomer(customer);
        log.info("setCustomer:{}", customer);
    }

    void setCustomer(Customer customer) {
        customer.setId(1);
        customer.setUserName("name");
        customer.setPassWord("123456");
    }

    @Test
    void testString3() {
        String join = String.join(".", "guzx", "lianghong");
        log.info("join:{}", join);

        log.info("join length:{},join last 4 char:{}", join.length(), join.substring(join.length() - 4));
    }

    @Test
    void testListOrder() {
        List<Customer> list = new ArrayList<Customer>();
        Customer customer1 = new Customer(2, "guzx2", "123");
        Customer customer2 = new Customer(3, "guzx3", "123");
        Customer customer3 = new Customer(1, "guzx1", "123");
        list.add(customer1);
        list.add(customer2);
        list.add(customer3);
        log.info("List order:{}", list);
        list.sort(Comparator.comparingInt(Customer::getId));
        log.info("List order:{}", list);
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        log.info("this is:{}", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Test
    void stringToNumber() {
        String number = "4.7.0";
        String substring = number.substring(0, 3);
        log.info("version is :{}", substring);
        if (Float.parseFloat(substring) < 4.7) {
            log.info("小于4.7");
        } else {
            log.info("不小于4.7");
        }
        String link = "fintech://insightbank.mi.com/transferIn";
        String[] split = link.split("/");
        log.info("type:{}", split[3]);
    }

    @Test
    void stringBuild() {
        StringBuilder s = new StringBuilder();
        String name = null;
        s.append(name);
        s.append("123");
        log.info("s:{}", s.toString());
        s.setLength(0);
        log.info("s:{}", s.toString());
    }

    @Test
    void floatAndDouble() {
        float a = 0.5f;
        double b = 0.5;
        log.info("a < b : ?{}", a < b);
        log.info("a > b : ?{}", a > b);
        log.info("a == b : ?{}", a == b);


    }

    @Test
    void testGson() {
        String s = new Gson().toJson(null);
        log.info("s : " + s);

        List<Customer> customers = Arrays.asList(new Customer(1, "username", "password"));
        log.info("customers : " + customers);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(5, -1);

        log.info("data time :{}", c.getTime());

        Customer customer = new Customer(1, "username", "password");
        String simpleName = customer.getClass().getSimpleName();
        log.info("simple name : " + simpleName + " date:{}", new Date().getDate());
    }

    @Test
    void testDate() {
        boolean i = false;
        testGson(i);
    }

    void testGson(Object o) {
        log.info("{}", o.getClass() == Boolean.class);
        log.info("{}", o.getClass() == Boolean.TYPE);
    }

    @Test
    void testMp3() throws IOException {
        // 输入音频
        File sourceFile = new File("D:\\KuGou\\弦子 - 舍不得.mp3");
        FileInputStream sourceStream = new FileInputStream(sourceFile);
        long fileSize = sourceFile.length();
        // 4,703,458
        long tag = fileSize / 1024 / 5;
        log.info("file size : {}, all adv times:{}", fileSize, tag);
        File advFile = new File(("D:\\KuGou\\筠子 - 立秋.mp3"));
        FileInputStream targetStream = new FileInputStream(advFile);

        BufferedInputStream music1 = new BufferedInputStream(sourceStream);
        BufferedInputStream music2 = new BufferedInputStream(targetStream);
        // 输出音频
        File resultFile = new File(("D:\\KuGou\\test.mp3"));
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile, true);
        BufferedOutputStream music3 = new BufferedOutputStream(fileOutputStream);

        byte[] bytes = new byte[1024];
        int sourceLength, advLength;
        int i = 0;
        while ((sourceLength = music1.read(bytes)) != -1) {
            // 插入广告
            if (i == tag || i == 0) {
                while ((advLength = music2.read(bytes)) != -1) {
                    i++;
                    music3.write(bytes, 0, advLength);
                    music3.write(bytes, 0, sourceLength);
                    music3.flush();
                }
            } else {
                i++;
                music3.write(bytes, 0, sourceLength);
                music3.flush();
            }

        }

        log.info("一共读取了{}次", i);

        music1.close();
        music2.close();
        music3.close();
    }

    @Test
    void testAac2Mp3() throws EncoderException {
        File advFile = new File(("D:\\KuGou\\adv.aac"));
        MultimediaObject music1 = new MultimediaObject(advFile);
        File targetFile = new File("D:\\KuGou\\adv.mp3");
        AudioAttributes audioAttributes = new AudioAttributes();
        Encoder encoder = new Encoder();
        audioAttributes.setCodec("libmp3lame");
        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setOutputFormat("mp3");
        encodingAttributes.setAudioAttributes(audioAttributes);
        encoder.encode(music1, targetFile, encodingAttributes);
    }

    @Test
    void testMusic2() throws IOException, InterruptedException {
        File sourceFile = new File("D:\\KuGou\\筠子-立秋.mp3");
        File advFile = new File("D:\\KuGou\\watermark.mp3");
        File targetFile = new File("D:\\KuGou\\result.mp3");

        DefaultFFMPEGLocator ffmpegLocator = new DefaultFFMPEGLocator();
        ProcessWrapper executor = ffmpegLocator.createExecutor();
        // 添加文件
        executor.addArgument("-i");
        executor.addArgument(sourceFile.getAbsolutePath());
        executor.addArgument("-i");
        executor.addArgument(advFile.getAbsolutePath());

        executor.addArgument("-filter_complex");
        executor.addArgument("[0:0] [1:0] concat=n=2:v=0:a=1 [a]");
        executor.addArgument("-map");
        executor.addArgument("[a]");

        executor.addArgument(targetFile.getAbsolutePath());
        BufferedReader br = null;
        try {
            executor.execute();
            br = new BufferedReader(new InputStreamReader(executor.getErrorStream()));
            String line;
            while ((line = br.readLine()) != null) {
                //输出处理过程中的日志（辅助观察处理过程）
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testUnderLine() {
        String aipMaxAmt6 = humpToUnderline("nameList123");
        log.info("underline: " + aipMaxAmt6);
    }

    public static String humpToUnderline(String str) {
        String regex = "([A-Z])";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group();
            str = str.replaceAll(target, "_" + target.toLowerCase());
        }
        return str;
    }

    private static Map<Class, Map<String, String>> entity2DB = new ConcurrentHashMap<Class, Map<String, String>>() {
        {
            put(Customer.class, new ConcurrentHashMap<>() {
                {
                    put("review2ndRejectReason", "review_2nd_reject_reason");
                    put("passWord", "passWord222");
                }
            });
        }
    };

    public static List<String> handlerClass(Class entityClass) {

        Field[] declaredFields = entityClass.getDeclaredFields();
        List<String> result = Arrays.stream(declaredFields)
                .map(Field::getName)
                .collect(Collectors.toList());

        Map<String, String> fieldAndDB = entity2DB.get(entityClass);

        if (entity2DB.containsKey(entityClass) && fieldAndDB.keySet().size() != declaredFields.length) {
            for (String item : result) {
                if (!fieldAndDB.containsKey(item)) {
                    fieldAndDB.put(item, humpToUnderline(item));
                }
            }
        } else {
            fieldAndDB = new HashMap<>();
            for (String item : result) {
                fieldAndDB.put(item, humpToUnderline(item));
            }
            entity2DB.put(entityClass, fieldAndDB);
        }
        return result;
    }

    @Test
    void testSet() {
        List<String> strings = handlerClass(Customer.class);
        log.info("Customer fields: " + strings);
        log.info("entity2DB: " + entity2DB);
    }

    @Test
    void testSb() {
        List<Customer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Customer customer = new Customer();
            customer.setId((int) (Math.random() * 100000));
            customer.setUserName(UUID.randomUUID().toString().substring(0, 10));
            customer.setPassWord(UUID.randomUUID().toString().substring(0, 10));
            list.add(customer);
        }


        for (int i = 0; i < 1000; i++) {
            Set<String> password = new HashSet<>();
            Set<String> username = new HashSet<>();
            list.parallelStream().forEach(item -> {
                password.add(item.getPassWord());
                username.add(item.getUserName());
            });
            //if(id.size() == 0 || username.size() == 0) {
            //    log.info("id size:{},username size:{}", id.size(), username.size());
            //}
            log.info("username size:{},id size:{}", new ArrayList<>(username).size(), new ArrayList<>(password).size());
        }

    }

}

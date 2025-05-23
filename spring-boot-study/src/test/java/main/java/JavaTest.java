package main.java;

import cn.hutool.core.date.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import main.model.Customer;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
//import org.pentaho.di.core.KettleEnvironment;
//import org.pentaho.di.core.database.DatabaseMeta;
//import org.pentaho.di.core.exception.KettleException;
//import org.pentaho.di.core.plugins.PluginFolder;
//import org.pentaho.di.core.plugins.StepPluginType;
//import org.pentaho.di.core.util.EnvUtil;
//import org.pentaho.di.repository.ObjectId;
//import org.pentaho.di.repository.RepositoryDirectoryInterface;
//import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
//import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
//import org.pentaho.di.trans.Trans;
//import org.pentaho.di.trans.TransMeta;
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

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.catalina.util.ConcurrentDateFormat.GMT;

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
        list.add(null);
        list.add(0, "guzhixiong");
        log.info(list.toString());

    }

    @Test
    public void testString() {
//        log.info("pDf".toLowerCase());
//        String test = "123456";
//        String substring = test.substring(5);
//        log.info("substring = {\"name\":{}}", substring);
//        Gson gson = new GsonBuilder().create();
        Map<String, String> map = new HashMap<>();
        map.put("name", null);
//        String json = gson.toJson(map);
//
//        JsonObject object = gson.fromJson(json, JsonObject.class);
//
//        JsonElement element = object.get("name");
//        String name = element.getAsString();
//        String[] s = name.split(" ");
//        log.info("name = {}", s.length);
        log.info("name:{}", map.get("name"));


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

        List<Customer> userByUserId = new ArrayList<>();
        userByUserId.add(new Customer(25, "lh", "123"));
        userByUserId.add(new Customer(26, "gzx", "456"));
        userByUserId.add(new Customer(27, "gzx", "456"));
        if (CollectionUtils.isNotEmpty(userByUserId)) {
            Map<String, String> registerMap = userByUserId.stream().collect(Collectors.toMap(Customer::getUserName, Customer::getPassWord));
            log.info("registerMap:{}", registerMap);
        }
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
    void testDate() throws ParseException {
//        boolean i = false;
//        testGson(i);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        log.info("毫秒值：{}", format.parse("2023-01-18").getTime());
        Date date = new Date();
        int dateDate = date.getDate();
        log.info("当前日期：{}", dateDate);
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
//        executor.addArgument("-ss");
//        executor.addArgument("00:00:02");
        executor.addArgument("-i");
        executor.addArgument(advFile.getAbsolutePath());

        executor.addArgument("-filter_complex");
        executor.addArgument("[0:0] [1:0] concat=n=2:v=0:a=1 [a]");
        executor.addArgument("-map");
        executor.addArgument("[a]");
        executor.addArgument("-f");
        executor.addArgument("mp3");
        executor.addArgument("-y");
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

    @Test
    void testWidthAndHeight() {
        String widthStr = "392.456";
        String heightStr = "856.214";
        String[] widths = widthStr.split("\\.");
        double width = Double.parseDouble(widths[0]);
        String[] heights = heightStr.split("\\.");
        double height = Double.parseDouble(heights[0]);
        double v = width / height;
        if ("0.45".equals(String.format("%#,.2f", v)) || "0.46".equals(String.format("%#,.2f", v))) {
            log.info("大于或等于");
        } else {
            log.info("小于");
        }

    }

    @Test
    void testMath() {
        double rate = 0.14;
        Double v = rate * 10000 / 365;
        log.info("result : {}", Math.floor(v));
        log.info("result : {}", v.intValue());

        int i = 123456;
//        double j = i * 1.0 / 100;
        String j = "123456.789";
        log.info("money : {}", String.format("%#,.2f", Double.parseDouble(j)));

        BigDecimal realRate = BigDecimal.valueOf(0.14);

        BigDecimal interest = realRate.multiply(new BigDecimal("10000")).divide(new BigDecimal("365"), 0, BigDecimal.ROUND_DOWN);
        log.info("interest : {}", interest);
    }

    @Test
    void testSort() {
        Customer c1 = new Customer(1, "123", "987");
        Customer c2 = new Customer(2, "456", "456");
        Customer c3 = new Customer(3, "789", "123");

        List<Customer> customers = new ArrayList<Customer>();
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);

        Optional<Customer> first = customers.stream().max(Comparator.comparingInt(Customer::getId));
//                Math.toIntExact(Long.parseLong(item1.getUserName()) - Long.parseLong(item2.getUserName())));

        log.info("max username:{}", first.get());
    }

    @Test
    void testHuo() throws ParseException {
//        log.info("||:{}", 1 > 0 || 2 / 0 == 1);
        DateFormat oldformat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
//        newFormat.setTimeZone(CST);
//        long l = format.parse("2023-01-08").getTime() - format.parse("2030-01-01").getTime();
//
//        log.info("result:{}", l);

        List<String> date = new ArrayList<>();
        date.add("2023-01-08");
        date.add("2030-01-01");

        Optional<String> min = date.stream().min((item1, item2) -> {
            try {
                return oldformat.parse(item1).getTime() - oldformat.parse(item2).getTime() >= 0 ? 1 : -1;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("min date:{}", min.get());

        String formatDate = "2022-12-25";

        String dateString = newFormat.format(oldformat.parse(formatDate));
        log.info("max date:{}", dateString);

        String dateString2 = formatDate(oldformat.parse(formatDate), "dd/MM/yyyy");
        log.info("max date2:{}", dateString2);
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        } else if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);
            formatter.setTimeZone(GMT);
            return formatter.format(date);
        }
    }

    @Test
    void testImage() {
        File file = new File("C:\\Users\\25446\\Desktop\\eep check\\demo.pdf");
        try {
            // 尝试读取图片信息
            BufferedImage read = ImageIO.read(file);

            // 如果能够读取到图片信息，说明该文件是图片
            if (read != null) {
                log.info("is image");
            } else {
                log.info("not image");
            }
        } catch (IOException e) {
            // 如果读取失败，说明该文件不是图片
            log.info("not image file");
        }
    }

    @Test
    public void getBeginningOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        System.out.println(time);
    }

    @Test
    public void testEnDate() {
//        String inputDate1 = "20210101";
//        String inputDate2 = "20210202";
//        String inputDate3 = "20210303";
//        String inputDate4 = "20210404";
//        String inputDate5 = "20210505";
//        String inputDate6 = "20210606";
//        String inputDate7 = "20210707";
//        String inputDate8 = "20210808";
//        String inputDate9 = "20210909";
//        String inputDate10 = "20211010";
//        String inputDate11 = "20211111";
//        String inputDate12 = "20211212";
//        System.out.println("en Date:" + formatEnDate(inputDate1));
//        System.out.println("en Date:" + formatEnDate(inputDate2));
//        System.out.println("en Date:" + formatEnDate(inputDate3));
//        System.out.println("en Date:" + formatEnDate(inputDate4));
//        System.out.println("en Date:" + formatEnDate(inputDate5));
//        System.out.println("en Date:" + formatEnDate(inputDate6));
//        System.out.println("en Date:" + formatEnDate(inputDate7));
//        System.out.println("en Date:" + formatEnDate(inputDate8));
//        System.out.println("en Date:" + formatEnDate(inputDate9));
//        System.out.println("en Date:" + formatEnDate(inputDate10));
//        System.out.println("en Date:" + formatEnDate(inputDate11));
//        System.out.println("en Date:" + formatEnDate(inputDate12));
        System.out.println("tt  en Date:" + formatEnDate("20290515"));
    }

    public String formatEnDate(String inputDate) {
        // 定义输入日期的格式
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

        // 定义目标日期的格式
        SimpleDateFormat outputFormatter = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);

        try {
            // 解析输入日期为 Date 对象
            Date date = inputFormatter.parse(String.valueOf(inputDate));

            // 格式化日期为目标格式
            return outputFormatter.format(date);
        } catch (ParseException e) {
            log.error("CoreBankDataUtils.formatEnDate exception:", e);
        }
        return null;
    }

    @Test
    public void testFind() {
        int[] array = {10, 8, 6, 4, 2}; // 降序数组
        int target = 0; // 指定的值

        int closestValue = findClosestValue2(array, target);
        System.out.println("Closest value: " + closestValue);
    }

    public static int findClosestValue(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int closestValue = -1; // 初始化为一个特定的标识，如-1

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                return array[mid];
            } else if (array[mid] > target) {
                closestValue = array[mid]; // 更新最接近的值
                left = mid + 1;
            } else {
                closestValue = array[mid]; // 更新最接近的值
                right = mid - 1;
            }
            if (array[right] > target) {
                break;
            }
        }

        return closestValue;
    }

    public static int findClosestValue2(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int closestValue = -1; // 初始化为一个特定的标识，如-1

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                return array[mid];
            } else if (array[mid] > target) {
                left = mid + 1;
            } else {
                closestValue = array[mid]; // 更新最接近的值
                right = mid - 1;
            }
        }
        return closestValue;
    }

    @Test
    public void test2() {
        Pattern pattern = Pattern.compile("(?:出境|入境).+?\\d{4}-\\d{2}-\\d{2}");
        String demo = "入境 2023-10-16 往来港澳通行证 CA3271602 测试口岸";
        Matcher matcher = pattern.matcher(demo);
        System.out.println("是否匹配：" + matcher.find());
    }

    @Test
    public void testChar() {
        String demo = "12345678910";
        char c = demo.charAt(9);
        System.out.println("获取到的字符是：" + c);
        if (c == 49) {
            System.out.println("符合要求");
        } else {
            System.out.println("不符合要求");
        }
    }

    @Test
    public void testDateC() throws ParseException {
//        Date lastExitDate = DateUtil.offsetDay(new Date(),-10);
//        Date applyDate = DateUtil.offsetDay(new Date(),-1);
//        int compare = DateUtil.s(lastExitDate, applyDate);
//        System.out.println("日期相差" + compare + "天");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date lastExitDate = sdf.parse("2023-11-18 21:33:18");
        Date applyDate = sdf.parse("2023-11-19 20:33:18");

        LocalDate localDate1 = lastExitDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = applyDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long days = ChronoUnit.DAYS.between(localDate1, localDate2);
        System.out.println("日期相差" + days + "天");
    }

    @Test
    public void testTxt(){
//        String input = "D:\\tempFile\\input.txt";
//        String output = "D:\\tempFile\\output.txt";
//        processFile(input, output);

        List<String> lbsRecordList = new ArrayList<>();
        lbsRecordList.add("1");
        lbsRecordList.add("2");
        lbsRecordList.add("3");
//        Iterator<String> iterator = lbsRecordList.iterator();
//        while(iterator.hasNext()){
//            String next = iterator.next();
//            if (next.equals("2")) {
//                iterator.remove();
//            }
//        }
        for (String record : lbsRecordList) {
            if (record.equals("2")) {
                lbsRecordList.remove(record);
            }
        }

        System.out.println(lbsRecordList);
    }

    public static void processFile(String inputFileName, String outputFileName) {
        try {
            File inputFile = new File(inputFileName);
            Scanner scanner = new Scanner(inputFile);
            PrintWriter writer = new PrintWriter(outputFileName);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String processedLine = processLine(line); // 自定义的处理方法
                writer.println(processedLine);
            }

            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String processLine(String line) {
        // 使用正则表达式将驼峰格式的变量名转为下划线形式的变量名
        return line.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

}

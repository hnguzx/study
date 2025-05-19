//package main.java;
//
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.date.DateUnit;
//import cn.hutool.core.date.DateUtil;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.itextpdf.text.pdf.*;
//import com.itextpdf.text.pdf.parser.PdfTextExtractor;
//import com.itextpdf.text.pdf.security.PdfPKCS7;
//import lombok.extern.slf4j.Slf4j;
//import main.model.Customer;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.lang3.StringUtils;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.junit.jupiter.api.Test;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//
//import java.io.*;
//import java.security.GeneralSecurityException;
//import java.security.Security;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Slf4j
//public class EEPTest {
//
//    public static final String PORT = ".*罗湖.*|.*文锦渡.*|.*皇岗.*|.*深圳湾.*|.*福田.*|.*莲塘.*|.*蛇口.*|.*福永或机场或空港.*|.*西九龙.*";
//
//    @Test
//    void test2() throws IOException {
//        // 添加提供者以获取签名
//        BouncyCastleProvider provider = new BouncyCastleProvider();
//        Security.addProvider(provider);
//
////        File file = new File("C:\\Users\\25446\\Desktop\\eep check\\354233ce25ae5f4a3f910fdd66717ac7.pdf");
//        File file = new File("C:\\Users\\25446\\Desktop\\eep check\\多页.pdf");
//        byte[] bytes = new byte[(int) file.length()];
//        GsonBuilder GSON_BUILDER = new GsonBuilder();
//        try (FileInputStream fileInputStream = new FileInputStream(file);) {
//            fileInputStream.read(bytes);
//            String content = Base64.encodeBase64String(bytes);
//            boolean signResult = checkEntryFileSign(content);
//            log.info("签名检查结果：{}", signResult);
//            String entryFileFistContent = getEntryFileFistContent(content);
//            Gson gson = new GsonBuilder().create();
//            JsonObject object = gson.fromJson(entryFileFistContent, JsonObject.class);
//            String[] dateInfo = object.get("dateInfo").getAsString().split(" ");
//            log.info("编号：{}", dateInfo[0].substring(3));
//            Map<String, String> checkFields = new HashMap<>();
//
//            checkFields.put("makeDate", null);
//            checkFields.put("queryDate", null);
//            checkFields.put("entryOrExit", "出境");
//            checkFields.put("certificateType", "往来港澳通行证");
//            checkFields.put("port", null);
//
//            checkFields.put("name", "刘梦影");
//            checkFields.put("gender", "F");
//            checkFields.put("birthday", "19910224");
//            checkFields.put("certificateNo", "C09871074");
//
//            checkFields.put("idNo", "360103199102244725");
//            Map<String, Boolean> fieldCheckResult = checkEntryFileContent(entryFileFistContent, checkFields);
//            Boolean name = fieldCheckResult.get("name");
//
//            String string = fieldCheckResult.toString();
//            String json = GSON_BUILDER.create().toJson(fieldCheckResult);
//            log.info("具体字段检查结果：{}", string);
//            Map map = GSON_BUILDER.create().fromJson(json, Map.class);
//            Map<String, Boolean> stringStringMap = mapStringToMap(string);
//            log.info("转换后结果：{}", stringStringMap);
//        }
//    }
//
//    @Test
//    void testPort() {
//        String testPort = "西九龙口岸";
//        log.info("口岸匹配是否成功：{}", testPort.matches(PORT));
//        boolean result = testPort.contains("皇岗");
//        log.info("口岸匹配是否成功：{}", result);
//
//
//        List<String> list = new ArrayList<>();
//        list.add("罗湖");
//        list.add("文锦渡");
//        list.add("皇岗");
//        list.add("深圳湾");
//        list.add("福田");
//        list.add("莲塘");
//        list.add("蛇口");
//        list.add("福永");
//        list.add("西九龙");
//
//        result = false;
//        Set<String> allowEntryPortSet = new HashSet<>(list);
//        for (String port : allowEntryPortSet) {
//            if (testPort.contains(port)) {
//                result = true;
//                break;
//            }
//        }
//        log.info("口岸匹配是否成功：{}", result);
//    }
//
//    @Test
//    void testList() {
//        List<String> list = new ArrayList<>();
//        list.add("罗蒙骰子");
//        list.add("世界！鬼");
//        log.info("list:{}", list);
//        list.set(0, "guzx");
//        log.info("list:{}", list);
//    }
//
//    @Test
//    void testFormat() {
//        String dateTimeFormat = "\\d{4}年\\d{2}月\\d{2}日\\d{2}时\\d{2}分";
//        log.info("result:{}", Pattern.matches(dateTimeFormat, "2022年02月20日10时10分"));
//        String dateFormat = "\\d{4}年\\d{2}月\\d{2}日";
//        log.info("result:{}", Pattern.matches(dateFormat, "2022年02月20日"));
//        String genderFormat = "[男女]";
//        log.info("result:{}", Pattern.matches(genderFormat, "男"));
//    }
//
//    @Test
//    void testCompare() {
//        log.info("v1>v2?:{}", compare("5.6.0", "5.5.0"));
//    }
//
//    @Test
//    void testAsync() {
//        List<Customer> list = new ArrayList<>();
//        for (int i = 0; i < 1000000; i++) {
//            list.add(generateCustomer());
//        }
//        ExecutorService executor = Executors.newFixedThreadPool(20);
//        log.info("start time:{}", System.currentTimeMillis());
//        for (Customer customer : list) {
//            customer.setPassWord(UUID.randomUUID().toString().substring(0, 10));
//            demo(customer, executor);
//        }
//        log.info("end   time:{}", System.currentTimeMillis());
//        // 关闭线程池
//        executor.shutdown();
//
//    }
//
//    private Customer generateCustomer() {
//        Customer customer = new Customer();
//        customer.setId((int) (Math.random() * 10000));
//        return customer;
//    }
//
//    private void demo(Customer customer, ExecutorService executor) {
//        // 循环提交任务
//        executor.execute(() -> customer.setUserName(UUID.randomUUID().toString().substring(0, 5)));
//    }
//
//    private void demo2(Customer customer) {
//        // 循环提交任务
//        customer.setUserName(UUID.randomUUID().toString().substring(0, 5));
//    }
//
//    public static int compare(String v1, String v2) {
//        if (!StringUtils.isBlank(v1) && !StringUtils.isBlank(v2)) {
//            String[] arr1 = v1.split("\\.");
//            String[] arr2 = v2.split("\\.");
//            int len = Math.min(arr1.length, arr2.length);
//
//            for (int i = 0; i < len; ++i) {
//                if (!arr1[i].equals(arr2[i])) {
//                    return Integer.parseInt(arr1[i]) - Integer.parseInt(arr2[i]);
//                }
//            }
//
//            return arr1.length - arr2.length;
//        } else {
//            throw new IllegalArgumentException("version parameter can't be empty");
//        }
//    }
//
//
//    @Test
//    void testDate() {
//        Date todya = new Date();
//        Date yesterday = new Date(1682524801000L);
//        Date yesterdayBefore = new Date(1682521200000L);
//        Date tomorrow = new Date(1682697600000L);
//
//        int dayNumber = 0;
//
//        log.info("是否在过去的{}天内：{}", dayNumber, isWithinPastDays(todya, dayNumber));
//        log.info("是否在过去的{}天内：{}", dayNumber, isWithinPastDays(yesterday, dayNumber));
//        log.info("是否在过去的{}天内：{}", dayNumber, isWithinPastDays(yesterdayBefore, dayNumber));
//        log.info("是否在过去的{}天内：{}", dayNumber, isWithinPastDays(tomorrow, dayNumber));
//    }
//
//    public boolean isWithinPastDays(Date date, int days) {
//        if (days < 0) {
//            return false;
//        }
//        // 获取指定日期的零点时刻
//        LocalDate beforeDate = DateUtil.offsetDay(new Date(), -days).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay().toLocalDate();
//        Date beforeDateZero = Date.from(beforeDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//        // 获取明天的零点时刻
//        LocalDate tomorrow = DateUtil.offsetDay(new Date(), 1).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay().toLocalDate();
//        Date tomorrowZero = Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//        return date.equals(beforeDateZero) || (date.after(beforeDateZero) && date.before(tomorrowZero));
//    }
//
//    public static Map<String, Boolean> mapStringToMap(String str) {
//        str = str.substring(1, str.length() - 1);
//        String[] strs = str.split(",");
//        Map<String, Boolean> map = new HashMap<>();
//        for (String string : strs) {
//            String key = string.split("=")[0];
//            String value = string.split("=")[1];
//            // 去掉头部空格
//            String key1 = key.trim();
//            String value1 = value.trim();
//            map.put(key1, Boolean.valueOf(value1));
//        }
//        return map;
//    }
//
//
//    /**
//     * 检查入境文件内容
//     */
//    public Map<String, Boolean> checkEntryFileContent(String content, Map<String, String> checkFields) {
//        Map<String, Boolean> result = new HashMap<>();
//        Map<String, String> map = getCheckFieldsAndValue(content);
//        checkFields.keySet().forEach(item -> {
//            if (map.containsKey(item)) {
//                Date makeDate = DateUtil.parse(map.get("makeDate"), "yyyy年MM月dd日HH时mm分");
//                switch (item) {
//                    case "makeDate":
//                        Date today = new Date();
//                        Date yesterday = DateUtil.offsetDay(today, -1);
//                        result.put(item, DateUtil.isSameDay(makeDate, today) || DateUtil.isSameDay(makeDate, yesterday));
//                        break;
//                    case "queryDate":
//                        Date queryDate = DateUtil.parse(map.get("queryDate"), "yyyy年MM月dd日");
//                        result.put(item, DateUtil.isSameDay(makeDate, queryDate));
//                        break;
//                    case "birthday":
//                        Date birthday = DateUtil.parse(checkFields.get(item), "yyyyMMdd");
//                        Date birthdayFile = DateUtil.parse(map.get(item), "yyyy年MM月dd日");
//                        result.put(item, birthday.equals(birthdayFile));
//                        break;
//                    case "port":
//                        result.put(item, map.get(item).matches(PORT));
//                        break;
//                    case "gender":
//                        result.put(item, ("M".equals(checkFields.get(item)) && "男".equals(map.get("gender"))
//                                || "F".equals(checkFields.get(item)) && "女".equals(map.get("gender"))));
//                        break;
//                    default:
//                        result.put(item, map.get(item).equals(checkFields.get(item)));
//                        break;
//                }
//            }
//        });
//        return result;
//    }
//
//    /**
//     * 获取要检查的值
//     */
//    public Map<String, String> getCheckFieldsAndValue(String content) {
//        Map<String, String> map = new HashMap<>();
//        if (StringUtils.isNotEmpty(content)) {
//            Gson gson = new GsonBuilder().create();
//            JsonObject object = gson.fromJson(content, JsonObject.class);
////            JsonObject object = JSONUtils.fromJSONString(content, JsonObject.class);
//
//            String[] dateInfo = object.get("dateInfo").getAsString().split(" ");
//            String[] userInfo = object.get("userInfo").getAsString().split(" ");
//            String[] prcIdInfo = object.get("prcIdInfo").getAsString().split(" ");
//            String[] entryInfo = object.get("entryInfo").getAsString().split(" ");
//            String[] makeInfo = object.get("makeInfo").getAsString().split(" ");
//
//
//            if (dateInfo.length > 1) {
//                // yyyy年MM月dd日
//                map.put("queryDate", dateInfo[7] + dateInfo[8] + dateInfo[9] + dateInfo[10] + dateInfo[11] + "日");
//            }
//            if (userInfo.length > 5) {
//                map.put("name", userInfo[1]);
//                // 男/女
//                map.put("gender", userInfo[3]);
//                // yyyy年MM月dd日
//                map.put("birthday", userInfo[5]);
//            }
//            if (entryInfo.length > 5) {
//                // 出境/入境
//                map.put("entryOrExit", entryInfo[1]);
//                // yyyy-MM-dd
//                map.put("entryOrExitDate", entryInfo[2]);
//                // 往来港澳通行证
//                map.put("certificateType", entryInfo[3]);
//                map.put("certificateNo", entryInfo[4]);
//                // 西九龙口岸
//                map.put("port", entryInfo[5]);
//            }
//            if (makeInfo.length > 0) {
//                // yyyy年MM月dd日hh时mm分
//                map.put("makeDate", makeInfo[0].substring(5));
//            }
//            if (prcIdInfo.length > 1) {
//                map.put("idNo", prcIdInfo[1]);
//            }
//        }
//        return map;
//    }
//
//    /**
//     * 获取入境文件内容
//     */
//    public String getEntryFileFistContent(String fileContent) throws RuntimeException {
//        PdfReader reader = null;
//        try (InputStream inputStream = new ByteArrayInputStream(Base64.decodeBase64(fileContent))) {
//            reader = new PdfReader(inputStream);
//            int numberOfPages = reader.getNumberOfPages();
//            String firstContent = PdfTextExtractor.getTextFromPage(reader, 1);
//            String lastContent = firstContent;
//            String[] first = firstContent.split("\n");
//            String[] last = first;
//            if (numberOfPages != 1) {
//                lastContent = PdfTextExtractor.getTextFromPage(reader, numberOfPages);
//                last = lastContent.split("\n");
//            }
//
//            Map<String, String> checkContent = new HashMap<>();
//
//
//            String inOrExit = null;
//            for (int i = 0; i < first.length; i++) {
//                if (first[i].contains("出境/入境")) {
//                    inOrExit = first[i + 1];
//                    break;
//                }
//            }
//            String[] ImmigrationInformation = inOrExit.split(" ");
//            String entryOrExit = ImmigrationInformation[1];
//            String entryOrExitDate = ImmigrationInformation[2];
//            String certificateType = ImmigrationInformation[3];
//            String certificateNo = ImmigrationInformation[4];
//            String port = ImmigrationInformation[5];
//            if (ImmigrationInformation.length > 6) {
//                String flight = ImmigrationInformation[6];
//            }
//
//            int nameIndex = firstContent.lastIndexOf("查询人姓名");
//            int genderIndex = firstContent.lastIndexOf("性别");
//            int brithdayIndex = firstContent.lastIndexOf("出生日期");
//            int idNoIndex = firstContent.lastIndexOf("公民身份号码");
//            int queryDateIndex = firstContent.lastIndexOf("日期间有下列出入境记录");
//            int makeDateIndex = lastContent.lastIndexOf("制作日期");
//            int noIndex = firstContent.lastIndexOf("编号");
//
//            String name = firstContent.substring(nameIndex + 7, genderIndex - 2);
//            String gender = firstContent.substring(genderIndex + 4, brithdayIndex - 2);
//            String brithday = firstContent.substring(brithdayIndex + 6, brithdayIndex + 17);
//            String idNo = firstContent.substring(idNoIndex + 8, idNoIndex + 26);
//            String queryDate = firstContent.substring(queryDateIndex - 15, queryDateIndex).replace(" ", "") + "日";
//            String makeDate = lastContent.substring(makeDateIndex + 5, makeDateIndex + 16);
//            String no = firstContent.substring(noIndex + 3, noIndex + 16).trim();
//
//
//
//
//            String userInfo = first[3];
//            checkContent.put("userInfo", userInfo);
//            checkContent.put("dateInfo", first[5]);
//            checkContent.put("prcIdInfo", first[4]);
//            checkContent.put("entryInfo", first[7]);
//            checkContent.put("makeInfo", last[last.length - 2]);
//
//            Gson gson = new GsonBuilder().create();
//            return gson.toJson(checkContent);
////            return JSONUtils.toJSONString(checkContent);
//        } catch (Exception e) {
//            log.error("解析入境文件内容错误", e);
//            throw new RuntimeException("解析入境文件内容错误");
//        } finally {
//            if (reader != null) {
//                reader.close();
//            }
//        }
//    }
//
//    /**
//     * 检查出入境文件的签名情况
//     */
//    public boolean checkEntryFileSign(String fileContent) throws RuntimeException {
//        boolean certificateResult = false;
//        boolean signResult = false;
//        PdfReader reader = null;
//        try (InputStream inputStream = new ByteArrayInputStream(Base64.decodeBase64(fileContent))) {
//            reader = new PdfReader(inputStream);
//            reader.setAppendable(true);
//            AcroFields fields = reader.getAcroFields();
//            ArrayList<String> signatureNames = fields.getSignatureNames();
//            X509Certificate trustCer = getTrustCer();
//
//            for (String item : signatureNames) {
//                PdfPKCS7 pkcs7 = fields.verifySignature(item); // 获取签名的pkcs7数据
//                for (Certificate cer : pkcs7.getSignCertificateChain()) {
//                    if (cer.equals(trustCer)) {
//                        certificateResult = true;
//                        continue;
//                    }
//                }
//                if (pkcs7.verify()) {
//                    signResult = true;
//                    continue;
//                }
//            }
//        } catch (Exception e) {
//            log.error("出入境文件签名验证失败", e);
//            // todo 错误码待定
//            throw new RuntimeException("出入境文件签名验证失败");
//        } finally {
//            if (reader != null) {
//                reader.close();
//            }
//        }
//        return certificateResult && signResult;
//    }
//
//    /**
//     * 获取出入境证书
//     * todo 待优化，可以缓存
//     */
//    public X509Certificate getTrustCer() throws IOException, CertificateException {
//        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dzwjyqbfzzs.cer")) {
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            return (X509Certificate) certificateFactory.generateCertificate(inputStream);
//        }
//    }
//
//}

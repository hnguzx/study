package main.java;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import main.model.Customer;
import main.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.StringUtil;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class GsonTest {

    @Test
    public void gsonTest() {
        String extraction = "{\"id\": \"415122c7-3e7a-40b5-b0eb-2623c27e9b03\", \"data\": {\"br_no\": {\"br_no_diff\": false, \"br_no_default\": \"36687383\"}, \"cr_no\": \"1026143\", \"br_info\": {\"br_address\": \"FLAT/RM B3 3/F TAI TAK IND BUILDING 2-12 KWAI FAT ROAD KWAI CHUNG NT\", \"br_payment\": true, \"info_owners\": [], \"br_date_expiry\": \"2022-02-22\", \"brcert_name_cn\": \"金道科技資本有限公司\", \"brcert_name_en\": \"GLOBAL GOLDLINKS CAPITAL LIMITED\", \"br_address_comparecr_diff\": true, \"brcert_name_cn_comparecr_diff\": false, \"brcert_name_en_comparecr_diff\": false}, \"cp_info\": {\"cp_r_address\": {\"r_address\": \"UNIT B3, 3/F., TAI TAK IND. BUILDING, 2-12 KWAI FAT ROAD, KWAI CHUNG, NT, HONG KONG\"}, \"company_status\": \"live\", \"r_company_name\": {\"r_company_name_cn\": \"金道科技資本有限公司\", \"r_company_name_en\": \"GLOBAL GOLDLINKS CAPITAL LIMITED\", \"r_company_name_cn_diff\": true, \"r_company_name_en_diff\": true}, \"date_incorporation\": {\"date_from_cp\": \"2006-02-23\"}, \"company_name_record\": [{\"date_changed\": \"2018-06-12\", \"found_in_cocon\": true, \"cocon_available\": true, \"company_name_cn\": \"金道科技資本有限公司\", \"company_name_en\": \"GLOBAL GOLDLINKS CAPITAL LIMITED\"}, {\"date_changed\": \"2018-05-31\", \"found_in_cocon\": true, \"cocon_available\": true, \"company_name_cn\": \"環球紫金聯資本有限公司\", \"company_name_en\": \"GOLDLINKS CAPITAL LIMITED\"}, {\"date_changed\": \"2018-05-15\", \"found_in_cocon\": true, \"cocon_available\": true, \"company_name_cn\": \"金聯區塊鏈資本有限公司\", \"company_name_en\": \"GLOBAL GOLD BLOCKCHAIN CAPITAL LIMITED\"}, {\"date_changed\": \"2014-08-13\", \"found_in_cocon\": true, \"cocon_available\": true, \"company_name_en\": \"GOGOPARENT.COM LIMITED\"}, {\"date_changed\": \"2013-12-18\", \"found_in_cocon\": true, \"cocon_available\": true, \"company_name_cn\": \"伊迪康生命科技有限公司\", \"company_name_en\": \"AETAS BIO-MEDICAL LIMITED\"}, {\"date_changed\": \"2009-12-21\", \"found_in_cocon\": true, \"cocon_available\": true, \"company_name_cn\": \"英翹國際有限公司\", \"company_name_en\": \"WINNING INTERNATIONAL (ASIA) LIMITED\"}, {\"date_changed\": \"2009-10-21\", \"found_in_cocon\": true, \"cocon_available\": true, \"company_name_cn\": \"英翹國際教育有限公司\", \"company_name_en\": \"WININING EDUCATION INTERNATIONAL LIMITED\"}, {\"date_changed\": \"2006-02-23\", \"found_in_cocon\": false, \"cocon_available\": true, \"company_name_cn\": \"冠源有限公司\", \"company_name_en\": \"CHAMPION LINKER LIMITED\"}], \"company_status_full\": \"live\", \"total_amount_shares\": [\"HKD1\"], \"country_incorporation\": \"HONG KONG\"}, \"other_info\": {\"icris_pendingdoc\": false}, \"entity_type\": {\"company_type\": \"private_company\", \"entity_type_default\": \"local_limited\"}, \"director_info\": {\"director_details\": [{\"hkid_cr\": \"H338483(9)\", \"d_name_cn\": \"林佳穎\", \"d_name_en\": \"LAM KAI WING\", \"hkid_cr_source\": [\"cp\"], \"d_name_cn_source\": [\"cp\"], \"d_name_en_source\": [\"cp\"], \"passport_no_source\": [\"cp\"], \"passport_issue_country_source\": [\"cp\"]}], \"director_body_coporate\": false}, \"purchase_info\": {}, \"shareholder_info\": [{\"s_name_cn\": \"林佳穎\", \"s_name_en\": \"LAM KAI WING\", \"s_name_address\": \"FLAT B, 18/F., BLOCK 6, CAVENDISH HEIGHTS, NO. 33 PERKINS ROAD, HONG KONG.\", \"s_currentholding\": \"1\", \"s_name_cn_source\": [\"nar1\"], \"s_name_en_source\": [\"nar1\"], \"s_name_address_source\": [\"nar1\"], \"s_currentholding_source\": [\"nar1\"]}], \"document_purchase\": {\"nd4\": 0, \"nr1\": 0, \"nar1\": 1, \"nd2a\": 0, \"nd2b\": 0, \"nnc1\": 0, \"nre1\": 0, \"nre2\": 0, \"nre3\": 0, \"nre4\": 0, \"nsc1\": 0, \"nama1\": 0, \"nama4\": 0, \"nsc14\": 0, \"articles_of_association\": 1, \"company_particulars_paid\": 1, \"company_particulars_unpaid\": 1, \"note_unsuccessful_purchase\": 0, \"certificate_of_incorporation\": 1, \"certificate_of_change_of_name\": 7, \"business_registration_eextract\": 0, \"business_registration_certificate\": 1}, \"shareholding_distribution\": {\"total_issued_shares\": 1, \"number_issued_shares\": 1}}, \"ticket_id\": \"1b923536-355d-44c1-b8b6-2f1e1ca02541\"}";

        JsonObject jsonData = JsonUtil.stringToJson(extraction);
        String extractionInfo = JsonUtil.getStringFromJson(jsonData, "data");
        String brInfo = JsonUtil.getStringFromJson(extractionInfo, "cp_info");
        String companyInfo = JsonUtil.getStringFromJson(brInfo, "r_company_name");
        String companyName = JsonUtil.getStringFromJson(companyInfo, "r_company_name_en");
        String companyLocalName = JsonUtil.getStringFromJson(companyInfo, "r_company_name_cn");
        String dateInfo = JsonUtil.getStringFromJson(brInfo, "date_incorporation");
        String establishDate = JsonUtil.getStringFromJson(dateInfo, "date_from_cp");
        String addressInfo = JsonUtil.getStringFromJson(brInfo, "cp_r_address");
        String address = JsonUtil.getStringFromJson(addressInfo, "r_address");
        log.info("companyName:{},companyLocalName:{},establishDate:{},address:{}", companyName, companyLocalName, establishDate, address);

        String companyNameRecord = JsonUtil.getStringFromJson(brInfo, "company_name_record");
        log.info("companyNameRecord:{}", companyNameRecord);

        List<Map<String, String>> nameList = new ArrayList<>();

        JsonArray jsonElements = JsonUtil.stringToJsonArray(companyNameRecord);

        int i = 0;
        for (JsonElement element : jsonElements) {
            if (i == 0) {
                i++;
                continue;
            }
            JsonObject jsonObject = element.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
            Iterator<Map.Entry<String, JsonElement>> iterator = entries.iterator();
            Map<String, String> companyNameInfo = new HashMap<>();

            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                String key = entry.getKey().toString();
                if ("date_changed".equals(key) || "company_name_cn".equals(key) || "company_name_en".equals(key)) {
                    String val = entry.getValue() == null ? null : entry.getValue().toString();
                    companyNameInfo.put(lineToHump(key), val);
                }
            }
            log.info("companyNameInfo:{}", companyNameInfo.toString());
            nameList.add(companyNameInfo);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -5);
        Date date = c.getTime();
        String fiveYearDate = sdf.format(date);
        log.info("五年前的日期是：{}", fiveYearDate);
        log.info("nameList:{}", nameList.toString().replace("=", ":"));

        Iterator<Map<String, String>> nameListIterator = nameList.iterator();
        while (nameListIterator.hasNext()) {
            Map<String, String> companyNameInfo = nameListIterator.next();
            Set<Map.Entry<String, String>> entries = companyNameInfo.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                log.info("key:{}", key);
                if ("dateChanged".equals(key)) {
                    String changeDate = next.getValue().replace("-", "").replace("\"", "");
                    if (Integer.valueOf(changeDate) < Integer.valueOf(fiveYearDate)) {
                        nameListIterator.remove();
                        nameList.remove(next);
                    }
                }
            }
        }
        log.info("nameList:{}", nameList.toString().replace("=", ":"));
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    @Test
    public void jsonUtilTest() {
        Customer customer = new Customer(1, "guzx", "123");
        String jsonString = JsonUtil.beanToJson(customer);
//        log.info(jsonString);
        Iterator iter = JsonUtil.getIteratorFromJson(jsonString);
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            log.info("key:{},value:{}", key, value);
        }
        List<Customer> list = new ArrayList<>();
        list.add(customer);
        String listString = JsonUtil.beanToJson(list);
        log.info(listString);
        Map<String, String> map = new HashMap<>();
        map.put("key", "val");
        String mapString = JsonUtil.beanToJson(map);
        log.info(mapString);
    }

    @Test
    public void jsonTest() {
        String jsonStr = "{\"parameters\":{\"entity_type\":\"local_limited\",\"cr_number\":\"1026143\",\"br_number\":\"36687383\"},\"id\":\"1b923536-355d-44c1-b8b6-2f1e1ca02541\",\"status\":\"pending\",\"progress\":\"0000\",\"client_id\":\"c93606f6-bef8-4fd5-abc3-b9a8898a1f67\",\"name\":\"string\",\"remark\":null,\"created_at\":\"2021-09-15T08:04:42.770551\"}";
        Iterator iter = JsonUtil.getIteratorFromJson(jsonStr);
        Map<String, String> map = new HashMap<>();
        log.info("基础数据处理");
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            log.info("key:{},value:{}", entry.getKey().toString(), entry.getValue().toString());
            map.put(entry.getKey().toString(), entry.getValue().toString());
        }
        String parameters = map.get("parameters");
        JsonObject jsonData = JsonUtil.stringToJson(parameters);

        Iterator iter2 = jsonData.entrySet().iterator();
        while (iter2.hasNext()) {
            Map.Entry entry = (Map.Entry) iter2.next();
//            log.info("key:{},value:{}",entry.getKey().toString(),entry.getValue().toString());
        }
    }

    @Test
    public void gsonTest2() {
        String jsonStr = "{\"parameters\":{\"entity_type\":\"local_limited\",\"cr_number\":\"1026143\",\"br_number\":\"36687383\"},\"id\":\"1b923536-355d-44c1-b8b6-2f1e1ca02541\",\"status\":\"pending\",\"progress\":\"0000\",\"client_id\":\"c93606f6-bef8-4fd5-abc3-b9a8898a1f67\",\"name\":\"string\",\"remark\":null,\"created_at\":\"2021-09-15T08:04:42.770551\"}";
        Iterator iter = JsonUtil.getIteratorFromJson(jsonStr);
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            log.info("key:{},value:{}", entry.getKey().toString(), handleResp(entry.getValue().toString()));
        }

        String remark = JsonUtil.getStringFromJson(jsonStr, "remark");
        log.info("remark is null ? {}", remark == null);

        JsonObject jsonObject1 = JsonUtil.stringToJson(jsonStr);

        String data = JsonUtil.getStringFromJson(jsonStr, "id");
        log.info("id:{}", data);

        JsonObject jsonObject = JsonUtil.stringToJson(jsonStr);
        String created_at = JsonUtil.getStringFromJson(jsonObject, "created_at");
//        log.info(created_at);
    }

    public String handleResp(String response) {
        if (response.startsWith("\"")) {
            response = response.substring(1);
        }
        if (response.endsWith("\"")) {
            response = response.substring(0, response.length() - 1);
        }
        return response;
    }

    @Test
    public void jsonTest3() {
        String testJSON = "[{\"meta\":{\"item_number\":\"2109B2592\",\"product_code\":\"RED153\"},\"format\":\"pdf\",\"checksum\":\"9710dcd69f26e75b02a9\n" +
                "c210ae950cd5\",\"created_at\":\"2021-09-15T06:59:32.067054\",\"id\":\"ab2104ff-41b3-4371-80c3-54f189cf98fa\",\"type\":\"br_extract\",\"ticket_id\":\"85700288-20f6-464e-95e0-d79fb7de2d06\"},{\"meta\":{\"item_number\":\"2109B2595\",\"product_code\":\"RED155\"},\"format\":\"pdf\",\n" +
                "\"checksum\":\"173045068c0160c3aa0cc7f527d541fa\",\"created_at\":\"2021-09-16T00:22:19.757329\",\"id\":\"bbc120f4-b28e-4633-8ef7-adbe097adf9d\",\"type\":\"br_cert\",\"ticket_id\":\"85700288-20f6-464e-95e0-d79fb7de2d06\"}]";
        JsonArray jsonElements = JsonUtil.stringToJsonArray(testJSON);
        for (int i = 0; i < jsonElements.size(); i++) {
            JsonObject asJsonObject = jsonElements.get(i).getAsJsonObject();
            log.info(handleResp(String.valueOf(asJsonObject.get("type"))));
            log.info(handleResp(String.valueOf(asJsonObject.get("format"))));
            log.info(handleResp(String.valueOf(asJsonObject.get("id"))));
            log.info(handleResp(String.valueOf(asJsonObject.get("checksum"))));
        }
    }
}

package main.java;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import main.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class ExcelTest {

    @Test
    public void test() {
        try {
            String jsonFilePath = "D:\\document\\temp\\faq.json";
            String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

            // 创建工作簿和工作表
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            // 解析JSON数据
            JSONArray jsonArray = new JSONArray(json);
            log.info("最外层有：{}个数据", jsonArray.length());
            int count = 0;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray faqArray = jsonObject.getJSONArray("faq");
                log.info("第二层有：{}个数据", faqArray.length());
                for (int j = 0; j < faqArray.length(); j++) {
                    JSONObject faqObject = faqArray.getJSONObject(j);
                    log.info("当前id:{}", jsonObject.getString("id"));
                    JSONObject zhObject = faqObject.getJSONObject("zh");
                    JSONObject cnObject = faqObject.getJSONObject("cn");
                    JSONObject enObject = faqObject.getJSONObject("en");

//                    log.info("创建：{}行", i * faqArray.length() + j);
                    // 创建行
                    Row row = sheet.createRow(i * 40 + j + 1);

                    // 创建单元格并填充数据
                    Cell cell1 = row.createCell(0);
                    cell1.setCellValue(cnObject.getString("title"));

                    Cell cell2 = row.createCell(1);
                    cell2.setCellValue(cnObject.getString("content"));

                    Cell cell3 = row.createCell(2);
                    cell3.setCellValue(zhObject.getString("title"));

                    Cell cell4 = row.createCell(3);
                    cell4.setCellValue(zhObject.getString("content"));

                    Cell cell5 = row.createCell(4);
                    cell5.setCellValue(enObject.getString("title"));

                    Cell cell6 = row.createCell(5);
                    cell6.setCellValue(enObject.getString("content"));
                    count++;
                }
            }

            // 保存Excel文件
            FileOutputStream fileOut = new FileOutputStream("D:\\document\\temp\\output.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            System.out.println("Excel文件已成功生成。");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test02() {
        String excelPath = "/Users/admin/Downloads/全球国家列表_latest.xlsx";
        String sqlPathPrefix = "notCNOutput_";

        List<Country> countryList = new ArrayList<>();
        // 读取国家信息
        EasyExcel.read(excelPath, Country.class, new AnalysisEventListener<Country>() {
            @Override
            public void invoke(Country user, AnalysisContext context) {
                countryList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(0).doRead();

        List<City> cityList = new ArrayList<>();
        // 读取国家城市信息
        EasyExcel.read(excelPath, City.class, new AnalysisEventListener<City>() {
            @Override
            public void invoke(City user, AnalysisContext context) {
                cityList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(1).doRead();

        int fileIndex = 1;
        int recordCount = 0;
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(sqlPathPrefix + fileIndex + ".sql"));
            Set<String> errorCountryList = new HashSet<>();
            int cnCount = 0;

            for (City city : cityList) {
                String countryName = city.getCountry();
                if (countryName.equalsIgnoreCase("China") || countryName.equalsIgnoreCase("Macao") ||
                        countryName.equalsIgnoreCase("Hong Kong") || countryName.equalsIgnoreCase("Taiwan")) {
                    cnCount++;
                    continue;
                }
                Country country = getCountryByName(countryName, countryList);
                if (Objects.isNull(country)) {
                    log.info("处理:{}的城市信息为空", countryName);
                    errorCountryList.add(countryName);
                    continue;
                }
                String sql = handlerNotCNAddressSql(city, country);
                if (StringUtils.isNotBlank(sql)) {
                    writer.write(sql);
                    writer.newLine();
                    recordCount++;

                    log.info("第:{}次写入成功,城市:{}的国家:{}", recordCount, city.getCity(), countryName);

                    // 每满 1000 条数据切换到新文件
                    if (recordCount % 1000 == 0) {
                        writer.close();
                        fileIndex++;
                        writer = new BufferedWriter(new FileWriter(sqlPathPrefix + fileIndex + ".sql"));
                    }
                }
            }

            for (String countryName : errorCountryList) {
                log.info("导入失败的国家有 :{}", countryName);
            }
            log.info("一共导入:{}个非中国的城市,中国城市共:{}", recordCount, cnCount);
        } catch (Exception e) {
            log.error("生成SQL文件时发生错误", e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                log.error("关闭文件时发生错误", e);
            }
        }
    }
    /*public void test02(){
        String excelPath = "/Users/admin/Downloads/全球国家列表_latest.xlsx";
        String sqlPath = "notCNOutput.sql";

        List<Country> countryList = new ArrayList<>();
        // 读取国家信息
        EasyExcel.read(excelPath, Country.class, new AnalysisEventListener<Country>() {
            @Override
            public void invoke(Country user, AnalysisContext context) {
                countryList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(0).doRead();

        List<City> cityList = new ArrayList<>();
        // 读取国家城市信息
        EasyExcel.read(excelPath, City.class, new AnalysisEventListener<City>() {
            @Override
            public void invoke(City user, AnalysisContext context) {
                cityList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(1).doRead();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath))) {
            Set<String> errorCountryList = new HashSet<>();
            int i = 0;
            int cnCount = 0;
            for (City city : cityList) {
                String countryName = city.getCountry();
                if (countryName.equalsIgnoreCase("China") || countryName.equalsIgnoreCase("Macao") ||
                        countryName.equalsIgnoreCase("Hong Kong") || countryName.equalsIgnoreCase("Taiwan")) {
                    cnCount++;
                    continue;
                }
                Country country = getCountryByName(countryName, countryList);
                if (Objects.isNull(country)) {
                    log.info("处理:{}的城市信息为空", countryName);
                    errorCountryList.add(countryName);
                    continue;
                }
                String sql = handlerNotCNAddressSql(city, country);
                if (StringUtils.isNotBlank(sql) && i <1000) {
                    i++;
                    writer.write(sql);
                    writer.newLine();
                    log.info("第:{}次写入成功,城市:{}的国家:{}", i, city.getCity(), countryName);
                }
            }
            for (String countryName : errorCountryList) {
                log.info("导入失败的国家有 :{}", countryName);
            }
            log.info("一共导入:{}个非中国的城市,中国城市共:{}", i, cnCount);
        } catch (Exception e) {
            log.error("生成SQL文件时发生错误", e);
        }
    }*/


    @Test
    public void handlerCountryAndCity() {
        String excelPath = "/Users/admin/Downloads/全球国家列表_latest.xlsx";  // Excel文件路径

        List<Country> countryList = new ArrayList<>();
        // 读取国家信息
        EasyExcel.read(excelPath, Country.class, new AnalysisEventListener<Country>() {
            @Override
            public void invoke(Country user, AnalysisContext context) {
                countryList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(0).doRead();

        List<City> cityList = new ArrayList<>();
        // 读取国家城市信息
        EasyExcel.read(excelPath, City.class, new AnalysisEventListener<City>() {
            @Override
            public void invoke(City user, AnalysisContext context) {
                cityList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(1).doRead();

        /*List<AoMen> aoMenList = new ArrayList<>();
        // 读取澳门信息
        EasyExcel.read(excelPath, AoMen.class, new AnalysisEventListener<AoMen>() {
            @Override
            public void invoke(AoMen user, AnalysisContext context) {
                aoMenList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(3).doRead();

        handlerAmAddressSql(aoMenList, getCountryByName("Macao", countryList));*/

        /*List<Taiwan> twList = new ArrayList<>();
//         读取台湾信息
        EasyExcel.read(excelPath, Taiwan.class, new AnalysisEventListener<Taiwan>() {
            @Override
            public void invoke(Taiwan user, AnalysisContext context) {
                twList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet(4).doRead();

        handlerTwAddressSql(twList, getCountryByName("Taiwan", countryList));*/

        log.info("共读取到国家:{}个,城市:{}个",countryList.size(),cityList.size());
        String sqlPath = "notCNOutput.sql";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath));
            Set<String> errorCountryList = new HashSet<>();
            int i = 0;
            int cnCount = 0;
            for (City city : cityList) {
                String countryName = city.getCountry();
                if (countryName.equalsIgnoreCase("China") || countryName.equalsIgnoreCase("Macao") ||
                        countryName.equalsIgnoreCase("Hong Kong") || countryName.equalsIgnoreCase("Taiwan")) {
                    cnCount++;
                    continue;
                }
                Country country = getCountryByName(countryName, countryList);
                if(Objects.isNull(country)){
                    log.info("处理:{}的城市信息为空", countryName);
                    errorCountryList.add(countryName);
                    continue;
                }
                String sql = handlerNotCNAddressSql(city, country);
                if (StringUtils.isNotBlank(sql) && i < 100) {
                    i++;
                    writer.write(sql);
                    writer.newLine();
                    log.info("第:{}次写入成功,城市:{}的国家:{}", i, city.getCity(), countryName);
                }
//                else{
//                    log.info("城市:{}未找到对应的国家:{}",city.getCity(),city.getCountry());
//                }
            }
            for (String countryName : errorCountryList) {
                log.info("导入失败的国家有 :{}",countryName);
            }
            log.info("一共导入:{}个非中国的城市,中国城市共:{}",i,cnCount);
        } catch (Exception e) {
            System.err.println("生成SQL文件时发生错误：" + e.getMessage());
            log.info("exception", e);
        }

        /*String sqlPath2 = "cnOutput.sql";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath2));
            Set<String> errorCountryList = new HashSet<>();
            for (City city : cityList) {
                String countryName = city.getCountry();
                if (!countryName.equalsIgnoreCase("China")) {
                    continue;
                }
                Country country = getCountryByName(countryName, countryList);
                if(Objects.isNull(country)){
                    errorCountryList.add(countryName);
                    continue;
                }
                String sql = handlerCNAddressSql(city, country);
                if (StringUtils.isNotBlank(sql)) {
                    writer.write(sql);
                    writer.newLine();
                }
            }
            for (String countryName : errorCountryList) {
                log.info(countryName);
            }
        } catch (Exception e) {
            System.err.println("生成SQL文件时发生错误：" + e.getMessage());
            log.info("exception", e);
        }*/

    }

    private String handlerCNAddressSql(City city, Country country) {
        return null;
    }

    private String handlerNotCNAddressSql(City city, Country country) {


        if(Objects.isNull(country)){
            log.info("处理:{}的城市信息为空", city.getCountry());
            return "";
        }
        String sql = String.format(
                "INSERT INTO account_opening.country_city (cn_country, simple_cn_country, en_country, country_code, " +
                        "cn_region, simple_cn_region, en_region, region_code, " +

                        "iso_code, " +
                        "hot_flag, high_risk, force_ibank, purpose_mandatory, " +
                        "enable_status, created_time, modified_time) " +
                        "VALUES ('%s', '%s', '%s', '%s', " +

                        "%s, %s, '%s', %s," +
                        "'%s', " +
                        "%d, %d, %d, %d, " +
                        "%d, now(), now());",
                country.getCn_tc_country(),
                country.getCn_sc_country(),
                handlerSpecial(country.getEn_country()),
                country.getIso_code(),

                null,
                null,
                handlerSpecial(city.getCity()),
                null,

                country.getIso_code(),

                isHot(country.getIso_code()),
                isHightRisk(country.getHighrisk_country()),
                isIbankk(country.getIban_mandatory()),
                isPurposeMandatory(country.getPurpose_mandatory()),
                1
        );
        return sql;

    }

    private void handlerAmAddressSql(List<AoMen> aoMenList, Country country) {
        String sqlPath = "omOutput.sql";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath))) {
            for (AoMen aomen : aoMenList) {
                String sql = String.format(
                        "INSERT INTO account_opening.country_city (cn_country, simple_cn_country, en_country, country_code, " +
                                "cn_region, simple_cn_region, en_region, region_code, " +
//                                "cn_district, simple_cn_district, en_district, district_code, " +
                                "iso_code, " +
                                "hot_flag, high_risk, force_ibank, purpose_mandatory, " +
                                "enable_status, created_time, modified_time) " +
                                "VALUES ('%s', '%s', '%s', '%s', " +
//                                "'%s', '%s', '%s', %s, " +
                                "'%s', '%s', '%s', %s," +
                                "'%s', " +
                                "%d, %d, %d, %d, " +
                                "%d, now(), now());",
                        country.getCn_tc_country(),
                        country.getCn_sc_country(),
                        handlerSpecial(country.getEn_country()),
                        country.getIso_code(),

//                        taiwan.getRegionNameTC(),
//                        taiwan.getRegionNameSC(),
//                        handlerSpecial(taiwan.getRegionNameEN()),
//                        null,

                        aomen.getRegionNameTC(),
                        aomen.getRegionNameSC(),
                        handlerSpecial(aomen.getRegionNameEN()),
                        null,

                        country.getIso_code(),

                        isHot(country.getIso_code()),
                        isHightRisk(country.getHighrisk_country()),
                        isIbankk(country.getIban_mandatory()),
                        isPurposeMandatory(country.getPurpose_mandatory()),
                        1
                );
                writer.write(sql);
                writer.newLine();
            }
            System.out.println("SQL文件生成成功：" + sqlPath);
        } catch (IOException e) {
            System.err.println("生成SQL文件时发生错误：" + e.getMessage());
        }
    }

    private Country getCountryByName(String name, List<Country> countryList) {
        for (Country country : countryList) {
            String enCountry = country.getEn_country();

            if((name.equalsIgnoreCase("BOLIVIA, PLURINATIONAL STATE OF") && enCountry.equalsIgnoreCase("BOLIVIA")) ||
                    name.equalsIgnoreCase("BONAIRE, SINT EUSTATIUS AND SABA") && enCountry.equalsIgnoreCase("BONAIRE") ||
                    name.equalsIgnoreCase("CABO VERDE") && enCountry.equalsIgnoreCase("Cape Verde") ||
                    name.equalsIgnoreCase("CENTRAL AFRICAN REPUBLIC") && enCountry.equalsIgnoreCase("Central African R.") ||
                    name.equalsIgnoreCase("COMOROS") && enCountry.equalsIgnoreCase("The Comoros") ||
                    name.equalsIgnoreCase("CONGO, THE DEMOCRATIC REPUBLIC OF THE") && enCountry.equalsIgnoreCase("Congo Republic") ||
                    name.equalsIgnoreCase("CONGO") && enCountry.equalsIgnoreCase("D.R. Congo") ||
                    name.equalsIgnoreCase("COTE D'IVOIRE") && enCountry.equalsIgnoreCase("Ivory Coast") ||
                    name.equalsIgnoreCase("CZECHIA") && enCountry.equalsIgnoreCase("Czech Republic") ||
                    name.equalsIgnoreCase("ESWATINI") && enCountry.equalsIgnoreCase("Swaziland") ||
                    name.equalsIgnoreCase("FALKLAND ISLANDS (MALVINAS)") && enCountry.equalsIgnoreCase("Falkland Islands") ||
                    name.equalsIgnoreCase("HOLY SEE") && enCountry.equalsIgnoreCase("Vatican") ||
                    name.equalsIgnoreCase("IRAN (ISLAMIC REPUBLIC OF)") && enCountry.equalsIgnoreCase("Iran") ||

                    name.equalsIgnoreCase("KOREA (DEMOCRATIC PEOPLE'S REPUBLIC OF)") && enCountry.equalsIgnoreCase("North Korea") ||
                    name.equalsIgnoreCase("KOREA, REPUBLIC OF") && enCountry.equalsIgnoreCase("South Korea") ||
                    name.equalsIgnoreCase("KOSOVO, REPUBLIC OF") && enCountry.equalsIgnoreCase("Kosovo") ||
                    name.equalsIgnoreCase("LAO PEOPLE'S DEMOCRATIC REPUBLIC") && enCountry.equalsIgnoreCase("Laos") ||
                    name.equalsIgnoreCase("MICRONESIA (FEDERATED STATES OF)") && enCountry.equalsIgnoreCase("Micronesia") ||
                    name.equalsIgnoreCase("MOLDOVA, REPUBLIC OF") && enCountry.equalsIgnoreCase("Moldova") ||
                    name.equalsIgnoreCase("NORTH MACEDONIA") && enCountry.equalsIgnoreCase("Macedonia") ||
                    name.equalsIgnoreCase("PALESTINE, STATE OF") && enCountry.equalsIgnoreCase("Palestine") ||
                    name.equalsIgnoreCase("PHILIPPINES") && enCountry.equalsIgnoreCase("The Philippines") ||
                    name.equalsIgnoreCase("SAINT PIERRE AND MIQUELON") && enCountry.equalsIgnoreCase("St Pierre & Miquelon") ||
                    name.equalsIgnoreCase("ST HELENA,ASCENSION AND TRISTAN DA CUNHA") && enCountry.equalsIgnoreCase("Saint Helena") ||
                    name.equalsIgnoreCase("TANZANIA, UNITED REPUBLIC OF") && enCountry.equalsIgnoreCase("Tanzania") ||
                    name.equalsIgnoreCase("TURKIYE") && enCountry.equalsIgnoreCase("Turkey") ||
                    name.equalsIgnoreCase("UNITED KINGDOM") && enCountry.equalsIgnoreCase("Great Britain") ||
                    name.equalsIgnoreCase("SYRIAN ARAB REPUBLIC") && enCountry.equalsIgnoreCase("Syria") ||
                    name.equalsIgnoreCase("VIET NAM") && enCountry.equalsIgnoreCase("Vietnam") ||
                    name.equalsIgnoreCase("VIRGIN ISLANDS (U.S.)") && enCountry.equalsIgnoreCase("Virgin Islands") ||
                    name.equalsIgnoreCase("VENEZUELA (BOLIVARIAN REPUBLIC OF)") && enCountry.equalsIgnoreCase("Venezuela") ||
                    name.equalsIgnoreCase("UNITED STATES OF AMERICA") && enCountry.equalsIgnoreCase("United States") ||
                    name.equalsIgnoreCase("SAINT VINCENT AND THE GRENADINES") && enCountry.equalsIgnoreCase("Saint Vincent") ||
                    name.equalsIgnoreCase("VIRGIN ISLANDS, BRITISH") && enCountry.equalsIgnoreCase("British Virgin Islands") ||
                    name.equalsIgnoreCase("SINT MAARTEN (DUTCH PART)") && enCountry.equalsIgnoreCase("Sint Maarten") ||

                    name.equalsIgnoreCase("BRUNEI DARUSSALAM") && enCountry.equalsIgnoreCase("BRUNEI")){
                return country;
            }
            if(enCountry.contains("&")){
                enCountry = enCountry.replace("&", "and");
            }
            if (name.equalsIgnoreCase(enCountry)) {
                return country;
            }

        }
        return null;
    }

    private Country getCountryByIso(String name, List<Country> countryList) {
        for (Country country : countryList) {
            if (country.getIso_code().equals(name)) {
                return country;
            }
        }
        return null;
    }

    private void handlerTwAddressSql(List<Taiwan> twList, Country country) {
        String sqlPath = "twOutput.sql";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath))) {
            for (Taiwan taiwan : twList) {
                String sql = String.format(
                        "INSERT INTO account_opening.country_city (cn_country, simple_cn_country, en_country, country_code, " +
                                "cn_region, simple_cn_region, en_region, region_code, " +
                                "cn_district, simple_cn_district, en_district, district_code, " +
                                "iso_code, " +
                                "hot_flag, high_risk, force_ibank, purpose_mandatory, " +
                                "enable_status, created_time, modified_time) " +
                                "VALUES ('%s', '%s', '%s', '%s', " +
                                "'%s', '%s', '%s', %s, " +
                                "'%s', '%s', '%s', %s," +
                                "'%s', " +
                                "%d, %d, %d, %d, " +
                                "%d, now(), now());",
                        country.getCn_tc_country(),
                        country.getCn_sc_country(),
                        handlerSpecial(country.getEn_country()),
                        country.getIso_code(),

                        taiwan.getRegionNameTC(),
                        taiwan.getRegionNameSC(),
                        handlerSpecial(taiwan.getRegionNameEN()),
                        null,

                        taiwan.getRegionNameTC2(),
                        taiwan.getRegionNameSC2(),
                        handlerSpecial(taiwan.getRegionNameEN2()),
                        null,

                        country.getIso_code(),

                        isHot(country.getIso_code()),
                        isHightRisk(country.getHighrisk_country()),
                        isIbankk(country.getIban_mandatory()),
                        isPurposeMandatory(country.getPurpose_mandatory()),
                        1
                );
                writer.write(sql);
                writer.newLine();
            }
            System.out.println("SQL文件生成成功：" + sqlPath);
        } catch (IOException e) {
            System.err.println("生成SQL文件时发生错误：" + e.getMessage());
        }
    }

    public String handlerSpecial(String text) {
        if (StringUtils.isNotBlank(text) && text.contains("'")) {
            return text.replace("'", "''");
        }
        return text;
    }

    @Test
    public void generateCountry() {
        String excelPath = "/Users/admin/Downloads/全球国家列表_latest.xlsx";  // Excel文件路径
        String countrySqlPath = "countryOutput.sql";    // 输出的SQL文件路径

        List<Country> countryList = new ArrayList<>();

        // 读取Excel文件
        EasyExcel.read(excelPath, Country.class, new AnalysisEventListener<Country>() {
            @Override
            public void invoke(Country user, AnalysisContext context) {
                countryList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 生成SQL语句
//                generateSqlFile(countryList, countrySqlPath);
            }
        }).sheet(0).doRead();
        log.info("country size:{}", countryList.size());
    }

    @Test
    public void generateCountryCity() {
        String excelPath = "/Users/admin/Downloads/全球国家列表_latest.xlsx";  // Excel文件路径
        String citySqlPath = "cityOutput.sql";    // 输出的SQL文件路径

        List<City> cityList = new ArrayList<>();

        // 读取Excel文件
        EasyExcel.read(excelPath, City.class, new AnalysisEventListener<City>() {
            @Override
            public void invoke(City user, AnalysisContext context) {
                cityList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 生成SQL语句
//                generateCitySqlFile(cityList, citySqlPath);
            }
        }).sheet(1).doRead();
        log.info("city size:{}", cityList.size());
    }

    @Test
    public void generateAoMen() {
        String excelPath = "/Users/admin/Downloads/全球国家列表_latest.xlsx";  // Excel文件路径
        String countrySqlPath = "aoMenOutput.sql";    // 输出的SQL文件路径

        List<AoMen> aoMenList = new ArrayList<>();

        // 读取Excel文件
        EasyExcel.read(excelPath, AoMen.class, new AnalysisEventListener<AoMen>() {
            @Override
            public void invoke(AoMen user, AnalysisContext context) {
                aoMenList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 生成SQL语句
//                generateAoMenSqlFile(aoMenList, countrySqlPath);
            }
        }).sheet(3).doRead();
        log.info("aomen size:{}", aoMenList.size());
    }

    @Test
    public void generateTW() {
        String excelPath = "/Users/admin/Downloads/全球国家列表_latest.xlsx";  // Excel文件路径
        String countrySqlPath = "twOutput.sql";    // 输出的SQL文件路径

        List<Taiwan> aoMenList = new ArrayList<>();

        // 读取Excel文件
        EasyExcel.read(excelPath, Taiwan.class, new AnalysisEventListener<Taiwan>() {
            @Override
            public void invoke(Taiwan user, AnalysisContext context) {
                aoMenList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 生成SQL语句
                generateTwSqlFile(aoMenList, countrySqlPath);
            }
        }).sheet(4).doRead();
        log.info("aomen size:{}", aoMenList.size());
    }

    private static void generateCitySqlFile(List<City> userList, String sqlPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath))) {
            for (City user : userList) {
                String sql = String.format(
                        "select concat(\n" +
                                "       'insert into airstar.country_city(cn_country, simple_cn_country, en_country, country_code, iso_code, iso_code_long, en_region, hot_flag, high_risk, i_bank, purpose_mandatory, enable_status, created_time, modified_time)\n" +
                                "values (','\\'',cn_country, '\\',',\n" +
                                "       '\\'',simple_cn_country, '\\',',\n" +
                                "       '\\'',en_country, '\\',',\n" +
                                "       '\\'',country_code, '\\',',\n" +
                                "       '\\'',iso_code, '\\',',\n" +
                                "       '\\'',iso_code_long, '\\',',\n" +
                                "       '\\'','%s', '\\',',\n" +
                                "       hot_flag, ',',\n" +
                                "       high_risk, ',',\n" +
                                "       i_bank, ',',\n" +
                                "       purpose_mandatory, ',',\n" +
                                "       enable_status,',',\n" +
                                "       '1, now(), now());'\n" +
                                "       ) from airstar.country_city where en_country = '%s';",
                        user.getCity().trim(),
                        user.getCountry().trim()
                );
                writer.write(sql);
                writer.newLine();
            }
            System.out.println("SQL文件生成成功：" + sqlPath);
        } catch (IOException e) {
            System.err.println("生成SQL文件时发生错误：" + e.getMessage());
        }
    }

    private static void generateSqlFile(List<Country> userList, String sqlPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath))) {
            for (Country user : userList) {
                String sql = String.format(
                        "INSERT INTO airstar.country_city (cn_country, simple_cn_country, en_country, country_code, iso_code, iso_code_long,\n" +
                                "                                  hot_flag, high_risk, i_bank, purpose_mandatory, enable_status, created_time, modified_time)\n" +
                                "values ('%s', '%s', '%s', '%s', '%s', '%s',\n" +
                                "        %d, %d, %d, %d, 1, now(), now());",
                        user.getCn_tc_country(),
                        user.getCn_sc_country(),
                        user.getEn_country(),
                        user.getIso_code(),
                        user.getIso_code(),
                        user.getIso_code_long(),

                        isHot(user.getIso_code()),
                        isHightRisk(user.getHighrisk_country()),
                        isIbankk(user.getIban_mandatory()),
                        isPurposeMandatory(user.getPurpose_mandatory())
                );
                writer.write(sql);
                writer.newLine();
            }
            System.out.println("SQL文件生成成功：" + sqlPath);
        } catch (IOException e) {
            System.err.println("生成SQL文件时发生错误：" + e.getMessage());
        }
    }

    private static void generateAoMenSqlFile(List<AoMen> userList, String sqlPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath))) {
            for (AoMen user : userList) {
                String sql = String.format(
                        "INSERT INTO airstar.country_city (cn_country, simple_cn_country, en_country, country_code, iso_code, iso_code_long,\n" +
                                "                                  hot_flag, high_risk, i_bank, purpose_mandatory, enable_status, created_time, modified_time)\n" +
                                "values ('%s', '%s', '%s', 1, now(), now());",
                        user.getRegionNameTC(),
                        user.getRegionNameSC(),
                        user.getRegionNameEN()
                );
                writer.write(sql);
                writer.newLine();
            }
            System.out.println("SQL文件生成成功：" + sqlPath);
        } catch (IOException e) {
            System.err.println("生成SQL文件时发生错误：" + e.getMessage());
        }
    }

    private static void generateTwSqlFile(List<Taiwan> userList, String sqlPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath))) {
            for (Taiwan user : userList) {
                String sql = String.format(
                        "INSERT INTO airstar.country_city (cn_country, simple_cn_country, en_country, country_code, iso_code, iso_code_long,hot_flag, high_risk, i_bank, purpose_mandatory, enable_status, created_time, modified_time) values ('%s', '%s', '%s', '%s', '%s', '%s', 1, now(), now());",
                        user.getRegionNameTC(),
                        user.getRegionNameSC(),
                        user.getRegionNameEN(),
                        user.getRegionNameTC2(),
                        user.getRegionNameSC2(),
                        user.getRegionNameEN2()
                );
                writer.write(sql);
                writer.newLine();
            }
            System.out.println("SQL文件生成成功：" + sqlPath);
        } catch (IOException e) {
            System.err.println("生成SQL文件时发生错误：" + e.getMessage());
        }
    }

    private static Map<String, String> getRegionMap() {
        Map<String, String> regionMap = new HashMap<>();
        regionMap.put("AU", "IRN");
        regionMap.put("CA", "KPK");
        regionMap.put("JP", "SYR");
        regionMap.put("US", "UKR");
        regionMap.put("KR", "CUB");
        regionMap.put("TH", "VEN");
        regionMap.put("GB", "BLR");
        regionMap.put("SG", "LBY");
        regionMap.put("TW", "RUS");
        regionMap.put("MO", "ZWE");
        return regionMap;
    }

    private static Map<String, String> getCNRegionMap() {
        Map<String, String> regionMap = new HashMap<>();
        regionMap.put("北京市", "IRN");
        regionMap.put("天津市", "KPK");
        regionMap.put("河北省", "SYR");
        regionMap.put("山西省", "UKR");
        regionMap.put("内蒙古自治区", "CUB");
        regionMap.put("辽宁省", "VEN");
        regionMap.put("吉林省", "BLR");
        regionMap.put("黑龙江省", "LBY");
        regionMap.put("上海市", "RUS");
        regionMap.put("江苏省", "ZWE");
        regionMap.put("浙江省", "ZWE");
        regionMap.put("安徽省", "ZWE");
        regionMap.put("福建省", "ZWE");
        regionMap.put("江西省", "ZWE");
        regionMap.put("山东省", "ZWE");
        regionMap.put("河南省", "ZWE");
        regionMap.put("湖北省", "ZWE");
        regionMap.put("湖南省", "ZWE");
        regionMap.put("广东省", "ZWE");
        regionMap.put("广西壮族自治区", "ZWE");
        regionMap.put("海南省", "ZWE");
        regionMap.put("重庆市", "ZWE");
        regionMap.put("四川省", "ZWE");
        regionMap.put("贵州省", "ZWE");
        regionMap.put("云南省", "ZWE");
        regionMap.put("西藏自治区", "ZWE");
        regionMap.put("陕西省", "ZWE");
        regionMap.put("甘肃省", "ZWE");
        regionMap.put("青海省", "ZWE");
        regionMap.put("宁夏回族自治区", "ZWE");
        regionMap.put("新疆维吾尔自治区", "ZWE");
        regionMap.put("甘肃省", "ZWE");
        return regionMap;
    }

    private static int isHot(String isoCode) {
        Map<String, String> regionMap = getRegionMap();
        if (regionMap.containsKey(isoCode)) {
            return 1;
        }
        return 0;
    }

    private static int isHightRisk(String isHighRisk) {
        if (StringUtils.isNotBlank(isHighRisk) && "highrisk".equals(isHighRisk)) {
            return 1;
        }
        return 0;
    }

    private static int isIbankk(String isIbank) {
        if (StringUtils.isNotBlank(isIbank) && "yes".equalsIgnoreCase(isIbank)) {
            return 1;
        }
        return 0;
    }

    private static int isPurposeMandatory(String isPurposeMandatory) {
        if (StringUtils.isNotBlank(isPurposeMandatory) && "yes".equalsIgnoreCase(isPurposeMandatory)) {
            return 1;
        }
        return 0;
    }


    @Test
    public void importCouponPool() {
        String excelPath = "/Users/admin/Downloads/25年合作code码-2025年5月.xlsx";  // Excel文件路径
        String countrySqlPath = "xiaomi02061832CouponPool.sql";    // 输出的SQL文件路径
        String channelCode = "xiaomi02061832";

        List<CouponPool> couponPools = new ArrayList<>();

        // 读取Excel文件
        EasyExcel.read(excelPath, CouponPool.class, new AnalysisEventListener<CouponPool>() {
            @Override
            public void invoke(CouponPool couponPool, AnalysisContext context) {
                couponPools.add(couponPool);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 生成SQL语句
                generateCouponSqlFile(couponPools, countrySqlPath, channelCode);
            }
        }).sheet(0).doRead();
        log.info("coupool size:{}", couponPools.size());
    }

    private static void generateCouponSqlFile(List<CouponPool> couponPools, String sqlPath, String channelCode) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlPath))) {
            for (CouponPool couponPool : couponPools) {
                String sql = String.format(
                        "INSERT INTO account_info.consume_channel_coupon_pool (user_coupon_code, channel_coupon_code, channel_code, status, valid_start_time, valid_end_time, create_time, update_time) VALUES (null, '%s', '%s', 1, unix_timestamp('2024-12-27 00:00:00') * 1000, unix_timestamp('2025-12-31 23:59:58') * 1000, unix_timestamp() * 1000, unix_timestamp() * 1000);",
                        couponPool.getCode(),
                        channelCode
                );
                writer.write(sql);
                writer.newLine();
            }
            System.out.println("SQL文件生成成功：" + sqlPath);
        } catch (IOException e) {
            System.err.println("生成SQL文件时发生错误：" + e.getMessage());
        }
    }
}

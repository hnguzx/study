package main.java;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import main.model.City;
import main.model.Country;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String TABLE_NAME = "user";

    @Test
    public void generateCountry() {
        String excelPath = "C:\\Users\\谷志雄\\Downloads\\全球国家列表.xlsx";  // Excel文件路径
        String countrySqlPath = "countryOutput.sql";    // 输出的SQL文件路径
        String citySqlPath = "cityOutput.sql";    // 输出的SQL文件路径

        List<Country> userList = new ArrayList<>();

        // 读取Excel文件
        EasyExcel.read(excelPath, Country.class, new AnalysisEventListener<Country>() {
            @Override
            public void invoke(Country user, AnalysisContext context) {
                userList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 生成SQL语句
                generateSqlFile(userList, countrySqlPath);
            }
        }).sheet().doRead();
    }

    @Test
    public void generateCountryCity() {
        String excelPath = "C:\\Users\\谷志雄\\Downloads\\全球国家列表.xlsx";  // Excel文件路径
        String citySqlPath = "cityOutput.sql";    // 输出的SQL文件路径

        List<City> userList = new ArrayList<>();

        // 读取Excel文件
        EasyExcel.read(excelPath, City.class, new AnalysisEventListener<City>() {
            @Override
            public void invoke(City user, AnalysisContext context) {
                userList.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 生成SQL语句
                generateCitySqlFile(userList, citySqlPath);
            }
        }).sheet(1).doRead();
    }

    private static void generateCitySqlFile(List<City> userList, String sqlPath){
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
                        user.getCountryCity().split("=>")[1].trim(),
                        user.getCountryCity().split("=>")[0].trim()
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

    private static int isHot(String isoCode){
        Map<String, String> regionMap = getRegionMap();
        if(regionMap.containsKey(isoCode)){
            return 1;
        }
        return 0;
    }

    private static int isHightRisk(String isHighRisk) {
        if(StringUtils.isNotBlank(isHighRisk) && "highrisk".equals(isHighRisk)){
            return 1;
        }
        return 0;
    }

    private static int isIbankk(String isIbank) {
        if(StringUtils.isNotBlank(isIbank) && "yes".equalsIgnoreCase(isIbank)){
            return 1;
        }
        return 0;
    }

    private static int isPurposeMandatory(String isPurposeMandatory) {
        if(StringUtils.isNotBlank(isPurposeMandatory) && "yes".equalsIgnoreCase(isPurposeMandatory)){
            return 1;
        }
        return 0;
    }
}

package com.csvreader;


import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 利用javacsv2.0做导入导出csv文件工具类<br/>
 *
 *
 * @author
 *
 */
public class CSVUtil {

    static char separator = ',';

    public static void main(String[] args) throws Exception {

        // 测试导出
        String filePath = "E:\\IdeaProjects\\cs307_pro1\\src\\com\\csvreader\\contract_info.csv";
//        List<String[]> dataList = new ArrayList<String[]>();
//        for (int i = 0; i < 10; i++) {
//            dataList.add(new String[] { "0" + i, "小明" + i, "java" + i });
//        }
//        exportCsv(dataList, filePath);


        // 测试导入
        List<String[]> datas = importCsv(filePath);
        for (String[] strings : datas) {
            System.out.println(strings[0]);
        }
    }

    /**
     * java导入csv文件
     *
     * @param filePath
     *            导入路径
     * @return
     * @throws Exception
     */
    public static List<String[]> importCsv(String filePath) throws Exception {
        CsvReader reader = null;
        List<String[]> dataList = new ArrayList<String[]>();
        try {
            reader = new CsvReader(filePath, separator, Charset.forName("GBK"));

            // 读取表头
            reader.readHeaders();
            // 逐条读取记录，直至读完
            while (reader.readRecord()) {
                dataList.add(reader.getRawRecord().split(","));
                // // 下面是几个常用的方法
                // 读取一条记录
                System.out.println(reader.getRawRecord());
                // 按列名读取这条记录的值
                System.out.println(reader.get(0));
                System.out.println(reader.get(1));
                System.out.println(reader.get(2));
                System.out.println(reader.get(3));
            }
        } catch (Exception e) {
            System.out.println("读取CSV出错..." + e);
            throw e;
        } finally {
            if (null != reader) {
                reader.close();
            }
        }

        return dataList;
    }

    /**
     * java导出cvs文件
     *
     * @param dataList
     *            数据集
     * @param filePath
     *            导出路径
     * @return
     * @throws Exception
     */
    public static boolean exportCsv(List<String[]> dataList, String filePath) throws Exception {
        boolean isSuccess = false;
        CsvWriter writer = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath, true);
            writer = new CsvWriter(out, separator, Charset.forName("GBK"));
            for (String[] strs : dataList) {
                writer.writeRecord(strs);
            }

            isSuccess = true;
        } catch (Exception e) {
            System.out.println("生成CSV出错..." + e);
            throw e;
        } finally {
            if (null != writer) {
                writer.close();
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("exportCsv close Exception: " + e);
                    throw e;
                }
            }
        }


        return isSuccess;
    }

}

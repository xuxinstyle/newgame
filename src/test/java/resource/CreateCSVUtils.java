package resource;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CreateCSVUtils {
    /**
     * 创建CSV文件类型
     * @param dataLists
     * @return
     */
 
    public static File createCSVFile(List<Object> dataLists, List<Object>   headList) throws IOException {
 
        File csvFile = null;
        BufferedWriter csvWrite = null;


        try {
 
            //定义文件类型
            csvFile = new File("src/test/java/resource/TestResource.csv");
            //去文件目录
            File parent = csvFile.getParentFile();
            if (parent.exists()) {
                parent.mkdirs();
 
            }
 
            //创建文件
            csvFile.createNewFile();
            csvWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
 
            //写入表头
            write(headList, csvWrite);
            //写入数据
 
            for ( Object dataList:dataLists){
            write((List<Object>)dataList, csvWrite);
            }
            csvWrite.flush();
        } catch (IOException e) {
            throw  new IOException("文件生成失败");
        } finally {
 
            try {
                csvWrite.close();
 
            } catch (IOException e) {
 
                throw  new IOException("关闭文件流失败");
            }
        }
 
        return csvFile;
    }
 
 
 
    /**
     * 将数据按行写入数据
     *
     * @param dataList
     * @param csvWreite
     * @throws IOException
     */
    private static void write(List<Object> dataList,BufferedWriter csvWreite) throws IOException {
 
        for (Object data: dataList) {
            StringBuffer buffer=new StringBuffer();
            String rowStr=buffer.append("\"").append(data).append("\",").toString();
            csvWreite.write(rowStr);
//            csvWreite.newLine();
        }
        csvWreite.newLine();
    }
 
}
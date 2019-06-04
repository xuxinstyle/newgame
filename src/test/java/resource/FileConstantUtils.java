package resource;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * CSV常量工具类
 */
public class FileConstantUtils {

    public final static List<Object> MEASURE_HEAD_LIST   = Arrays.asList(
            "客户ID",
            "产品ID",
            "产品类型"
    );
 
    // 导出文件路径
    public final static String  DOWNLOAD_FILE_PATH  = "src/test/java/resource/TestResource.xlsx";
    public final static String FILE_NAME = "存疑报表";
}
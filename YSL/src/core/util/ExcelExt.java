package core.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 类描述：Excel导入导出工具扩展类
 * 
 * @author: FBL @date： 2016/06/11
 * @version 1.0
 */
public class ExcelExt {

    /**
     * csv文件导入
     * 
     * @param fileName
     * @return
     */
    public static List<Map<String, String>> ImportExcel(InputStream is, String extension) {

        // 返回对象定义
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
        Map<String, String> resultMap;
        Map<String, Integer> colMap = new HashMap<String, Integer>();

        // 文件路径
        //String path = ServletActionContext.getServletContext().getRealPath("/upload") + "\\" + fileName;
        PoiExcelHelper helper = getPoiExcelHelper(extension);

        // 读取excel文件数据(最多28列)
        int maxCol = 28;
        ArrayList<ArrayList<String>> dataListList = helper.readExcel(is, 0, "5-", new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "aa", "ab" });

        
        if(dataListList.size() < 1){
            return resultMapList;
        }
        // 数据列ID解析
        String colKey = null;
        for (int col = 0; col < maxCol; col++) {

            colKey = dataListList.get(0).get(col);

            if (colKey == null || "".equals(colKey)) {
                // 列key没有的情况下不做后续处理
                break;
            }

            // 列key全部变成大写，以便匹配方便
            colKey = colKey.trim().toUpperCase();

            if (!colMap.containsKey(colKey)) {
                colMap.put(colKey, col);
            }
        }

        // 数据解析
        String value = null;
        List<String> dataList = null;
        Integer colIndex;
        for (int row = 1; row < dataListList.size(); row++) {

            resultMap = new HashMap<String, String>();
            dataList = dataListList.get(row);

            // 根据列key遍历
            for (String key : colMap.keySet()) {
                colIndex = colMap.get(key);
                value = dataList.get(colIndex).trim();
                resultMap.put(key, value);
            }

            resultMapList.add(resultMap);
        }
        return resultMapList;
    }

    /**
     * 获取Excel处理类
     * 
     * @param filePath
     * @return
     */
    private static PoiExcelHelper getPoiExcelHelper(String extension) {
        PoiExcelHelper helper;
        if (extension.indexOf("xlsx") != -1) {
            helper = new PoiExcel2k7Helper();
        } else {
            helper = new PoiExcel2k3Helper();
        }
        return helper;
    }
    
    /**
     * 输出Response
     * @param jsonMap
     * @throws IOException
     */
//    public static void outResponse(Map<String, Object> jsonMap) throws IOException {
//        JSONObject jsonObject = JSONObject.fromObject(jsonMap);
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setContentType("text/html");
//        response.setCharacterEncoding("UTF-8"); 
//        response.getWriter().write(jsonObject.toString());
//        response.getWriter().flush();
//        response.getWriter().close();
//    }
}

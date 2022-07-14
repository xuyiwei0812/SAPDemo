import com.sap.conn.jco.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * 从SAP中通过RFC查询表，并输出到.csv文件
 */

public class RFCdemo {
    public static void main(String[] args) throws JCoException, FileNotFoundException, UnsupportedEncodingException {
        String L_DATE = "20200501";
        String H_DATE = "20200503";

        JCoFunction function=null;
        try {
            JCoDestination destination = JCoDestinationManager.getDestination("ECC");
            //调用function
            function = destination.getRepository().getFunction("Z_RFC_FI_BANKINCOME_LIST_STHZ");
            //获取输入参数列表
            JCoParameterList input = function.getImportParameterList();
            //设置输入查询参数
            input.setValue("L_DATE", L_DATE);
            input.setValue("H_DATE", H_DATE);
            function.execute(destination);
        } catch (JCoException e) {
            e.printStackTrace();
        }
        //查询表
        JCoTable tblexport = function.getTableParameterList().getTable("IT_INCOMELIST");
        System.out.println(tblexport);
        //表的行数
        System.out.println("num:"+tblexport.getNumRows());

        //打印表中的ITEM字段
        for (int i = 0; i < tblexport.getNumRows(); i++) {
            tblexport.setRow(i);
            String FI_INCOME = tblexport.getValue("ITEM").toString();
            System.out.println(FI_INCOME);
        }
        System.out.println(tblexport);

        //打印到csv
        PrintStream ps = new PrintStream("C:\\Users\\t0yxu\\IdeaProjects\\2.csv","utf-8");
        System.setOut(ps);
        //这四个字符使得不乱码
        System.out.println(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
        System.out.println(tblexport);
        ps.close();

    }
}

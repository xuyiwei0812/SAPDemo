import com.sap.conn.jco.*;

import java.io.*;
import java.util.Date;
import java.util.TimerTask;

public class DoSomethingTimerTask extends TimerTask {

    private String taskName;

    public DoSomethingTimerTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println(new Date() + " : 任务「" + taskName + "」被执行。");

        String L_DATE = "20200501";
        String H_DATE = "20200503";

        JCoFunction function=null;
        try {
            JCoDestination destination = JCoDestinationManager.getDestination("ECC");
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
        // 表输出
        JCoTable tblexport = function.getTableParameterList().getTable("IT_INCOMELIST");
        System.out.println("num:"+tblexport.getNumRows());

        for (int i = 0; i < tblexport.getNumRows(); i++) {
            tblexport.setRow(i);
            String FI_INCOME = tblexport.getValue("ITEM").toString();
            //System.out.println(FI_INCOME);
            //tblexport.getString(字段名)也可以
        }
        System.out.println(tblexport);

        String b = new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF });
        String tblexportString = b + tblexport.toString();

        FileOutputStream o = null;
        String path="C:\\Users\\t0yxu\\IdeaProjects\\";
        String filename="3.csv";
        byte[] buff = new byte[]{};
        try{
            File file = new File(path+filename);
            if(!file.exists()){
                file.createNewFile();
            }
            buff=tblexportString.getBytes();
            o=new FileOutputStream(file,false);//false覆盖，true不覆盖
            o.write(buff);
            o.flush();
            o.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}

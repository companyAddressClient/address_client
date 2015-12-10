package com.xyf.AddressList.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.widget.Toast;
import com.xyf.AddressList.R;
import com.xyf.AddressList.bean.ContactsBean;
import com.xyf.AddressList.database.AddressModel;
import com.xyf.AddressList.resources.XResources;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by shxiayf on 2015/12/4.
 */
public class XlsUtils {

    private static final class Utils {
        public static final XlsUtils INSTANCES = new XlsUtils();
    }

    public static XlsUtils getInstances()
    {
        return Utils.INSTANCES;
    }

    public String getUpdateXlsPath(Context mContext)
    {
        try{
            return mContext.getExternalCacheDir().getAbsolutePath();
        }catch (Exception e)
        {
            try{
                return Environment.getExternalStorageDirectory().getAbsolutePath();
            }
            catch (Exception e2)
            {
                return mContext.getFilesDir().getAbsolutePath();
            }
        }
    }

    public int ImportDefalutXls(Context mContext)
    {
        try{
            String xlsType = "";
            InputStream is = null;

            String downloadXlsPath = getUpdateXlsPath(mContext) + File.separator + "address.xls";
            File downloadXlsFile = new File(downloadXlsPath);
            if (downloadXlsFile.exists())
            {
                LogUtils.i(XlsUtils.class.getName(),"downloadXlsPath:"+downloadXlsPath);
                is = new FileInputStream(downloadXlsFile);
                xlsType = "xls";
            }
            else
            {
                AssetManager assets = mContext.getAssets();
                String[] files = assets.list("");

                for (String fileName : files)
                {
                    if (fileName.equals(ConfigUtils.DEFAULT_XLS_NAME))
                    {
                        xlsType = "xls";
                        is = assets.open(fileName);
                        LogUtils.i(XlsUtils.class.getName(),"assets file");
                        break;
                    }
                    else if (fileName.equals(ConfigUtils.DEFAULT_XLSX_NAME))
                    {
                        xlsType = "xlsx";
                        is = assets.open(fileName);
                        break;
                    }
                    else
                    {
                        return -1;
                    }
                }
            }


            if (xlsType.equals("xls"))
            {
                Workbook wb = null;
                wb = Workbook.getWorkbook(is);
                Sheet sht = wb.getSheet(XResources.getString(mContext,ConfigUtils.SHEET_NAME)); // name or number

                int coloums = sht.getColumns();
                int rows = sht.getRows();

                Cell currentCell = null;
                String addressname = XResources.getString(mContext, ConfigUtils.ADDRESS_USERNAME);
                String jobname = XResources.getString(mContext, ConfigUtils.ADDRESS_JOB);
                String mobilephonename = XResources.getString(mContext, ConfigUtils.ADDRESS_MOBILEPHONE);
                String workphonename = XResources.getString(mContext, ConfigUtils.ADDRESS_WORKPHONE);

                LogUtils.i(XlsUtils.class.getName(), "************************************");
                LogUtils.i(XlsUtils.class.getName(),String.format("row[%d],columns[%d]",rows,coloums));

                //delete address db
                DBUtils.getInstances().deleteAll(mContext, AddressModel.TableName);

                for (int i= 1;i< rows ; i++)
                {
                    LogUtils.i(XlsUtils.class.getName(),"####################");
                    ContactsBean tmp = new ContactsBean();
                    for (int j = 0;j<coloums ; j++)
                    {
                        Cell topCell = sht.getCell(j,0);
                        String topName = topCell.getContents();
                        currentCell = sht.getCell(j,i);
//                        LogUtils.i(XlsUtils.class.getName(),topName);
                        if (topName.equals(addressname))
                        {
                            tmp.setName(currentCell.getContents());
                            LogUtils.i(XlsUtils.class.getName(),String.format("%s:%s",addressname,tmp.getName()));
                        }
                        else if (topName.equals(jobname))
                        {
                            tmp.setJob(currentCell.getContents());
                            LogUtils.i(XlsUtils.class.getName(),String.format("%s:%s",jobname,tmp.getJob()));
                        }
                        else if (topName.equals(mobilephonename))
                        {
                            tmp.setMobilephone(currentCell.getContents());
                            LogUtils.i(XlsUtils.class.getName(),String.format("%s:%s",mobilephonename,tmp.getMobilephone()));
                        }
                        else if (topName.equals(workphonename))
                        {
                            tmp.setWorkphone(currentCell.getContents());
                            LogUtils.i(XlsUtils.class.getName(),String.format("%s:%s",workphonename,tmp.getWorkphone()));
                        }
                        else
                        {
                        }

                    }

                    //insert to db

                    DBUtils.getInstances().InsertAddress(mContext,tmp);

                    LogUtils.i(XlsUtils.class.getName(),"####################");
                }
                LogUtils.i(XlsUtils.class.getName(),"************************************");

            }
            else
            {
                return -2;
            }



            return 1;
        }catch (Exception e)
        {
            LogUtils.error(e);
        }

        return 0;
    }

}

package com.xyf.AddressList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.xyf.AddressList.adapter.AddressListAdapter;
import com.xyf.AddressList.bean.ContactsBean;
import com.xyf.AddressList.bean.UpdateRequestBean;
import com.xyf.AddressList.bean.UpdateResponseBean;
import com.xyf.AddressList.utils.*;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by shxiayf on 2015/12/1.
 */
public class AddressListActivity extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener{

    private static final String TAG = AddressListActivity.class.getSimpleName();

    private ListView m_AddressList;
    private TextView m_Empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addresslist);

        initViews();
        initDatas();

        checkUpdate();

    }

    private void checkUpdate()
    {
        if (System.currentTimeMillis() - SharedPrefUtils.getInstances().getLastUpdateTime(this) >= ConfigUtils.UPDATE_SPEED_TIME)
        {
            LogUtils.i(TAG,"update xls ...");
            showProgress("check xls for update ...");
            UpdateRequestBean requestBean = new UpdateRequestBean();
            requestBean.setPackageName(getPackageName());
            requestBean.setXlsVersion(SharedPrefUtils.getInstances().getXlsVersion(this));
            requestBean.setAppVersion(ApkUtils.getVersion(this));

            HttpUrlUtils.getInstances().Post(ConfigUtils.UPDATE_URL, JsonUtils.parseObj2String(requestBean), new HttpUrlUtils.HttpResultListener() {
                @Override
                public void onResponse(InputStream is) {
                    dismissDialog();

                    String responseContent = HttpUrlUtils.getInstances().parseStream2String(is);
                    UpdateResponseBean responseBean = (UpdateResponseBean) JsonUtils.parseString2Obj(responseContent,UpdateResponseBean.class);

                    if (responseBean.getIsUpdate() == 1)
                    {
                        updateXls(responseBean);
                    }
                    else
                    {
                        if (SharedPrefUtils.getInstances().isNeedImportDefautXLS(AddressListActivity.this))
                        {
                            importDefaultXLS();
                        }
                    }

                }
            });
        }
        else
        {
            if (SharedPrefUtils.getInstances().isNeedImportDefautXLS(this))
            {
                importDefaultXLS();
            }
        }

    }


    private void updateXls(final UpdateResponseBean responseBean)
    {
        showProgress("download xls ...");
        new Thread(new Runnable() {
            @Override
            public void run() {

                URL mUrl = null;
                HttpURLConnection conn = null;
                RandomAccessFile raf = null;
                try{
                    mUrl = new URL(responseBean.getDownUrl());
                    conn = (HttpURLConnection) mUrl.openConnection();

                    String filePath = XlsUtils.getInstances().getUpdateXlsPath(AddressListActivity.this) + File.separator + "address.xls" ;
                    LogUtils.i(TAG,"filepath:"+filePath);
                    File dstFile = new File(filePath);
                    if (dstFile.exists())
                    {
                        dstFile.delete();
                    }
                    raf = new RandomAccessFile(filePath,"rw");

                    InputStream is = conn.getInputStream();
                    int rs = -1;
                    byte[] buf = new byte[1024];

                    while ((rs = is.read(buf)) != -1)
                    {
                        raf.write(buf,0,rs);
                    }

                    is.close();
                    raf.close();


                    SharedPrefUtils.getInstances().setLastUpdateTime(AddressListActivity.this,System.currentTimeMillis());
                    SharedPrefUtils.getInstances().setXlsVersion(AddressListActivity.this,responseBean.getXlsVersion());


                    importNewXLS();

                }
                catch (Exception e)
                {
                    LogUtils.error(e);
                }

                dismissDialog();
            }
        }).start();

    }

    private ProgressDialog mDialog;
    private void showProgress(final String message)
    {
        getUiHanlder().post(new Runnable() {
            @Override
            public void run() {
                if (mDialog == null)
                {
                    mDialog = new ProgressDialog(AddressListActivity.this);
                    mDialog.setMessage(message);
                    mDialog.setIndeterminate(true);
                    mDialog.setCancelable(false);
                    mDialog.show();
                }
            }
        });

    }

    private void dismissDialog()
    {
        getUiHanlder().post(new Runnable() {
            @Override
            public void run() {
                if (mDialog != null)
                {
                    mDialog.dismiss();
                    mDialog = null;
                }
            }
        });
    }

    private void importDefaultXLS()
    {
        SharedPrefUtils.getInstances().setNeedImportDefaultXLS(this,false);
        showProgress("import default address ...");

        int result = XlsUtils.getInstances().ImportDefalutXls(this);
        LogUtils.i(TAG,"import result:" + result);

        dismissDialog();

        updateList();
    }

    private void importNewXLS()
    {
        showProgress("import new address ...");

        int result = XlsUtils.getInstances().ImportDefalutXls(this);
        LogUtils.i(TAG,"import result:" + result);

        dismissDialog();

        getUiHanlder().post(new Runnable() {
            @Override
            public void run() {
                updateList();
            }
        });

    }


    private List<ContactsBean> m_datas;

    private void initDatas() {
        m_datas = DBUtils.getInstances().QueryAddressList(this);

        if (m_datas.size() == 0)
        {
            m_Empty.setVisibility(View.VISIBLE);
            m_AddressList.setVisibility(View.GONE);
        }
        else
        {
            m_Empty.setVisibility(View.GONE);
            m_AddressList.setVisibility(View.VISIBLE);
        }

        m_AddressList.setAdapter(new AddressListAdapter(this,m_datas));
    }

    private void updateList()
    {
        LogUtils.i(TAG,"update List ...");

        if (m_AddressList != null)
        {
            m_datas = DBUtils.getInstances().QueryAddressList(this);

            if (m_datas.size() == 0)
            {
                m_Empty.setVisibility(View.VISIBLE);
                m_AddressList.setVisibility(View.GONE);
            }
            else
            {
                m_Empty.setVisibility(View.GONE);
                m_AddressList.setVisibility(View.VISIBLE);
            }

            ((AddressListAdapter)m_AddressList.getAdapter()).setmAddresses(m_datas);
            ((AddressListAdapter)m_AddressList.getAdapter()).notifyDataSetChanged();
        }

    }

    private void initViews() {

        m_AddressList = (ListView) findViewById(R.id.addresslist_list);
        m_Empty = (TextView) findViewById(R.id.addresslist_empty);


        m_AddressList.setOnItemClickListener(this);
        m_Empty.setOnClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        ContactsBean currentBean = (ContactsBean) m_AddressList.getAdapter().getItem(i);

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + currentBean.getMobilephone()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addresslist_empty)
        {
            importDefaultXLS();
        }
    }
}

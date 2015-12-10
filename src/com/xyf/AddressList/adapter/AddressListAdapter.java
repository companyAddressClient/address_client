package com.xyf.AddressList.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xyf.AddressList.R;
import com.xyf.AddressList.bean.ContactsBean;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by shxiayf on 2015/12/2.
 */
public class AddressListAdapter extends BaseAdapter {

    private List<ContactsBean> mAddresses;
    private Context mContext;

    public AddressListAdapter(Context context,List<ContactsBean> list)
    {
        this.mAddresses = list;
        this.mContext = context;
    }

    public void setmAddresses(List<ContactsBean> mAddresses) {
        this.mAddresses = mAddresses;
    }

    @Override
    public int getCount() {
        if (mAddresses != null)
        {
            return mAddresses.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (mAddresses != null)
        {
            return mAddresses.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mHolder = null;
        if (view == null)
        {
            mHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.addresslist_item,null);
            mHolder.addresslist_item_add = (TextView) view.findViewById(R.id.addresslist_item_add);
            mHolder.addresslist_item_layout = (RelativeLayout) view.findViewById(R.id.addresslist_item_layout);
            mHolder.addresslist_item_name = (TextView) view.findViewById(R.id.addresslist_item_name);
            mHolder.addresslist_item_mobilephone = (TextView) view.findViewById(R.id.addresslist_item_mobilephone);
            mHolder.addresslist_item_job = (TextView) view.findViewById(R.id.addresslist_item_job);

            view.setTag(mHolder);
        }
        else
        {
            mHolder = (ViewHolder) view.getTag();
        }

//        if (i == 0)
//        {
//            mHolder.addresslist_item_layout.setVisibility(View.GONE);
//            mHolder.addresslist_item_add.setVisibility(View.VISIBLE);
//        }
//        else
//        {
            mHolder.addresslist_item_layout.setVisibility(View.VISIBLE);
            mHolder.addresslist_item_add.setVisibility(View.GONE);
            ContactsBean currentBean = mAddresses.get(i);

            mHolder.addresslist_item_name.setText(currentBean.getName());
            mHolder.addresslist_item_mobilephone.setText(currentBean.getMobilephone());
            mHolder.addresslist_item_job.setText(currentBean.getJob());

//        }

        return view;
    }

    class ViewHolder
    {
        TextView addresslist_item_add;
        RelativeLayout addresslist_item_layout;
        TextView addresslist_item_name;
        TextView addresslist_item_mobilephone;
        TextView addresslist_item_job;
    }

}

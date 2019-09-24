package com.runtime_terror.myapplication.interfaces;

import com.runtime_terror.myapplication.models.ProductItem;

import java.util.List;

public interface EditItemInterface {
    int getQty();
    void setQty(int quantity);
    String getReqs();
    void  setReqs(String reqs);
    List<Object> getDataSet();
    int getItemPosition();
    void dialogNotifyItemRemoved(int position);
    public void itemChanged();
    String getTranslation(int resource);
    boolean isOrdered(ProductItem item );

}

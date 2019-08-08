package com.runtime_terror.myapplication;

import java.util.List;

public interface EditItemInterface {
    public int getQty();
    public void setQty(int quantity);
    public String getReqs();
    public void  setReqs(String reqs);
    public List<Object> getDataSet();
    public int getItemPosition();
    public void dialogNotifyItemRemoved(int position);
    public String getTranslation(int resource);

}

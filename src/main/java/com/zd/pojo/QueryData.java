package com.zd.pojo;

/**
 * Created by Administrator on 2017-04-24.
 */
public class QueryData {

    private String pack_no;
    private String pack_barcode;
    private String type;
    private String goods_id;

    public String getPack_no() {
        return pack_no;
    }
    public void setPack_no(String pack_no) {
        this.pack_no = pack_no;
    }

    public String getPack_barcode() {
        return pack_barcode;
    }
    public void setPack_barcode(String pack_barcode) {
        this.pack_barcode = pack_barcode;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getGoods_id() {
        return goods_id;
    }
    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }
}

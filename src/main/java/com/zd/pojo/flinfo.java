package com.zd.pojo;

import java.io.Serializable;

/**
 * Created by zhangxiusheng on 17/3/21.
 */
public class flinfo implements Serializable {
    private String _flName = "";

    public void set_flName( String _flName ) {
        this._flName = _flName;
    }

    public String get_flName() {
        return _flName;
    }
}

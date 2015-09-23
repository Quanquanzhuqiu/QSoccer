package com.qqzq.widget.time;

import java.io.Serializable;

/**
 * Created by jie.xiao on 15/9/21.
 */
public class PickerItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int type;
    private String value;

    public PickerItem(int type, int id, String value) {
        super();
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public PickerItem() {
        super();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "id = " + id + ", type = " + type + ", value = " + value;
    }
}

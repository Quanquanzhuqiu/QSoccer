package com.qqzq;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jie.xiao on 9/2/2015.
 */
public class DataSerializerTest extends TestCase{

    public void testSerialize(){
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String s = sFormat.format(new Date());
        assertNotNull(s);
        System.out.println(s);
    }

}

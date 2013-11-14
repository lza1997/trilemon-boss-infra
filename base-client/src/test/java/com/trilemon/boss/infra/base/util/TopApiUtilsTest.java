package com.trilemon.boss.infra.base.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author kevin
 */
public class TopApiUtilsTest {
    @Test
    public  void testGetItemUrlNumIid(){
        String url="http://item.taobao.com/item.htm?spm=0.0.0.0.u2MBxc&id=19440841598";
        assertEquals(19440841598L,(long)TopApiUtils.getItemUrlNumIid(url));
    }
}

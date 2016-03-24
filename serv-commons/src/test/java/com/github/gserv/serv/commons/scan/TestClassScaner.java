package com.github.gserv.serv.commons.scan;

import com.github.gserv.serv.commons.beanvali.MessageValidateService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by shiying on 2016/3/24.
 */
public class TestClassScaner {

    /**
     * 类扫描测试
     * @throws IOException
     */
    @Test
    public void test_scan_class() throws IOException {
        new ClassScaner().scan("com.github", new ClassScaner.ClassScanerAction<Object>() {
            @Override
            public void action(Class<Object> cls) {
                try {
                    Assert.assertFalse(cls.isInterface());
                } catch (Throwable e ) {
                    e.printStackTrace();
                }
            }
        }, true, null);
    }

    /**
     * 接口扫描测试
     * @throws IOException
     */
    @Test
    public void test_scan_interface() throws IOException {
        new ClassScaner().scan("com.github", new ClassScaner.ClassScanerAction<Object>() {
            @Override
            public void action(Class<Object> cls) {
                try {
                    Assert.assertTrue(cls.isInterface());
                } catch (Throwable e ) {
                    e.printStackTrace();
                }
            }
        }, false, null);
    }

    /**
     * 子类扫描测试
     * @throws IOException
     */
    @Test
    public void test_sacn_subClass() throws IOException {
        new ClassScaner().scan("com.github", new ClassScaner.ClassScanerAction<MessageValidateService>() {
            @Override
            public void action(Class<MessageValidateService> cls) {
                try {
                    Assert.assertTrue(cls.newInstance() instanceof MessageValidateService);
                } catch (Throwable e ) {
                    e.printStackTrace();
                }
            }
        }, true, MessageValidateService.class);
    }


}

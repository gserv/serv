package com.github.gserv.serv.web.shiro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by shiying on 2016/3/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/web/shiro/applicationContext.xml")
public class TestRetryLimitHashedCredentialsMatcher {

    @Resource(name="credentialsMatcher")
    private RetryLimitHashedCredentialsMatcher credentialsMatcher;

    @Test
    public void test() {
        String code = credentialsMatcher.generatorCode("admin", "salt");
        System.out.println(code);
    }


}

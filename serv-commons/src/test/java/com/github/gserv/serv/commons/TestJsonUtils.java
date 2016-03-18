package com.github.gserv.serv.commons;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiying on 2016/3/16.
 */
public class TestJsonUtils {

    public static class TestV {
        private int a;
        private String s;
        public int getA() {
            return a;
        }
        public void setA(int a) {
            this.a = a;
        }
        public String getS() {
            return s;
        }
        public void setS(String s) {
            this.s = s;
        }
    }

    @Test
    public void test_parseJson() {
        String json = "[{\"a\":1,\"s\":\"sss\"},{\"a\":2,\"s\":\"sss2\"}]";
        @SuppressWarnings("unchecked")
		List<TestV> list = JsonUtils.parseJson(json, ArrayList.class, TestV.class);
        for (TestV v : list) {
            System.out.println(JsonUtils.toJson(v));
        }
    }
}

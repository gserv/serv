package serv.commons;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by shiying on 2016/3/16.
 */
public class TestHttpUtils {

    @Test
    public void test_download() throws IOException {
        File file = HttpUtils.download("https://www.baidu.com/img/bd_logo1.png");
        System.out.print(file);
    }

}

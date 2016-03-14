package serv.commons.beanvali;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shiying on 2016/3/13.
 */
public class TestDefaultMessageValidateService {

    DefaultMessageValidateService valid = new DefaultMessageValidateService();

    @Test
    public void test() throws Exception {
//		List<RevcTextMessage> list = Arrays.asList(new RevcTextMessage[] {
//				new RevcTextMessage("to", "from", new Date(), MessageType.text, "", "text string")
//		});
//		for (RevcTextMessage msg : list) {
//			Map<String, Object> redate = new HashMap<String, Object>();
//			if (validate("[toUserName=to1] [fromUserName=to2] [content?=.*(xt.*)]", msg, redate)) {
//				System.out.println(msg);
//				System.out.println("redate : " + redate);
//			}
//		}
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("toUserName", "to1");
        obj.put("fromUserName", "to2");
        Map<String, Object> chi = new HashMap<String, Object>();
        chi.put("cc", "aa");
        obj.put("chi", chi);
//		System.out.println(validate("[nn=to2][toUserName=to1][fromUserName=to2]", obj));
        System.out.println(valid.validate("[chi.cc=aa]", obj));
    }

}

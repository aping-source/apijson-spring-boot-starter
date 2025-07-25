//package io.yunjiao.apijson.framework;
//
//import cn.hutool.core.util.IdUtil;
//import com.alibaba.fastjson2.JSONObject;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.Test;
//
//import java.util.Date;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThatCode;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
///**
// * {@link RemoteFunction} 单元测试用例
// *
// * @author yangyunjiao
// */
//public class RemoteFunctionTest {
//    private final RemoteFunction remoteFunction = new RemoteFunction() {
//        @Override
//        public String getArgStr(String path) {
//            return null;
//        }
//
//        @Override
//        public HttpSession getSession() {
//            return null;
//        }
//    };
//
//    @Test
//    public void givenNumberOrString_whenVerifyIdList_thenSuccess() {
//        JSONObject json = new JSONObject();
//
//        List<Integer> numberIdList = List.of(1, 2, 3, 4);
//        json.put("numberIdList", numberIdList);
//        assertThatCode(() -> remoteFunction.verifyIdList(json, "numberIdList")).doesNotThrowAnyException();
//
//        List<String> stringIdList = List.of("id1", "id2", IdUtil.fastSimpleUUID(), IdUtil.getSnowflakeNextIdStr());
//        json.put("stringIdList", stringIdList);
//        assertThatCode(() -> remoteFunction.verifyIdList(json, "stringIdList")).doesNotThrowAnyException();
//    }
//
//    @Test
//    public void givenDate_whenVerifyIdList_thenException() {
//        JSONObject json = new JSONObject();
//
//        List<Date> dateIdList = List.of(new Date(1752913260000L), new Date(1752913270000L), new Date(1752913280000L));
//        json.put("dateIdList", dateIdList);
//
//        assertThatThrownBy(() -> remoteFunction.verifyIdList(json, "dateIdList"))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining(" 不符合 Number 或 String 类型!");
//    }
//
//    @Test
//    public void givenNot_whenVerifyIdList_thenException() {
//        JSONObject json = new JSONObject();
//
//        List<String> urlList = List.of("http://www.baidu.com", "https://gitee.com/APIJSON1", "abc", "https://gitee.com/APIJSON");
//        json.put("urlList", urlList);
//
//        assertThatThrownBy(() -> remoteFunction.verifyURLList(json, "urlList"))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("urlList/2: abc 不符合 URL 字符串格式!");
//    }
//}

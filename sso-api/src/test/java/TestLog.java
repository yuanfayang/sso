import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Package : PACKAGE_NAME
 * @Author : Fayang Yuan
 * @Email : fayang.yuan@changhong.com ,flyyuanfayang@sina.com
 * @Date : 16/3/11
 * @Time : 下午3:36
 * @Description :
 */
public class TestLog {

    private static final Logger logger= LoggerFactory.getLogger(TestLog.class);

    @Test
    public void testLog(){
        logger.info("测试info");
        logger.debug("测试debug");
        logger.trace("测试trace");
        logger.error("测试error");
    }
}

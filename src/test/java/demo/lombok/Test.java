package demo.lombok;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: madali
 * @Date: 2018/11/5 11:21
 */
@Slf4j
public class Test {

    public static void main(String[] args) {
        LomEntity lomEntity = new LomEntity("name", false, true);
        System.out.println(lomEntity);
        log.info("slf4j");
    }

}

package demo.lombok;

import lombok.*;

/**
 * @Auther: madali
 * @Date: 2018/11/5 11:18
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LomEntity {

    private String name;

    private boolean flag;

    private Boolean needFlag;

}

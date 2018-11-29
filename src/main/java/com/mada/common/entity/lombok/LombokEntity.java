package com.mada.common.entity.lombok;

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
public class LombokEntity {

    private String name;

    private boolean flag;

    private Boolean needFlag;

    private int num;

}

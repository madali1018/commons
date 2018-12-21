package com.mada.commons.entity.lombok;

import lombok.*;

/**
 * @Auther: madali
 * @Date: 2018/11/5 11:18
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LombokEntity {

    private String name;

    private boolean flag;

    private Boolean needFlag;

    private int num;

}

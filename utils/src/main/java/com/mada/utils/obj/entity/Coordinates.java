package com.mada.utils.obj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by madali on 2019/4/30 10:30
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {

    // 经度
    private double lon;
    // 纬度
    private double lat;

}

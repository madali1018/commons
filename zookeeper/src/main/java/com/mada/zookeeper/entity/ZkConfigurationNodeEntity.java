package com.mada.zookeeper.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by madali on 2017/4/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZkConfigurationNodeEntity {

    private String key;
    private String value;

}

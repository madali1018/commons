package com.mada.zookeeper.entity;

import com.mada.zookeeper.enumeration.ServerStateEnum;
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
public class ZkConnectionNodeEntity {

    private String id;
    private String ip;
    private int port;
    private ServerStateEnum serverStateEnum;

}

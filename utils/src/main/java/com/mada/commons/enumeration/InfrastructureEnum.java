package com.mada.commons.enumeration;

/**
 * Created by madali on 2017/5/1.
 */
public enum InfrastructureEnum {

    //mongodb服务
    Mongodb(0),

    //kafka服务
    Kafka(1),

    //redis服务
    Redis(2),

    //mysql服务
    Mysql(3);

    private final int value;

    private InfrastructureEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public String getZookeeperNodeName() {
        String name = null;
        switch (this) {
            case Mongodb:
                name = "mongodb";
                break;
            case Kafka:
                name = "kafka";
                break;
            case Redis:
                name = "redis";
                break;
            case Mysql:
                name = "mysql";
                break;
        }
        return name;
    }

    public String description() {
        return this.name();
    }
}

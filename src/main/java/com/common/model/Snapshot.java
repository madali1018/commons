package com.common.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;

/**
 * Created by madali on 2017/4/27.
 */
@Entity
@Table(name = "snapshot")
public class Snapshot extends BaseModel {

    @EmbeddedId
    private SnapshotId id;

    @Column(name = "data", columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    @Column(name = "execution_status")
    private Integer executionStatus;

    public SnapshotId getId() {
        return id;
    }

    public void setId(SnapshotId id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Integer getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(Integer executionStatus) {
        this.executionStatus = executionStatus;
    }

    @Override
    public String toString() {

        return "Snapshot{" +
                "id: {buId=" + id.getBuId() + ", executeGmt0Datetime=" + id.getExecuteGmt0Datetime() + "}" +
                ", data=" + Arrays.toString(data) +
                ", executionStatus=" + executionStatus +
                '}';
    }
}

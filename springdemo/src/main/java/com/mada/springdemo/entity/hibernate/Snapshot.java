package com.mada.springdemo.entity.hibernate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by madali on 2017/4/27.
 */
@Entity
@Table(name = "snapshot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Snapshot extends BaseModel {

    @EmbeddedId
    private SnapshotId id;

    @Column(name = "data", columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    @Column(name = "execution_status")
    private Integer executionStatus;

}

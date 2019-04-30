package com.mada.utils.serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by madali on 2018/11/28 16:39
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String pwd;

}

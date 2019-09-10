package com.mada.springdemo.entity.spring;

import com.mada.springdemo.entity.hibernate.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by madali on 2017/5/2.
 */
@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseModel {

    private Integer id;

    private Integer age;

    private String name;

}

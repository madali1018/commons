package com.mada.commons.utils.obj.entity;

import com.mada.commons.utils.obj.annotation.FieldDescriptionAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2017/5/17.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class A {

    @FieldDescriptionAnnotation(fieldDescription = "age")
    private int age;

    private int id;

    @FieldDescriptionAnnotation(fieldDescription = "false")
    private Boolean flag;

    @FieldDescriptionAnnotation(fieldDescription = "name")
    private String name;

    @FieldDescriptionAnnotation(fieldDescription = "array")
    private int[] array;

    @FieldDescriptionAnnotation(fieldDescription = "list")
    private List<String> list;

    @FieldDescriptionAnnotation(fieldDescription = "collection/map")
    private Map<Integer, String> map;

    @FieldDescriptionAnnotation(fieldDescription = "coordinates")
    private Coordinates coordinates;

}
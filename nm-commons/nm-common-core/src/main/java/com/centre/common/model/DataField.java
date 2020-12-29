package com.centre.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
 * @author: stars
 * @data: 2020年 08月 11日 19:08
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataField<T> implements Serializable {

    private static final long serialVersionUID = -275582248840137389L;

    /**
     * 总数
     */
    private Integer count;

    /**
     * 当前页结果集
     */
    private List<T> list;
}

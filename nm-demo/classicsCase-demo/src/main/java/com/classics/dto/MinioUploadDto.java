package com.classics.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传返回结果
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MinioUploadDto {
    /***
     * 文件访问URL
     */
    private String url;
    /**
     * 文件名称
     */
    private String name;

    /***
     * 对象存储名称，删除资源的时候需要指定这个名称
     */
    private String objectName ;
}

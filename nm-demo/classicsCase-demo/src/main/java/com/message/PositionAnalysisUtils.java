package com.message;

import lombok.Data;

/**
 * 高德地图根据经纬度获位置信息
 * <p>
 *     解析JSON格式的数据
 *     <b>这个格式还有些问题，不过打大致是正确的。</b>
 * </p>
 * @author: gl_stars
 * @data: 2020年 10月 09日 14:02
 **/
@Data
public class PositionAnalysisUtils {

    /**
     * 状态信息
     */
    private String status ;

    /***
     * 是否成功
     */
    private String info ;

    /***
     * 状态码
     */
    private String infocode ;

    private Regeocode regeocode;

    @Data
    public class  Regeocode{

        /***
         * 详细
         */
        private AddressComponent addressComponent ;

        /***
         * 名称
         */
        private String formattedAddress ;

        @Data
        public class AddressComponent {

            /**
             * 城市
             */
            private String city;

            /***
             * 省份
             */
            private String province;

            /***
             * 区代码
             */
            private String adcode;

            /**
             * 区
             */
            private String district;

            /***
             * 诚实代码
             */
            private String towncode ;

            /***
             * 街道
             */
            private StreetNumber streetNumber;

            /***
             * 国际
             */
            private String country ;

            /***
             * 乡镇
             */
            private String township;

            private String[] businessAreas ;

            private Building building ;

            @Data
            public class Building{
                private String[] name ;
                private String[] type;
            }
            private Neighborhood neighborhood ;

            @Data
            public class Neighborhood{
                private String[] name;
                private String[] type;
            }

            private String citycode;

            /***
             * 街道详细
             */
            @Data
            public class StreetNumber{
                private String[] number ;

                private String[] direction;

                private String[] distance;
                private String[] street;
            }
        }
    }
}


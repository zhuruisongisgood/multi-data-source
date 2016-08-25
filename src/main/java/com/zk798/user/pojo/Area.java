package com.zk798.user.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 区 <br/>
 * Date: 2016/7/13<br/>
 *
 * @author ZRS
 * @since JDK7
 */
@Setter
@Getter
@ToString
public class Area  implements Serializable {

    private Integer areaId;
    private String areaChName;
    private String areaEnName;
    private Integer parentID;
    private Integer layer;
    private String patch;
    private String parentIDs;


}

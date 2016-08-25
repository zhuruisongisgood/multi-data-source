package com.zk798.user.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <br/>
 * Date: 2016/7/25<br/>
 *
 * @author ZRS
 * @since JDK7
 */
@Setter
@Getter
@ToString
public class User implements Serializable{

    private String userId;
    private String nickName;
    private String trueName;


}

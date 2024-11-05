package me.jet.zhu.core.common.netty.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class IMHeadO extends IMHead {
    /**
     * 响应码
     */
    private String cd;
    /**
     * 响应描述
     */
    private String ds = "OK";

    /**
     * 服务端处理耗时
     */
    private Long ct;
    /**
     * 服务端接受时间
     */
    private Long at;
    /**
     * 服务端响应时间
     */
    private Long rt;
}

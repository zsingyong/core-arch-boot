package me.jet.zhu.core.common.netty.dto;


import cn.hutool.core.util.IdUtil;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IMHead {
    /**
     * 消息随机id
     */
    private String id;
    /**
     * 客户端发送时间
     */
    private Long st;
    /**
     * 客户端消息创建
     */
    private Long ct;
    /**
     * 通信渠道
     */
    private String cn;
    /*
     * 校验MD5
     */
    private String md;
    /**
     * 消息类型 CTRL/TEXT/FILE/IMG
     */
    private Integer tp;
    /**
     * 本地mac 视为客户端ID
     */
    private String mc;

    public IMHead() {
        id = IdUtil.fastUUID();
        ct = System.currentTimeMillis();
    }
}

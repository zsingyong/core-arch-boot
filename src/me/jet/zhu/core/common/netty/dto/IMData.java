package me.jet.zhu.core.common.netty.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IMData {
    /**
     * 响应信息
     */
    private String ms;

}

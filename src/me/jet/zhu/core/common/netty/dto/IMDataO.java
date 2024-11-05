package me.jet.zhu.core.common.netty.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

@Data
@Accessors(chain = true)
public class IMDataO extends IMData {
    /**
     * 响应码
     */
    private String cd;
    /**
     * 基础描述
     */
    private String ds;

    public boolean isOk() {
        return StringUtils.equals(cd, "00000000");
    }
}

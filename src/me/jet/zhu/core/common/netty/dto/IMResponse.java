package me.jet.zhu.core.common.netty.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IMResponse {
    private String host;
    private Integer port;
    private IMHeadO head;
    private IMDataO data;
}

package me.jet.zhu.core.common.netty.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IMRequest {

    private IMHeadI head;
    private IMDataI data;
}

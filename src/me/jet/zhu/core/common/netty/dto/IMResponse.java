package me.jet.zhu.core.common.netty.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IMResponse {
    private IMHeadO head;
    private IMDataO data;

    public IMResponse() {
        this.head = new IMHeadO();
        this.data = new IMDataO();
    }
}

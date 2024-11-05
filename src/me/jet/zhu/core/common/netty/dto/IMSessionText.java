package me.jet.zhu.core.common.netty.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IMSessionText extends IMSession {

    public IMSessionText(String host1, Integer port1) {
        super(host1, port1);
        setTp(0);
    }
}

package me.jet.zhu.core.common.netty.cache.route;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NetRoute {
    private String route;
    private String hostA;
    private String maskA;
    private Integer portA;
    private String hostB;
    private String maskB;
    private Integer portB;
}

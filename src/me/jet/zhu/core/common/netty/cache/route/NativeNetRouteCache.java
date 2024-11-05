package me.jet.zhu.core.common.netty.cache.route;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.ConcurrentHashMap;

@Data
@Accessors(chain = true)
public class NativeNetRouteCache {
    private ConcurrentHashMap<String, NetRoute> routes = new ConcurrentHashMap<>();
}

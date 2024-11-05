package me.jet.zhu.core.common.netty.cache.route;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class NativeNetRouteCache {
    private ConcurrentHashMap<String, NetRoute> routes = new ConcurrentHashMap<>();
}

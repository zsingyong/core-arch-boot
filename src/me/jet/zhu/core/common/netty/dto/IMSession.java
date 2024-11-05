package me.jet.zhu.core.common.netty.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.function.Consumer;

@Data
@Accessors(chain = true)
public class IMSession {
    private String tp;
    private IMRequest request;
    private IMResponse response;
    private Consumer<IMResponse> okCall;
    private Consumer<IMResponse> exCall;
}

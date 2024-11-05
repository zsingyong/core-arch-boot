package me.jet.zhu.core.common.netty.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@Accessors(chain = true)
public class IMSession {
    private static final Logger logger = LoggerFactory.getLogger(IMSession.class);
    private Integer tp;
    private String host0;
    private Integer port0;
    private String host1;
    private Integer port1;
    private IMRequest request;
    private IMResponse response;

    public IMSession(String host0, Integer port0, String host1, Integer port1) {
        this.host0 = host0;
        this.port0 = port0;
        this.host1 = host1;
        this.port1 = port1;
        this.request = new IMRequest();
    }

    public IMSession(String host1, Integer port1) {
        this.host1 = host1;
        this.port1 = port1;
        this.request = new IMRequest();
    }


    public IMSession() {
        this.request = new IMRequest();
    }
}

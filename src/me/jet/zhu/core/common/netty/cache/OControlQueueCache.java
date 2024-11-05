package me.jet.zhu.core.common.netty.cache;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import me.jet.zhu.core.common.netty.dto.IMSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 输出控制消息缓存
 */
@State(name = "IControlQueueCache", storages = {@Storage("IControlQueueCache")})
public class OControlQueueCache implements PersistentStateComponent<OControlQueueCache> {

    private final ConcurrentLinkedQueue<IMSession> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public static OControlQueueCache getInstance() {
        return ServiceManager.getService(OControlQueueCache.class);
    }

    public IMSession pop() {
        return concurrentLinkedQueue.poll();
    }

    public void add(IMSession iSession) {
        concurrentLinkedQueue.add(iSession);
    }

    @Nullable
    @Override
    public OControlQueueCache getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull OControlQueueCache content) {
        XmlSerializerUtil.copyBean(content, this);
    }
}

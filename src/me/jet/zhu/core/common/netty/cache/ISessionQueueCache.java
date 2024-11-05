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
 * 输入普通消息缓存
 */
@State(name = "ISessionQueueCache", storages = {@Storage("ISessionQueueCache")})
public class ISessionQueueCache implements PersistentStateComponent<ISessionQueueCache> {

    private final ConcurrentLinkedQueue<IMSession> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public static ISessionQueueCache getInstance() {
        return ServiceManager.getService(ISessionQueueCache.class);
    }

    public IMSession pop() {
        return concurrentLinkedQueue.poll();
    }

    public void add(IMSession iSession) {
        concurrentLinkedQueue.add(iSession);
    }

    @Nullable
    @Override
    public ISessionQueueCache getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ISessionQueueCache content) {
        XmlSerializerUtil.copyBean(content, this);
    }
}

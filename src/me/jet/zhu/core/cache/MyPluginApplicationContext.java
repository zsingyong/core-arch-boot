package me.jet.zhu.core.cache;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 插件全局应用上下文
 */
@State(name = "MyPluginApplicationContext", storages = {@Storage("MyPluginApplicationContext")})
public class MyPluginApplicationContext implements PersistentStateComponent<MyPluginApplicationContext> {
    private String runMode;

    public static MyPluginApplicationContext getInstance() {
        return getService(MyPluginApplicationContext.class);
    }

    public static <T> T getService(Class<T> clazz) {
        return ServiceManager.getService(clazz);
    }

    @Nullable
    @Override
    public MyPluginApplicationContext getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MyPluginApplicationContext content) {
        XmlSerializerUtil.copyBean(content, this);
    }
}

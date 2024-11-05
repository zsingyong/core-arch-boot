package me.jet.zhu.core.activity;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import me.jet.zhu.core.common.netty.service.NettyServiceListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PluginStartupActivity implements StartupActivity {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Logger logger = LoggerFactory.getLogger(PluginStartupActivity.class);

    @Override
    public void runActivity(@NotNull Project project) {
        new NettyServiceListener().start();
    }
}
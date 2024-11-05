package me.jet.zhu.core.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import me.jet.zhu.core.common.action.MyAction;
import me.jet.zhu.core.common.action.MyActionRule;
import me.jet.zhu.core.common.netty.dto.IMRequest;
import me.jet.zhu.core.common.netty.utils.IMMessageTools;
import org.jetbrains.annotations.NotNull;

@MyActionRule(id = "DEV0000")
public class NettyMessageTestAction extends MyAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        final IMRequest request = new IMRequest();
        IMMessageTools.sendMessage("127.0.0.1", 6666, request, response -> {

        });
    }

    @Override
    public boolean hide(@NotNull AnActionEvent e) {
        return false;
    }
}

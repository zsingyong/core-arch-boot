package me.jet.zhu.core.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import me.jet.zhu.core.common.action.MyAction;
import me.jet.zhu.core.common.action.MyActionRule;
import me.jet.zhu.core.common.netty.dto.IMSessionProxy;
import me.jet.zhu.core.common.netty.dto.IMSessionText;
import org.jetbrains.annotations.NotNull;

@MyActionRule(id = "DEV0000")
public class NettyMessageCtrlAction extends MyAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        final IMSessionText imMessagePureTxt = new IMSessionText("127.0.0.1", 6842);
        IMSessionProxy.builder().session(imMessagePureTxt).exCall(exResponse -> {

        }).okCall(okResponse -> {

        }).build().sendMessage();
    }
}
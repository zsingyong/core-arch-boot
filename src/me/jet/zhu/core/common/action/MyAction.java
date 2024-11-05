package me.jet.zhu.core.common.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public abstract class MyAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent e) {
        if (!hide(e) && getClass().isAnnotationPresent(MyActionRule.class)) {
            MyActionRule myActionRule = getClass().getDeclaredAnnotation(MyActionRule.class);
            String id = myActionRule.id();
        }
    }

    public boolean hide(@NotNull AnActionEvent e) {
        return false;
    }
}

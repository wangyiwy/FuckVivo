package me.wangyi.fuckvivo;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class InstallHelperService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) return;

        if (event.getPackageName().equals("com.vivo.secime.service")) {
            String password = PreferencesUtils.getString(getApplicationContext(),
                    PreferencesUtils.KEY_PASSWORD, "");
            if (!TextUtils.isEmpty(password)) {
                fillPassword(rootNode, password);
            }
        } else if (event.getPackageName().equals("com.android.packageinstaller")) {
            installConfirm(rootNode);
        }
    }

    private void fillPassword(AccessibilityNodeInfo rootNode, String password) {
        AccessibilityNodeInfo editText = rootNode.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        if (editText == null) return;

        if (editText.getPackageName().equals("com.bbk.account")
                && editText.getClassName().equals("android.widget.EditText")) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo
                    .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, password);
            editText.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        }

        List<AccessibilityNodeInfo> nodeInfoList =rootNode.findAccessibilityNodeInfosByText("确定");
        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    private void installConfirm(AccessibilityNodeInfo rootNode) {
        List<AccessibilityNodeInfo> nodeInfoList = new ArrayList<>();
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("继续安装"));
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("安装"));
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("打开"));

        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    @Override
    public void onInterrupt() {
    }
}

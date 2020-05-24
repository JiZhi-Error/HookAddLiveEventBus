package com.jizhi.hookaddliveeventbus

import android.app.Application
import android.os.Parcel
import android.util.Log
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.Config
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage


class MainHook : IXposedHookLoadPackage {

    val TAG = "com.hookaddliveeventbus"
    var systemUI_LiveEventBus: Config? = null
    var android_LiveEventBus: Config? = null

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam?.packageName == "com.android.systemui") {
            XposedHelpers.findAndHookMethod(
                Application::class.java,
                "onCreate",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        // 只初始化一次
                        if (systemUI_LiveEventBus == null) {
                            systemUI_LiveEventBus = LiveEventBus.config()
                                .lifecycleObserverAlwaysActive(true)
                                .autoClear(false)
                            Log.i(TAG, "SystemUI,config $systemUI_LiveEventBus")
                            LiveEventBus.get(
                                "hookaddliveeventbus_SystemUI_String",
                                String::class.java
                            ).observeStickyForever {
                                Log.i(TAG, "SystemUI接收到数据：$it")
                            }

                            LiveEventBus.get(
                                "hookaddliveeventbus_SystemUI_String_Post",
                                String::class.java
                            ).observeStickyForever {
                                Log.i(TAG, "SystemUI接收到数据：$it")
                                LiveEventBus
                                    .get("hookaddliveeventbus_SystemUI_String_Get")
                                    .postAcrossApp("我是从SystemUI来的数据！！！4444444444")
                            }

                            LiveEventBus
                                .get("hookaddliveeventbus_SystemUI_String_Init")
                                .broadcast("我是从SystemUI来的数据！！！66666666")
                        }
                    }
                })
        } else if (lpparam?.packageName == "android") {

            val STUB_CLASS = Class.forName("android.view.IWindowManager\$Stub")
            XposedHelpers.findAndHookMethod(
                STUB_CLASS,
                "onTransact",
                Int::class.java,
                Parcel::class.java,
                Parcel::class.java,
                Int::class.java,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        // 只初始化一次
                        if (android_LiveEventBus == null) {
                            android_LiveEventBus = LiveEventBus.config()
                                .lifecycleObserverAlwaysActive(false)
                                .autoClear(true)
                            Log.i(TAG, "Android,config $android_LiveEventBus")
                            LiveEventBus.get(
                                "hookaddliveeventbus_Android_String",
                                String::class.java
                            ).observeStickyForever {
                                Log.i(TAG, "Android接收到数据：$it")
                            }
                        }
                    }
                })
        }
    }

}
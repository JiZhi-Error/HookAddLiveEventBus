package com.jizhi.hookaddliveeventbus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        systemUI.setOnClickListener {
            LiveEventBus
                .get("hookaddliveeventbus_SystemUI_String")
                .postAcrossApp("我是从模块来的数据！！！")
        }
        android.setOnClickListener {
            LiveEventBus
                .get("hookaddliveeventbus_Android_String")
                .postAcrossApp("我是从模块来的数据！！！11111111111111")
        }
    }
}

package com.jizhi.hookaddliveeventbus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
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

        button.setOnClickListener {
            LiveEventBus
                .get("hookaddliveeventbus_SystemUI_String_Post")
                .postAcrossApp("我是从模块来的数据！！！222222222")
        }

        LiveEventBus
            .get("hookaddliveeventbus_SystemUI_String_Get")
            .observeSticky(this, Observer {
                textView.text = it as String
            })

        // 接受刚开机时候传递的数据
        LiveEventBus
            .get("hookaddliveeventbus_SystemUI_String_Init")
            .observeSticky(this, Observer {
                textView2.text = it as String
            })


    }
}

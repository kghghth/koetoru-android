package com.junyakengo.koetoru

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_setting)// コードからフラグメントを追加
//        if (savedInstanceState == null) {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.add(R.id.container, SettingFragment.newInstance("TOYOTA", "プリウス"))
//            transaction.commit()
//        }

    }
}

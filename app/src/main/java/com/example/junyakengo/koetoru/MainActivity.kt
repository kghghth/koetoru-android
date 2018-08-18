package com.example.junyakengo.koetoru

import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ArrayAdapter
import java.io.File
import android.os.Environment


class MainActivity : AppCompatActivity() {



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, RecodeFragment())
                        .commit()
                this.title = "録音"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FileSelectFragment())
                        .commit()
                this.title = "録音一覧"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingFragment())
                        .commit()
                this.title = "設定"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dir = "${Environment.getExternalStorageDirectory()}/Koetoru"
        File(dir).mkdirs()
        setContentView(R.layout.activity_main)
        message.setText("")
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, RecodeFragment())
                .commit()
        this.title = "録音"
        // 音量調整を端末のボタンに任せる
        setVolumeControlStream(AudioManager.STREAM_MUSIC)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}

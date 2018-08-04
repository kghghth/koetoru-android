package com.example.junyakengo.koetoru

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fragment3 = SettingFragment()
    val transaction3 = supportFragmentManager.beginTransaction()

    val fragment1 = RecodeFragment()
    val transaction1 = supportFragmentManager.beginTransaction()

    val fragment2 = FileSelectFragment()
    val transaction2 = supportFragmentManager.beginTransaction()


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                this.showCheck(false, fragment3)
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                this.showCheck(false, fragment3)
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
//                setContentView(R.layout.fragment_setting)
                this.showCheck(true, fragment3)

//                val intent = Intent(this, SettingActivity::class.java)
//                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun showCheck(isSettingFragment: Boolean, fragment: Fragment) {
        if (isSettingFragment){
            this.transaction3.show(fragment)
        } else {
            this.transaction3.hide(fragment)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            this.transaction1.add(R.id.container, fragment1)
            this.transaction1.commit()
            this.transaction2.add(R.id.container, fragment2)
            this.transaction2.commit()
            this.transaction3.add(R.id.container, fragment3)
            this.transaction3.commit()
            this.showCheck(false, fragment3)
            this.showCheck(false, fragment1)
            this.showCheck(true, fragment2)
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}

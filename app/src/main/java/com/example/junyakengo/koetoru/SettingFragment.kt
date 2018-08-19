package com.junyakengo.koetoru

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.R.attr.data
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_setting.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SettingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SettingFragment : Fragment() {
    // Fragmentで表示するViewを作成するメソッド
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val mainFrame = inflater!!.inflate(R.layout.fragment_setting, container, false)
        val listView = mainFrame.findViewById(R.id.listView) as ListView
        val versionName = BuildConfig.VERSION_NAME
        val data:Array<String> = arrayOf("アプリバージョン: " + versionName,"ご意見: Twitter(@spoon_kogepan)")
        val adapterSet = ArrayAdapter(this.context, android.R.layout.simple_list_item_1,data)
        listView.adapter = adapterSet

        // 先ほどのレイアウトをここでViewとして作成します
        return mainFrame
    }
}

package com.example.junyakengo.koetoru

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Environment.getExternalStorageDirectory
import android.os.Handler
import android.util.Log
import android.widget.*
import java.io.File
import java.io.IOException
import android.provider.SyncStateContract.Helpers.update
import android.content.ContentValues
import android.content.DialogInterface
import com.example.junyakengo.koetoru.R.id.textView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.AdapterView
import com.example.junyakengo.koetoru.R.id.listView
import kotlin.math.ceil


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FileSelectFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FileSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FileSelectFragment : Fragment() {

    private val songList = ArrayList<String>()
    private val songplayFlagList = ArrayList<Boolean>()
    private var listView: ListView? = null
    private var beforeListNumber = 0
    private var beforeListView: View? = null
    val FILENAME = "/sdcard/Movies/"

    private var playingFlag = false
    private var longFlag = false
    private var mediaPlayer: MediaPlayer? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val mainFrame = inflater!!.inflate(R.layout.fragment_file_select, container, false)
        listView = mainFrame.findViewById(R.id.listView) as ListView
        val sdPath = getExternalStorageDirectory().getPath()


        Log.d(tag, sdPath)
        val files = File(FILENAME).listFiles()
        if (files != null) {
            for (i in 0 until files.size) {
                if (files[i].isFile() && files[i].getName().endsWith(".mp3")) {
                    songList.add(files[i].getName())
                    songplayFlagList.add(false)
                }
            }

            val adapter = ArrayAdapter<String>(this.context, android.R.layout.simple_expandable_list_item_1, songList)
            listView!!.adapter = adapter

            listView!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                if (longFlag) {
                    longFlag = false
                    return@OnItemClickListener
                }
                val listView = parent as ListView
                val item = listView.getItemAtPosition(position) as String
                if (songplayFlagList[position]) {
                    audioStop(view)
                } else {
                    audioStop(beforeListView)
                    view.setBackgroundColor(Color.GREEN)
                    beforeListNumber = position
                    beforeListView = view
                    audioPlay(item, view)
                }
            })

            listView!!.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
                longFlag = true
                val list = parent as ListView
                val selectedItem = list
                        .getItemAtPosition(position) as String
                showDialogFragment(selectedItem, position,adapter)
                false

            }

        }
        // 先ほどのレイアウトをここでViewとして作成します
        return mainFrame
    }

    fun showDialogFragment(selectedItem: String, removePosition: Int, deleteAdapter: ArrayAdapter<String>) {
        AlertDialog.Builder(activity)
                .setTitle(selectedItem + "を削除しますか？")
                .setMessage("削除するならOKを押下してください。")
                .setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int ->
                    val saveFileName = FILENAME + selectedItem
                    var mediafile: File? = File(saveFileName)
                    if (mediafile!!.exists()) {
                        //ファイルが存在する場合は削除する
                        Log.d(tag, "削除処理により、ファイル削除")
                        mediafile!!.delete()
                        songList.removeAt(removePosition)
                        deleteAdapter.remove(selectedItem)

                    }

                })
                .setNegativeButton("CANCEL", null)
                .show()
    }

    fun deleteFile(){

    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "onDestroy Start")
        if (mediaPlayer == null) {
            return
        }
        // 再生終了
        mediaPlayer!!.stop()
        // リセット
        mediaPlayer!!.reset()
        // リソースの解放
        mediaPlayer!!.release()

        mediaPlayer = null
    }

    private fun audioSetup(playFileName: String): Boolean {
        var fileCheck = false

        // インタンスを生成
        mediaPlayer = MediaPlayer()

        //音楽ファイル名, あるいはパス
        val saveFileName = FILENAME + playFileName

        Log.d(tag, "ファイル名" + saveFileName)
        // assetsから mp3 ファイルを読み込み
        try {
            mediaPlayer!!.setDataSource(saveFileName)
            mediaPlayer!!.prepare()
            fileCheck = true
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return fileCheck
    }

    // AudioPlay Action
    private fun audioPlay(playFileName: String, playListView: View) {

        if (mediaPlayer == null) {
            // audio ファイルを読出し
            if (audioSetup(playFileName)) {
                Toast.makeText(this.activity.getApplication(), "Rread audio file", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.activity.getApplication(), "Error: read audio file", Toast.LENGTH_SHORT).show()
                playingFlag = true
                return
            }
        }

        // 再生する
        mediaPlayer!!.start()

        // 終了を検知するリスナー
        mediaPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {

            audioStop(playListView)
        })

        playingFlag = true
        songplayFlagList[beforeListNumber] = true
    }

    private fun audioStop(playListView: View?) {
        if (mediaPlayer == null || playListView == null ) {
            return
        }
        // 再生終了
        mediaPlayer!!.stop()
        // リセット
        mediaPlayer!!.reset()
        // リソースの解放
        mediaPlayer!!.release()

        mediaPlayer = null
        playingFlag = false
        songplayFlagList[beforeListNumber] = false
        playListView!!.setBackgroundColor(Color.WHITE)
    }



//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_file_select, container, false)
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     *
//     *
//     * See the Android Training lesson [Communicating with Other Fragments]
//     * (http://developer.android.com/training/basics/fragments/communicating.html)
//     * for more information.
//     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment FileSelectFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                FileSelectFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}

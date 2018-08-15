package com.example.junyakengo.koetoru

import android.content.Context
import android.net.Uri
import android.util.Log
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.media.MediaRecorder
import kotlin.math.max
import java.nio.file.Files.exists
import android.os.Environment
import android.widget.EditText
import java.io.IOException
import java.io.File
import android.app.AlertDialog
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RecodeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RecodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RecodeFragment : Fragment() {
    private var recodingFlag = false
    val FILENAME = "/sdcard/Movies/"
    var recorder: MediaRecorder = MediaRecorder()
    var recodeButton: ImageButton? = null
    var playButton: ImageButton? = null
    var editText: EditText? = null
    var textView: TextView? = null

    val handler = Handler()
    var timeValue = 0
    var runnable: Runnable? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val mainFrame = inflater!!.inflate(R.layout.fragment_recode, container, false)
        // 先ほどのレイアウトをここでViewとして作成します
        this.recodeButton = mainFrame.findViewById(R.id.imageButton) as ImageButton
        this.playButton = mainFrame.findViewById(R.id.imageButton2) as ImageButton
        this.textView = mainFrame.findViewById(R.id.textView) as TextView
        this.editText = mainFrame.findViewById(R.id.editText) as EditText
        this.recodeButton!!.setOnClickListener{tapStartRecoding()}
        this.playButton!!.setOnClickListener{tapPlayButton()}


        runnable = object : Runnable {
            override fun run() {
                timeValue++

                timeToText(timeValue)?.let {
                    textView!!.text = "録音中: " + it
                }
                handler.postDelayed(this, 1000)
            }
        }

        return mainFrame
    }

    fun tapPlayButton() {

    }

    fun tapStartRecoding() {

        if (recodingFlag) {
            Log.d(tag, "Stop Action")
            stopRecording()
            Log.d(tag, "Stop OK!!!")
        } else {
            Log.d(tag, "Start Action")
            if (this.editText!!.text.isEmpty()) {
                AlertDialog.Builder(activity)
                        .setTitle("ファイル名が空です")
                        .setMessage("ファイル名を入力してから、最後マイクボタンを押下してください")
                        .setPositiveButton("OK", null)
                        .show()
            } else {
                startRecording()
            }
            Log.d(tag, "OK!!!")
        }
    }


    fun startRecording() {
        val saveFileName = FILENAME + this.editText!!.text + ".mp3"
        var mediafile: File? = File(saveFileName)
        if (mediafile!!.exists()) {
            //ファイルが存在する場合は削除する
            Log.d(tag, "ファイル削除")
            mediafile!!.delete()
        }
        mediafile = null
        recorder = MediaRecorder()
        //マイクからの音声を録音する
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        //ファイルへの出力フォーマット DEFAULTにするとwavが扱えるはず
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
        //音声のエンコーダーも合わせてdefaultにする
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
        //ファイルの保存先を指定
        recorder.setOutputFile(saveFileName)
        //録音の準備をする
        recorder.prepare()
        //録音開始
        recorder.start()
        this.recodeButton!!.setImageResource(R.drawable.stop)
        recodingFlag = true
        this.textView!!.text = "録音中"
        handler.post(runnable)

    }

    fun stopRecording() {
        // 録音停止
        recorder.stop()
        recorder.reset()
        recodingFlag = false
        this.textView!!.text = "待機中"
        this.recodeButton!!.setImageResource(R.drawable.recoding)
        handler.removeCallbacks(runnable)
        timeValue = 0
    }

    private fun timeToText(time: Int = 0): String? {
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}

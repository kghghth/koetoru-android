package com.example.junyakengo.koetoru

import android.content.Context
import android.net.Uri
import android.util.Log
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_recode.*
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlin.math.max
import java.nio.file.Files.exists
import android.os.Environment
import android.widget.EditText
import java.io.IOException
import java.io.File
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.support.v4.app.DialogFragment




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
    }

    fun stopRecording() {
        // 録音停止
        recorder.stop()
        recorder.reset()
        recodingFlag = false
        this.textView!!.text = "待機中"
        this.recodeButton!!.setImageResource(R.drawable.recoding)
    }
}

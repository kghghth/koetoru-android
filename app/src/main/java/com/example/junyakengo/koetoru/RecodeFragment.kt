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
import kotlinx.android.synthetic.main.fragment_recode.*
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlin.math.max
import java.nio.file.Files.exists
import android.os.Environment
import java.io.IOException
import java.io.File






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
    val FILENAME = "/sdcard/sampleWav.wav"
    var recorder: MediaRecorder = MediaRecorder()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val mainFrame = inflater!!.inflate(R.layout.fragment_recode, container, false)
        // 先ほどのレイアウトをここでViewとして作成します
        val imageButton = mainFrame.findViewById(R.id.imageButton) as ImageButton
        imageButton.setOnClickListener{tapStartRecoding()}
        return mainFrame
    }

    fun tapStartRecoding() {

        Log.d(tag, "12346789")

        if (recodingFlag) {
            Log.d(tag, "Stop Action")
            stopRecording()
            Log.d(tag, "Stop OK!!!")
        } else {
            Log.d(tag, "Start Action")
            startRecording()
            Log.d(tag, "OK!!!")
        }
    }


    fun startRecording() {
        var mediafile: File? = File(FILENAME)
        if (mediafile!!.exists()) {
            //ファイルが存在する場合は削除する
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
        recorder.setOutputFile(FILENAME)
        //録音の準備をする
        recorder.prepare()
        //録音開始
        recorder.start()
        recodingFlag = true
    }

    fun stopRecording() {
        // 録音停止
        recorder.stop()
        recorder.reset()
        recodingFlag = false
    }
}

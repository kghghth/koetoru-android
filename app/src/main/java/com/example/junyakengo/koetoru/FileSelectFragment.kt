package com.example.junyakengo.koetoru

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.AdapterView
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import java.io.File




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
    private val lv: ListView? = null
    val FILENAME = "/sdcard/Movies"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val mainFrame = inflater!!.inflate(R.layout.fragment_file_select, container, false)
        val listView = mainFrame.findViewById(R.id.listView) as ListView
        val sdPath = getExternalStorageDirectory().getPath()

        Log.d(tag, sdPath)
        val files = File(FILENAME).listFiles()
        if (files != null) {
            for (i in 0 until files.size) {
                if (files[i].isFile() && files[i].getName().endsWith(".mp3")) {
                    songList.add(files[i].getName())
                }
            }

            val adapter = ArrayAdapter<String>(this.context, android.R.layout.simple_expandable_list_item_1, songList)
            listView.adapter = adapter

            listView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
//                val listView = parent as ListView
//                val item = listView.getItemAtPosition(position) as String
//                showItem(item)
                Log.d(tag, "Select!!!")
            })
        }
        // 先ほどのレイアウトをここでViewとして作成します
        return mainFrame
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

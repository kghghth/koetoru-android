package com.example.junyakengo.koetoru

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


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
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // 先ほどのレイアウトをここでViewとして作成します
        return inflater!!.inflate(R.layout.fragment_recode, container, false)
    }
}

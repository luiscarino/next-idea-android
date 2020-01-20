package com.luiscarino.nextidea.view.preferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.luiscarino.nextidea.R
import kotlinx.android.synthetic.main.fragment_web_view.*

private const val ARG_URL = "url"

/**
 * A simple [Fragment] subclass.
 * Use the [WebViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WebViewFragment : androidx.fragment.app.Fragment() {

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_web_view, container, false)
        myWebView.loadUrl(arguments?.getString(ARG_URL))
        return myView
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url
         *
         */
        fun newInstance(url: String) =
                WebViewFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_URL, url)
                    }
                }
    }
}

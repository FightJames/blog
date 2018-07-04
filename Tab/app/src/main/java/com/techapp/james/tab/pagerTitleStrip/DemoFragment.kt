package com.techapp.james.tab

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_demo.view.*


class DemoFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_demo, container, false)
        arguments?.takeIf {
            it.containsKey(CollectionPagerAdapter.ARG_OBJECT)
        }?.apply {
            rootView.textView.text = getInt(CollectionPagerAdapter.ARG_OBJECT).toString()
        }
        return rootView
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}

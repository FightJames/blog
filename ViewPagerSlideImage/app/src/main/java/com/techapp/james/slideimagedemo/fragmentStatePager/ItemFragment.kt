package com.techapp.james.slideimagedemo.fragmentStatePager

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.techapp.james.slideimagedemo.R
import kotlinx.android.synthetic.main.fragment_pager_fragment_item.*

private const val ARGE = "arge"

class ItemFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        Log.d("ItemFragment", " onCreate ${arguments.get(ARGE).toString()}")

        return inflater.inflate(R.layout.fragment_pager_fragment_item, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ItemFragment", " onDestroy ${arguments.get(ARGE).toString()}")
    }

    override fun onStart() {
        super.onStart()
        countTextView.text = arguments.get(ARGE).toString()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        @JvmStatic
        fun newInstance(parameter: Int) =
                ItemFragment().apply {
                    arguments = Bundle().apply { putInt(ARGE, parameter) }
                }
    }
}


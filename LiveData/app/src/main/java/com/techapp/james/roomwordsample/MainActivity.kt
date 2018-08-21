package com.techapp.james.roomwordsample

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.techapp.james.roomwordsample.data.Word
import com.techapp.james.roomwordsample.viewModel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mWordViewModel: WordViewModel

    companion object {
        val NEW_WORD_ACTIVITY_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var adapter = MyAdapter()
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        mWordViewModel.getAllWords().observe(this, object : Observer<List<Word>> {
            override fun onChanged(t: List<Word>?) {
//                Toast.makeText(this@MainActivity, "Call", Toast.LENGTH_LONG).show()
                adapter.setWords(t!!)
            }

        })
        recyclerList.layoutManager = LinearLayoutManager(this)
        recyclerList.adapter = adapter
        floatingActionButton2.setOnClickListener {
            var i = Intent(this, NewWordActivity::class.java)
            startActivityForResult(i, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            var word = Word(data!!.getStringExtra("Ready"))
            mWordViewModel.insert(word)
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}

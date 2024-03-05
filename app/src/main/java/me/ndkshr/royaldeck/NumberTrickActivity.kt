package me.ndkshr.royaldeck

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.ndkshr.royaldeck.databinding.ActivityNumbertrickBinding

const val OFFSET_DIFF = 10
class NumberTrickActivity : AppCompatActivity(), NumberTrickCardColumnAdapter.OnCardClickListener {

    private lateinit var binding: ActivityNumbertrickBinding

    private var myDeck = ROYAL_DECK.shuffled().toMutableList().subList(0, 21)

    private var firstDeck = myDeck.subList(0, 7).toMutableList()
    private var secondDeck = myDeck.subList(7, 14).toMutableList()
    private var thirdDeck = myDeck.subList(14, 21).toMutableList()

    private val adapter1 = NumberTrickCardColumnAdapter(0, this)
    private val adapter2 = NumberTrickCardColumnAdapter(1, this)
    private val adapter3 = NumberTrickCardColumnAdapter(2, this)

    private var times = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_numbertrick)

        initBoard()
//        decorateBoard()
    }

    private fun initBoard() {
        binding.col1Rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.col1Rv.adapter = adapter1.apply {
            cardsInColumn = firstDeck
        }

        binding.col2Rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.col2Rv.adapter = adapter2.apply {
            cardsInColumn = secondDeck
        }

        binding.col3Rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.col3Rv.adapter = adapter3.apply {
            cardsInColumn = thirdDeck
        }
    }

    private fun decorateBoard() {
        ObjectAnimator.ofFloat(binding.col1Rv, "translationY", -1000f).apply {
            duration = 500
            start()
        }
    }

    override fun cardClicked(colIdx: Int) {
        times += 1
        myDeck = when(colIdx) {
            0 -> {
                (secondDeck + firstDeck + thirdDeck).toMutableList()
            }
            1 -> {
                (thirdDeck + secondDeck + firstDeck).toMutableList()
            }
            2 -> {
                (firstDeck + thirdDeck + secondDeck).toMutableList()
            }
            else -> {
                myDeck.toMutableList()
            }
        }
        firstDeck.clear()
        var idx = 0
        for (i in 0..6) {
            firstDeck.add(myDeck[idx])
            idx += 3
        }

        secondDeck.clear()
        idx = 1
        for (i in 0..6) {
            secondDeck.add(myDeck[idx])
            idx += 3
        }

        thirdDeck.clear()
        idx = 2
        for (i in 0..6) {
            thirdDeck.add(myDeck[idx])
            idx += 3
        }

        if (times == 3) {
            times = 0
            ResultDialogFragment(myDeck[10])
                .show(supportFragmentManager, "ResultDialogFragment")
            return
        }

        adapter1.cardsInColumn = firstDeck
        adapter2.cardsInColumn = secondDeck
        adapter3.cardsInColumn = thirdDeck

        adapter1.notifyDataSetChanged()
        adapter2.notifyDataSetChanged()
        adapter3.notifyDataSetChanged()
    }
}
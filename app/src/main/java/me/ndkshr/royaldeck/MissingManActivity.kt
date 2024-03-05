package me.ndkshr.royaldeck

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import me.ndkshr.royaldeck.databinding.ActivityMainBinding
import kotlin.random.Random


class MissingManActivity : AppCompatActivity(), OnTouchListener {

    private lateinit var binding: ActivityMainBinding

    private var currentUiState = UiState.VISIBLE

    private val blackCards = listOf(
        R.mipmap.card_jack_of_clubs, R.mipmap.card_king_of_clubs, R.mipmap.card_queen_of_clubs,
        R.mipmap.card_jack_of_spades, R.mipmap.card_king_of_spades, R.mipmap.card_queen_of_spades
    )

    private val redCards = listOf(
        R.mipmap.card_jack_of_hearts, R.mipmap.card_king_of_hearts, R.mipmap.card_queen_of_hearts,
        R.mipmap.card_king_of_diamonds, R.mipmap.card_jack_of_diamonds, R.mipmap.card_queen_of_diamonds
    )

    var deck = if (Random.nextBoolean()) blackCards else redCards
    var idxs = mutableListOf(0, 1, 2, 3, 4, 5).shuffled()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setVisibleState()
        binding.hideMyCard.setOnClickListener {
            if (currentUiState == UiState.VISIBLE) {
                setHiddenState()
            } else {
                setVisibleState()
            }
        }
    }

    private fun setHiddenState() {
        currentUiState = UiState.HIDDEN
        binding.card1Image.setImageResource(deck[idxs[3]])
        binding.card2Image.setImageResource(deck[idxs[4]])
        binding.card3.visibility = View.GONE
        binding.hideMyCard.text = "Try Again"

        ObjectAnimator.ofFloat(binding.card1, "translationX", 400f).apply {
            duration = 500
            start()
        }

        ObjectAnimator.ofFloat(binding.card2, "translationX", -400f).apply {
            duration = 500
            start()
        }
    }

    private fun setVisibleState() {

        binding.card1.animate().translationX(0f).translationY(0f).setDuration(300)
        binding.card2.animate().translationX(0f).translationY(0f).setDuration(300)
        binding.card3.animate().translationX(0f).translationY(0f).setDuration(300)

        deck = if (Random.nextBoolean()) blackCards else redCards
        idxs = mutableListOf(0, 1, 2, 3, 4, 5).shuffled()
        currentUiState = UiState.VISIBLE
        binding.card3.visibility = View.VISIBLE

        binding.card1Image.setImageResource(deck[idxs[0]])
        binding.card2Image.setImageResource(deck[idxs[1]])
        binding.card3Image.setImageResource(deck[idxs[2]])

        Handler(mainLooper).postDelayed({
            ObjectAnimator.ofFloat(binding.card1, "translationY", -1000f).apply {
                duration = 500
                start()
            }

            ObjectAnimator.ofFloat(binding.card2, "translationY", 1000f).apply {
                duration = 500
                start()
            }

            ObjectAnimator.ofFloat(binding.card3, "translationY", -1000f).apply {
                duration = 500
                start()
            }
        }, 500L)


        binding.hideMyCard.text = "Hide my card"
    }

    private var _xDelta = 0
    private var _yDelta = 0

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val X = event.rawX.toInt()
        val Y = event.rawY.toInt()
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val lParams = view.layoutParams as ConstraintLayout.LayoutParams
                _xDelta = X - lParams.leftMargin
                _yDelta = Y - lParams.topMargin
            }

            MotionEvent.ACTION_UP -> {}
            MotionEvent.ACTION_POINTER_DOWN -> {}
            MotionEvent.ACTION_POINTER_UP -> {}
            MotionEvent.ACTION_MOVE -> {
                val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.leftMargin = X - _xDelta
                layoutParams.topMargin = Y - _yDelta
                layoutParams.rightMargin = -250
                layoutParams.bottomMargin = -250
                view.setLayoutParams(layoutParams)
            }
        }
        binding.root.invalidate()
        return true
    }

    enum class UiState {
        HIDDEN, VISIBLE
    }
}
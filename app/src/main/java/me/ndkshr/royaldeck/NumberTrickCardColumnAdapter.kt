package me.ndkshr.royaldeck

import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ndkshr.royaldeck.databinding.CardItemBinding

class NumberTrickCardColumnAdapter(
    private val colIdx: Int,
    private val listener: OnCardClickListener
) : RecyclerView.Adapter<NumberTrickCardColumnAdapter.NumberTrickCardItemVH>() {

    var cardsInColumn = mutableListOf<Int>()

    class NumberTrickCardItemVH(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mipmapResId: Int) {
            binding.card1Image.setImageResource(mipmapResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberTrickCardItemVH {
        val holder = NumberTrickCardItemVH(
            CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        holder.itemView.setOnClickListener {
            listener.cardClicked(colIdx)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return cardsInColumn.size
    }

    override fun onBindViewHolder(holder: NumberTrickCardItemVH, position: Int) {
        holder.bind(cardsInColumn[position])
    }

    interface OnCardClickListener {
        fun cardClicked(colIdx: Int)
    }
}
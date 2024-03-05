package me.ndkshr.royaldeck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import me.ndkshr.royaldeck.databinding.ResultDialogFragmentBinding

class ResultDialogFragment(private val resultCardResId: Int): DialogFragment() {

    private lateinit var binding: ResultDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResultDialogFragmentBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.card1Image.setImageResource(resultCardResId)
        binding.restart.setOnClickListener {
            dismiss()
        }
    }
}
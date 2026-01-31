package com.example.colormyviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.colormyviews.R
import com.example.colormyviews.databinding.FragmentSecondaryBinding
import com.example.colormyviews.viewmodels.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondFragment : Fragment(R.layout.fragment_secondary) {

    // Scoped to the NavGraph as we discussed
    private val viewModel: GameViewModel by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    // View Binding setup
    private var _binding: FragmentSecondaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSecondaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Now you access views directly via the binding object!
        val buttons = listOf(
            binding.cell0, binding.cell1, binding.cell2,
            binding.cell3, binding.cell4, binding.cell5,
            binding.cell6, binding.cell7, binding.cell8
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { viewModel.onCellClicked(index) }
        }

        binding.btnGoBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Observe game state
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.boardState.collect { board ->
                    board.forEachIndexed { index, value ->
                        buttons[index].text = value
                    }
                }
            }
        }

        //update on winner
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.winner.collect { winner ->
                    if (winner != null) {
                        val message = if (winner == "Draw") "It's a Draw!" else "Winner is $winner!"
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                        // Optional: Change the "Back" button text to "Play Again?"
                        //binding.btnGoBack.text = "Play Again?"
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Critical to prevent memory leaks
    }
}
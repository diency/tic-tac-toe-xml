package com.example.colormyviews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.colormyviews.R
import com.example.colormyviews.databinding.FragmentSecondaryBinding
import com.example.colormyviews.ui.viewmodels.GameViewModel
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
        _binding = FragmentSecondaryBinding.bind(view)

        binding.ticTacToeBoard.onCellClickListener = { index ->
            viewModel.onCellClicked(index)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //collect board state and pass to custom view
                launch{
                    viewModel.boardState.collect { board ->
                        binding.ticTacToeBoard.updateBoard(board)
                    }
                }

                launch {
                    //collect winner state, and show toast
                    viewModel.winner.collect { winner ->
                        winner?.let {
                            val message = if (it == "Draw") "It's a draw!" else "Player $it Wins!"
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        binding.btnGoBack.setOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.colormyviews.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.colormyviews.R

class FirstFragment : Fragment(R.layout.fragment_primary) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.button_go_to_next)
        button.setOnClickListener {
            // Use the action ID from your nav_graph
            findNavController().navigate(R.id.action_first_to_second)
        }
    }

}
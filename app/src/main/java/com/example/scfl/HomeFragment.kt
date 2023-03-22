package com.example.scfl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scfl.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button450.setOnClickListener {

            val eventClass = "450"
            val action = HomeFragmentDirections.actionFirstFragmentToRaces450Fragment(eventClass)

            findNavController().navigate(action)
            //findNavController().navigate(R.id.action_FirstFragment_to_races450Fragment("450"))
        }

        binding.button250.setOnClickListener {
            val eventClass = "250"
            val action = HomeFragmentDirections.actionFirstFragmentToRaces450Fragment(eventClass)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
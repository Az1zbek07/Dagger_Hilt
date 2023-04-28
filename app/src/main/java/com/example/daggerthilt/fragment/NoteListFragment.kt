package com.example.daggerthilt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daggerthilt.R
import com.example.daggerthilt.adapter.NoteAdapter
import com.example.daggerthilt.databinding.FragmentNoteListBinding
import com.example.daggerthilt.viewmodel.NoteViewModel
import com.example.daggerthilt.viewmodel.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list) {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!
    private val noteListAdapter by lazy { NoteAdapter() }
    private val viewModel: NoteViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNoteListBinding.bind(view)

        viewModel.getAllNotes()
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteListAdapter
        }
        lifecycleScope.launch{
            viewModel.state.collect{
                when(it){
                    is State.Empty -> Unit
                    is State.Loading -> {
                        binding.pr.isVisible = true
                        binding.rv.isVisible = false
                        binding.btnAdd.isVisible = false
                    }
                    is State.Error -> {
                        binding.pr.isVisible = false
                        binding.rv.isVisible = true
                        binding.btnAdd.isVisible = true
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is State.Success -> {
                        binding.pr.isVisible = false
                        binding.rv.isVisible = true
                        binding.btnAdd.isVisible = true
                        noteListAdapter.submitList(it.noteList)
                    }
                }
            }
        }

        noteListAdapter.onClick = {
            val bundle = bundleOf("data" to it)
            findNavController().navigate(R.id.action_noteListFragment_to_detailFragment, bundle)
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_editNoteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
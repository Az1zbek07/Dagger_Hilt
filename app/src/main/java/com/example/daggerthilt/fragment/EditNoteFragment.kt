package com.example.daggerthilt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.daggerthilt.R
import com.example.daggerthilt.databinding.FragmentEditNoteBinding
import com.example.daggerthilt.model.NoteResponseItem
import com.example.daggerthilt.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditNoteBinding.bind(view)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {
            val title = binding.editTitle.text.toString().trim()
            val body = binding.editBody.text.toString().trim()

            if (title.isNotBlank() && body.isNotBlank()){
                val noteResponseItem = NoteResponseItem(title = title, body = body, userId = 1, myId = 1, id = 1)

                lifecycleScope.launch{
                    viewModel.saveNote(noteResponseItem)
                }

                Toast.makeText(requireContext(), "Note saved successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
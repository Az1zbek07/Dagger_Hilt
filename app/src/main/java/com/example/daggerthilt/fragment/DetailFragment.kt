package com.example.daggerthilt.fragment

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.daggerthilt.R
import com.example.daggerthilt.adapter.NoteAdapter
import com.example.daggerthilt.databinding.FragmentDetailBinding
import com.example.daggerthilt.databinding.FragmentNoteListBinding
import com.example.daggerthilt.model.NoteResponseItem
import com.example.daggerthilt.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        val noteResponseItem = arguments?.getParcelable<NoteResponseItem>("data")!!
        binding.textBody.text = noteResponseItem.body
        binding.textTitle.text = noteResponseItem.title
        binding.btnLiked.setOnClickListener {
            viewModel.saveNote(noteResponseItem)
            Toast.makeText(requireContext(), "Note liked", Toast.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
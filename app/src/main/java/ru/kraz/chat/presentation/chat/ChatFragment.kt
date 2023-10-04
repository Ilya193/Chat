package ru.kraz.chat.presentation.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kraz.chat.R
import ru.kraz.chat.databinding.FragmentChatBinding
import ru.kraz.chat.databinding.FragmentSignUpBinding
import ru.kraz.chat.presentation.BaseFragment

class ChatFragment : BaseFragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() =
            ChatFragment()
    }
}
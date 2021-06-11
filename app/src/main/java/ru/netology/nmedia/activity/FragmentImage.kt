package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentImageBinding
import ru.netology.nmedia.view.load

class FragmentImage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentImageBinding.inflate(inflater, container, false)

        arguments?.textArg.let {
            binding.attachmentView.load("${BuildConfig.BASE_URL}/media/$it")
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }
        return binding.root
    }

}
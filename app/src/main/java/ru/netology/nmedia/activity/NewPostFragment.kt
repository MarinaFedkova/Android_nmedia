package ru.netology.nmedia.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        binding.edit.requestFocus()

        arguments?.textArg?.let(binding.edit::setText)

        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.edit.text.toString())
            viewModel.save()
            savecontent("")
            AndroidUtils.hideKeyboard(requireView())
        }
        viewModel.postCreated.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
        viewModel.networkError.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), "${resources.getString(R.string.network_error)} $it", Snackbar.LENGTH_LONG).show()
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val content = binding.edit.text.toString()
            savecontent(content)
            findNavController().navigateUp()
        }
        return binding.root
    }
    fun savecontent(string: String) {
        context?.openFileOutput("savecontent.json", Context.MODE_PRIVATE)?.bufferedWriter().use {
            if (it != null) {
                it.write(string)
            }
        }
    }
}


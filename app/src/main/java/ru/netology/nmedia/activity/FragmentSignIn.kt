package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentSignInBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.AuthViewModel

class FragmentSignIn : Fragment() {
    private val viewModelAuth: AuthViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.buttonSignIn.setOnClickListener {
                val login = binding.login.text?.trim().toString()
                val pass = binding.password.text?.trim().toString()
                viewModelAuth.authentication(login, pass)
                AndroidUtils.hideKeyboard(it)
                findNavController().navigateUp()
            }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }
        return binding.root
    }
}

/*binding.buttonSignIn.setOnClickListener {
    if (binding.login.text == null) {
        binding.login.error = getString(R.string.error_edit)

    } else {
        val login = binding.login.text.toString()
        val pass = binding.password.text?.trim().toString()

        viewModelAuth.authentication(login, pass)
        AndroidUtils.hideKeyboard(it)
        findNavController().navigateUp()
    }

}*/
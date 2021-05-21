import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class EditPostFragment : Fragment() {
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
        val binding = FragmentEditPostBinding.inflate(inflater, container, false)

        binding.editEdit.requestFocus()
        arguments?.textArg?.let(binding.editEdit::setText)
        binding.okEdit.setOnClickListener {
            viewModel.changeContent(binding.editEdit.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }

        viewModel.networkError.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), "${resources.getString(R.string.network_error)} $it", Snackbar.LENGTH_LONG).show()
        })

        return binding.root
    }
}
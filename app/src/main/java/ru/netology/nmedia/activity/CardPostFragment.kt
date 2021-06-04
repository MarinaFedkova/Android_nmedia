package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.nmedia.databinding.FragmentCardPostBinding
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.viewmodel.PostViewModel


class CardPostFragment : Fragment() {
    companion object {
        var Bundle.postId by LongArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCardPostBinding.inflate(inflater, container, false)

        val postId = requireArguments().postId ?: error("Post id is required")

        viewModel.getPostById(postId).let { /*post ->
           binding.apply {
                author.text = post.author

                val url = "http://10.0.2.2:9999/avatars/${post.authorAvatar}"
                Glide.with(avatar)
                    .load(url)
                    .timeout(10_000)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_loading_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(avatar)
                
                published.text = post.published
                content.text = post.content
                like.isChecked = post.likedByMe
                like.text = "${post.likes}"
                if (!post.videoUrl.isNullOrEmpty()) {
                    video.visibility = View.VISIBLE
                } else video.visibility = View.GONE

                if (post.attachment?.url != null) {
                    attachmentView.visibility = View.VISIBLE
                     val urlAttachment = "http://10.0.2.2:9999/images/${post.attachment?.url}"
                     Glide.with(binding.attachmentView)
                         .load(urlAttachment)
                         .timeout(10_000)
                         .placeholder(R.drawable.ic_baseline_loading_24)
                         .error(R.drawable.ic_baseline_error_24)
                         .into(attachmentView)
                 } else attachmentView.visibility = View.GONE

                menu.setOnClickListener { it ->
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.remove -> {
                                    viewModel.removeById(postId)
                                    findNavController().navigateUp()
                                    true
                                }
                                R.id.edit -> {
                                    viewModel.edit(post)
                                    true
                                }
                                else -> false
                            }
                        }
                    }.show()
                }
                like.setOnClickListener {
                    viewModel.likeById(postId)
                }
                repost.setOnClickListener {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plane"
                    }
                    val repostIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_repost))
                    startActivity(repostIntent)
                }
                video.setOnClickListener {
                    viewModel.video()
                }
                //  like.text = displayNumbers(post.likes)
                //   repost.text = displayNumbers(post.reposts)
            }
       */ }

        return binding.root
    }
}
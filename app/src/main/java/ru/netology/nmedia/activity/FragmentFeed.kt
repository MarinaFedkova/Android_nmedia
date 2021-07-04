package ru.netology.nmedia.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nmedia.R
import ru.netology.nmedia.R.id.action_fragmentFeed_to_editPostFragment
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInterfactionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.AuthViewModel
import ru.netology.nmedia.viewmodel.PostViewModel
import java.io.File

class FragmentFeed : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private val viewModelAuth: AuthViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )


    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostsAdapter(object : OnInterfactionListener {
            override fun onEdit(post: Post) {
                findNavController().navigate(
                    action_fragmentFeed_to_editPostFragment,
                    Bundle().apply
                    {
                        textArg = post.content
                        viewModel.edit(post)
                    })
            }

            override fun onLike(post: Post) {
                if (!viewModelAuth.authenticated) {
                    findNavController().navigate(R.id.action_fragmentFeed_to_fragmentSignIn)
                    return
                } else viewModel.likeById(post.id)

            }

            override fun onRemove(post: Post) {
                if (!viewModelAuth.authenticated) {
                    findNavController().navigate(R.id.action_fragmentFeed_to_fragmentSignIn)
                    return
                }
                viewModel.removeById(post.id)
            }

            override fun onRepost(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val repostIntent = Intent.createChooser(intent, getString(R.string.chooser_repost))
                startActivity(repostIntent)
                //viewModel.repostById(post.id)
            }

            override fun onVideo(post: Post) {
//                post.videoUrl?.let { viewModel.video() }
//                if (!post.videoUrl.isNullOrEmpty()) {
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
//                    startActivity(intent)
//                }
            }

            override fun onOpenPost(post: Post) {
                findNavController().navigate(R.id.action_fragmentFeed_to_cardPostFragment,
                    Bundle().apply {
                        putParcelable("post", post)
                    })
            }

            override fun onViewImage(post: Post) {
                findNavController().navigate(R.id.action_fragmentFeed_to_fragmentImage,
                    Bundle().apply
                    {
                        textArg = post.attachment?.url
                    })
            }

        })

        binding.list.adapter = adapter
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swipeRefreshLayout.isRefreshing = state.refresh
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        }

        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts)
            binding.emptyText.isVisible = state.empty
        }

        val badge = requireContext().let { BadgeDrawable.create(it) }
            .apply { isVisible = true }

        binding.newer.doOnPreDraw { BadgeUtils.attachBadgeDrawable(badge, binding.newer) }
        binding.newer.isInvisible = true

        viewModel.newerCount.observe(viewLifecycleOwner) { state ->
            if (state > 0) {
                binding.newer.isVisible = true
                badge.number = state
            }else {
                binding.newer.isInvisible = true
            }
        }

        binding.newer.setOnClickListener {
             binding.list.smoothScrollToPosition(0)
            binding.newer.isInvisible = true
            viewModel.readAll()

        }

        binding.fab.setOnClickListener {
            if (!viewModelAuth.authenticated) {
                findNavController().navigate(R.id.action_fragmentFeed_to_fragmentSignIn)
                return@setOnClickListener
            }
            findNavController().navigate(R.id.action_fragmentFeed_to_newPostFragment,
                Bundle().apply
                {
                    val file = File(context?.filesDir, "savecontent.json")
                    if (file.exists()) textArg = file.readText()
                })
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPosts()
        }
        return binding.root
    }
}

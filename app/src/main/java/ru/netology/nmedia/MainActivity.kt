package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.Post
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    var countReposts = 10000
    var countLikes = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netology.ru",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            like?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
                countLike.setText(
                    if (post.likedByMe) displayNumbers(countLikes--) else displayNumbers(countLikes++)
                )
            }
            repost?.setOnClickListener {
                countRepost.setText(displayNumbers(countReposts++))
            }
        }
    }

    fun displayNumbers(number: Int): String {
        val decimalFormat = DecimalFormat("#.#")
        return when (number) {
            in 0..999 -> "$number"
            in 1000..99_999 -> "${decimalFormat.format(number.toFloat() / 1_000)}K"
            in 10_000..999_999 -> "${number / 1_000}K"
            else -> "${decimalFormat.format(number.toFloat() / 1_000_000)}M"
        }
    }
}

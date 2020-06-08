package gst.trainingcourse.pagingimpl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import gst.trainingcourse.pagingimpl.local.model.Article
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter : PagedListAdapter<Article, NewsListAdapter.NewsViewHolder>(newsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val newsDiffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(news: Article?) {
            if (news != null) {
                itemView.txt_news_name.text = news.title
                if (!news.urlToImage.isNullOrEmpty())
                    Picasso.get().load(news.urlToImage).into(itemView.img_news_banner)
            }
        }

        companion object {
            fun create(parent: ViewGroup): NewsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_news, parent, false)
                return NewsViewHolder(view)
            }
        }
    }
}
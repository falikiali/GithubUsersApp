package com.falikiali.githubusersapp.presentation.activity.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.falikiali.githubusersapp.R
import com.falikiali.githubusersapp.databinding.RowItemUserBinding
import com.falikiali.githubusersapp.domain.model.SearchUserItem

class MainAdapter : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {
    private var listUser = ArrayList<SearchUserItem>()

    fun setItems(newList: List<SearchUserItem>) {
        clearItems()
        listUser.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        listUser.clear()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RowItemUserBinding.bind(itemView)

        fun bind(data: SearchUserItem) {
            with(binding) {
                tvUser.text = data.login
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .into(ivAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listUser[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listUser.size
}
package com.anshdeep.takehome.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anshdeep.takehome.databinding.ItemRepositoryBinding
import com.anshdeep.takehome.network.response.Repository


class UserRepoAdapter(
    private var repositories: List<Repository>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<UserRepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRepositoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(repositories[position], listener)
    }

    override fun getItemCount(): Int = repositories.size

    interface OnItemClickListener {
        fun onRepositoryClick(repository: Repository)
    }

    fun replaceData(arrayList: List<Repository>) {
        repositories = arrayList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: Repository, listener: OnItemClickListener?) {
            binding.repositoryName.text = repository.name
            binding.repositoryDescription.text = repository.description
            listener?.let {
                binding.root.setOnClickListener { listener.onRepositoryClick(repository) }
            }
        }
    }
}
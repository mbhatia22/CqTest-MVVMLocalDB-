package com.ankit.cqlsys.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ankit.cqlsys.databinding.ItemUsersBinding
import com.ankit.cqlsys.model.RegisterModel
import com.ankit.cqlsys.utils.AllUserDiff

class AllUsersAdapter : RecyclerView.Adapter<AllUsersAdapter.VIewHolder>() {

    private var list = ArrayList<RegisterModel>()

    fun setData(newList: ArrayList<RegisterModel>) {
        val diff = AllUserDiff(list, newList)
        val diffResult = DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class VIewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIewHolder =
        VIewHolder(ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VIewHolder, position: Int) {
        val item = list[holder.adapterPosition]
        val binding = holder.binding

        binding.txtName.text = "Name: ${item.name}"
        binding.txtCountry.text = "Country: ${item.country}"
        binding.txtEmail.text = "Email: ${item.email}"
        binding.txtPhone.text = "Phone: ${item.phone}"
        binding.txtGender.text = "Gender: ${item.gender}"

    }

    override fun getItemCount(): Int = list.size
}
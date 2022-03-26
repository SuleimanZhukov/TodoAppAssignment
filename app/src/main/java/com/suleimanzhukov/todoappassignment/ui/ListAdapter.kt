package com.suleimanzhukov.todoappassignment.ui

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.widget.CompoundButtonCompat
import androidx.recyclerview.widget.RecyclerView
import com.suleimanzhukov.todoappassignment.R
import com.suleimanzhukov.todoappassignment.databinding.ListItemViewBinding
import com.suleimanzhukov.todoappassignment.model.entities.Task

class ListAdapter(
    private val context: Context,
    private var tasksList: MutableList<Task>,
    private val resources: Resources,
    private val clickListener: MainActivity.ClickListener
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    lateinit var binding: ListItemViewBinding

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) = with(binding) {
            textView.text = tasksList[position].name
            priorityToColor(position, taskCheckbox)

            taskCheckbox.setOnCheckedChangeListener { compoundButton, b ->
                clickListener.onCheckChanged(textView, compoundButton, position)
            }

            root.setOnLongClickListener { clickListener.onLongClick(position) }

            clickListener.initCheckbox(taskCheckbox, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ListViewHolder {
        binding = ListItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    fun addTask(task: Task) {
        tasksList.add(task)
        notifyItemInserted(tasksList.size - 1)
    }

    fun addAllTasks(tasks: MutableList<Task>) {
        tasksList = tasks
        notifyDataSetChanged()
    }

    fun editTask(task: Task, position: Int) {
        tasksList[position].name = task.name
        tasksList[position].priority = task.priority
        notifyDataSetChanged()
    }

    private fun priorityToColor(position: Int, checkBox: CheckBox) {
        when(tasksList[position].priority) {
            0 -> changeCheckboxColor(resources, R.color.checkbox_red, checkBox)
            1 -> changeCheckboxColor(resources, R.color.checkbox_red, checkBox)
            2 -> changeCheckboxColor(resources, R.color.checkbox_blue, checkBox)
            else -> changeCheckboxColor(resources, R.color.checkbox_green, checkBox)
        }
    }

    private fun changeCheckboxColor(resources: Resources, color: Int, checkBox: CheckBox) {
        val colorFilter = PorterDuffColorFilter(resources.getColor(color), PorterDuff.Mode.SRC_ATOP)
        CompoundButtonCompat.getButtonDrawable(checkBox)?.colorFilter = colorFilter
    }
}

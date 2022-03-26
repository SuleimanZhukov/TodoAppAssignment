package com.suleimanzhukov.todoappassignment.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.suleimanzhukov.todoappassignment.R
import com.suleimanzhukov.todoappassignment.TodoApp
import com.suleimanzhukov.todoappassignment.databinding.ActivityMainBinding
import com.suleimanzhukov.todoappassignment.model.entities.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: ListViewModel

    lateinit var listAdapter: ListAdapter

    private var tasksList: MutableList<Task> = mutableListOf()
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TodoApp.instance.appComponent.value.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPrefs = getSharedPreferences(SELECTED_TASKS, Context.MODE_PRIVATE)

        subscribeToLiveData(sharedPrefs)
        onAddFloatingButtonClick()
    }

    private fun setAdapter(sharedPrefs: SharedPreferences) = with(binding) {
        listAdapter = ListAdapter(this@MainActivity, tasksList, resources, object : ClickListener {
            override fun onLongClick(position: Int): Boolean {

                if (tasksList[position].isSelected) {
                    return true
                }

                val dialogFragmentEdit = DialogFragmentEdit(listAdapter, tasksList[position], position, tasksList)
                dialogFragmentEdit.show(supportFragmentManager, "")
                return true
            }

            override fun onCheckChanged(
                textView: TextView, compoundButton: CompoundButton, position: Int
            ) {
                if (compoundButton.isChecked) {

                    textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    textView.setTextColor(resources.getColor(R.color.checked_grey_text))
                    tasksList[position].isSelected = true

                    val TASK_ID = "${tasksList[position].id}"
                    sharedPrefs.edit().putLong(TASK_ID, tasksList[position].id).apply()

                    setDeleteFloatingButton(true, -200f, 1f)
                    onDelete(position)

                } else {
                    textView.paintFlags = 0
                    tasksList[position].isSelected = false
                    textView.setTextColor(resources.getColor(R.color.black))

                    val TASK_ID = "${tasksList[position].id}"
                    sharedPrefs.edit().remove(TASK_ID).apply()

                    var count = 0
                    for (i in 0 until tasksList.size) {
                        if (tasksList[i].isSelected) {
                            count++
                        }
                    }

                    if (count == 0 || tasksList.size == 0) {
                        setDeleteFloatingButton(false, 0f, 0f)
                    }
                }
            }

            override fun initCheckbox(checkBox: CheckBox, position: Int) {
                var count = 0

                if (tasksList[position].isSelected) {
                    checkBox.isChecked = true
                    count++
                }

                if (count != 0 && tasksList.size != 0) {
                    setDeleteFloatingButton(true, -200f, 1f)
                    onDelete(position)
                }
            }
        })

        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
    }

    private fun subscribeToLiveData(sharedPrefs: SharedPreferences) {
        viewModel.getTasksListLiveData().observe(this, Observer {
            tasksList = viewModel.getTasksListLiveData().value!!
        })

        CoroutineScope(Main).launch {
            val job = async(IO) {
                viewModel.getAllTasks()
            }
            job.await()

            for (i in 0 until tasksList.size) {
                if (tasksList[i].id == sharedPrefs.getLong(tasksList[i].id.toString(), -1)) {
                    tasksList[i].isSelected = true
                }
            }

            setAdapter(sharedPrefs)
        }
    }

    private fun onAddFloatingButtonClick() = with(binding) {
        deleteButtonContainer.alpha = 0f
        deleteButton.isClickable = false
        addButton.setOnClickListener {
            setAlertDialog()
        }
    }

    private fun setDeleteFloatingButton(boolean: Boolean, animValue: Float, alphaAnim: Float) = with(binding) {
        isExpanded = boolean
        var anim = ObjectAnimator.ofFloat(deleteButtonContainer, "translationY", animValue)
        anim.duration = 500
        anim.start()
        deleteButtonContainer.animate()
            .alpha(alphaAnim)
            .setDuration(500)
            .start()
        deleteButton.isClickable = boolean
    }

    private fun onDelete(position: Int) = with(binding) {
        deleteButton.setOnClickListener {
            var tasksToDelete = mutableListOf<Task>()
            for (i in 0 until tasksList.size) {
                if (tasksList[i].isSelected) {
                    tasksToDelete.add(tasksList[i])
                }
            }

            CoroutineScope(Main).launch {
                val deleteJob = async(IO) {
                    for (i in 0 until tasksToDelete.size) {
                        viewModel.deleteTaskById(tasksToDelete[i])
                    }
                }
                deleteJob.await()

                val getJob = async(IO) {
                    viewModel.getAllTasks()
                }
                getJob.await()
                listAdapter.addAllTasks(tasksList)
                setDeleteFloatingButton(false, 0f, 0f)
            }
        }
    }

    private fun setAlertDialog() {
        val dialogFragment = DialogFragment()
        dialogFragment.getListAdapter(listAdapter)
        dialogFragment.show(supportFragmentManager, "")
    }

    interface ClickListener {
        fun onLongClick(position: Int): Boolean

        fun onCheckChanged(textView: TextView, compoundButton: CompoundButton, position: Int)

        fun initCheckbox(checkBox: CheckBox, position: Int)
    }

    companion object {
        const val SELECTED_TASKS = "SELECTED_TASKS"
//        const val TASK_ID = "TASK_ID"
    }
}
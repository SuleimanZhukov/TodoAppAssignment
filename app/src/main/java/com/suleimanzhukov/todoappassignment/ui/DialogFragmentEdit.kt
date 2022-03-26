package com.suleimanzhukov.todoappassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.suleimanzhukov.todoappassignment.TodoApp
import com.suleimanzhukov.todoappassignment.databinding.FragmentDialogEditBinding
import com.suleimanzhukov.todoappassignment.model.entities.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DialogFragmentEdit(
    private val listAdapter: ListAdapter,
    private var task: Task,
    private val position: Int,
    private var tasksList: MutableList<Task>
) : DialogFragment() {

    private var _binding: FragmentDialogEditBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TodoApp.instance.appComponent.value.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDialogEditBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initEditDialog()
        onButtonsClick()
    }

    private fun initEditDialog() = with(binding) {
        taskEditText.setText(task.name)
        radioGroup.check(radioButtonRed.id)
        when(task.priority) {
            0 -> radioGroup.check(radioButtonRed.id)
            1 -> radioGroup.check(radioButtonRed.id)
            2 -> radioGroup.check(radioButtonBlue.id)
            else -> radioGroup.check(radioButtonGreen.id)
        }
    }

    private fun onButtonsClick() = with(binding) {
        dialogEditButton.setOnClickListener {
            task.name = taskEditText.text.toString()
            task.priority = setPriority()
            CoroutineScope(Main).launch {
                val job = async(IO) {
                    viewModel.editTaskById(task)
                }
                job.await()
                val getJob = async(IO) {
                    task = viewModel.getTaskById(task.id)
                }
                getJob.await()
//                val getJob = async(IO) {
//                    viewModel.getAllTasks()
//                }
//                getJob.await()
                listAdapter.editTask(task, position)
            }
            dismiss()
        }

        dialogCancelButton.setOnClickListener {
            Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun setPriority(): Int = with(binding) {
        return when(radioGroup.checkedRadioButtonId) {
            radioButtonRed.id -> 1
            radioButtonBlue.id -> 2
            else -> 3
        }
    }
}
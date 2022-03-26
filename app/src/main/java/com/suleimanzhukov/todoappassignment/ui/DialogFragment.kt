package com.suleimanzhukov.todoappassignment.ui

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.suleimanzhukov.todoappassignment.R
import com.suleimanzhukov.todoappassignment.TodoApp
import com.suleimanzhukov.todoappassignment.databinding.FragmentDialogAddBinding
import com.suleimanzhukov.todoappassignment.model.entities.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DialogFragment : DialogFragment() {

    private var _binding: FragmentDialogAddBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: ListViewModel

    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TodoApp.instance.appComponent.value.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDialogAddBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onButtonsClick()
    }

    private fun onButtonsClick() = with(binding) {
        dialogAddButton.setOnClickListener {
            if (taskEditText.text.toString() != "") {
                val task = Task(0, taskEditText.text.toString(), setPriority())
                CoroutineScope(Main).launch {
                    val job = async(IO) {
                        viewModel.addTask(task)
                    }
                    job.await()
                    val getJob = async(IO) {
                        viewModel.getAllTasks()
                    }
                    getJob.await()
                    listAdapter.addTask(task)
                    dismiss()
                }
            } else {
                taskEditText.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.checkbox_red))
                taskEditText.setHintTextColor(resources.getColor(R.color.checkbox_red))
                Toast.makeText(context, "Type something", Toast.LENGTH_SHORT).show()
            }
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

    fun getListAdapter(listAdapter: ListAdapter) {
        this.listAdapter = listAdapter
    }
}
package com.example.quizapp.presentation.ui.fragments.history

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.common.base.BaseFragment
import com.example.quizapp.databinding.FragmentHistoryBinding
import com.example.quizapp.domain.entities.HistoryEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val viewModel: HistoryViewModel by viewModels()
    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter()
    }
    private val args: HistoryFragmentArgs by navArgs()

    override fun setupObservers() {
        if (args.difficulty != "" && args.category != "" && args.correctAnswers != "") {
            viewModel.addHistory(HistoryEntity(args.difficulty, args.category, args.correctAnswers))
        }
        viewModel.historyList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { handleHistory(it) }.launchIn(lifecycleScope)
    }

    private fun handleHistory(it: List<HistoryEntity>) {
        adapter.submitList(it)
    }

    override fun setupUI() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HistoryFragment.adapter
        }
        setupSwipeListeners(binding.rvHistory)
    }

    private fun setupSwipeListeners(rvHistory: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.absoluteAdapterPosition]
                viewModel.deleteHistory(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvHistory)
    }

    override fun setupListeners() {

    }

    override fun bind(): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(layoutInflater)
    }

}
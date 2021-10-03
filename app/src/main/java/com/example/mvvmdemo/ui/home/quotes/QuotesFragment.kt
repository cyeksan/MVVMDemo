package com.example.mvvmdemo.ui.home.quotes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmdemo.R
import com.example.mvvmdemo.data.db.entities.Quote
import com.example.mvvmdemo.databinding.QuotesFragmentBinding
import com.example.mvvmdemo.util.Coroutines
import com.example.mvvmdemo.util.hide
import com.example.mvvmdemo.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: QuotesViewModel
    private lateinit var binding: QuotesFragmentBinding
    private val factory: QuotesViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, factory).get(QuotesViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.quotes_fragment, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()

    }

    private fun bindUI() = Coroutines.main {
        binding.progressBar.show()
        viewModel.quotes.await().observe(
            viewLifecycleOwner, {

                binding.progressBar.hide()
                initRecyclerView(it.toQuoteItem())
            }
        )
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter

        }
    }

    private fun List<Quote>.toQuoteItem() : List<QuoteItem> {
        return this.map {
            QuoteItem(it)
        }
    }

}
package com.kattabozor.task.presentation.screen.offer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kattabozor.task.R
import com.kattabozor.task.common.Constants
import com.kattabozor.task.common.Resource
import com.kattabozor.task.databinding.FragmentOfferBinding
import com.kattabozor.task.domain.model.Offer
import com.kattabozor.task.presentation.adapter.OfferProductAdapter
import com.kattabozor.task.presentation.adapter.PlaceholderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfferFragment : Fragment(), OfferProductAdapter.OnOfferClickListener {

    private var _binding: FragmentOfferBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OfferViewModel by viewModels()
    private val holderAdapter = PlaceholderAdapter()
    private val adapter = OfferProductAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeProducts()
    }

    private fun observeProducts() {
        lifecycleScope.launch {
            viewModel.remoteOfferProductsFlow.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        changeLoadingState(true)
                    }
                    is Resource.Success -> {
                        changeLoadingState(false)
                        adapter.submitList(state.data)
                    }
                    is Resource.Error -> {
                        changeLoadingState(false)
                    }
                }
            }
        }
    }

    private fun changeLoadingState(isLoading: Boolean) = with(binding) {
        shimmerView.isVisible = isLoading
        productRv.isVisible = !isLoading

        if (isLoading) shimmerView.startShimmer() else shimmerView.stopShimmer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        viewModel.fetchOfferProducts()
    }

    private fun initUI() = with(binding) {
        shimmerRv.adapter = holderAdapter

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        root.setPadding(0, getStatusBarHeight(), 0, 0)
        productRv.layoutManager = layoutManager
        productRv.itemAnimator = DefaultItemAnimator()
        productRv.adapter = adapter

        productRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManager.invalidateSpanAssignments()
            }
        })
    }

    override fun onOfferClicked(offer: Offer) {
        findNavController().navigate(
            R.id.action_nav_offer_to_detailFragment,
            bundleOf(Pair(Constants.OFFER_BUNDLE_KEY, offer))
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}
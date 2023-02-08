package com.kattabozor.task.presentation.screen.details

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.kattabozor.task.R
import com.kattabozor.task.common.Constants
import com.kattabozor.task.common.Resource
import com.kattabozor.task.databinding.FragmentDetailBinding
import com.kattabozor.task.domain.model.Offer
import com.kattabozor.task.presentation.TaskKbView
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.abs

@AndroidEntryPoint
class DetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

    private var offer: Offer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            offer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(Constants.OFFER_BUNDLE_KEY, Offer::class.java)
            } else {
                it.getSerializable(Constants.OFFER_BUNDLE_KEY) as Offer
            }
            observeOfferState()
        }
    }

    private fun observeOfferState() {
        lifecycleScope.launch {
            viewModel.getLocalOfferProductsFlow.collect{state->
                when(state){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        if (state.data!=null){
                            binding.saveIv.setImageResource(R.drawable.ic_baseline_bookmark_24)
                        }else{
                            binding.saveIv.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                        }
                    }
                    is Resource.Error -> {}
                }

            }
        }
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private var state: State? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (offer != null) {
            setupUI(offer!!)
            viewModel.getSavedOffer(offer!!.id)
        }else{
            findNavController().popBackStack()
            Toast.makeText(requireContext(), R.string.an_error_occurred, Toast.LENGTH_LONG).show()
        }
        (activity as TaskKbView?)?.hideNavView()
    }

    private fun setupUI(product: Offer) = with(binding) {
        backIv.setOnClickListener { findNavController().popBackStack() }
        appbar.addOnOffsetChangedListener(this@DetailFragment)
        Picasso.get().load(product.image.url)
            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
            .placeholder(R.drawable.ic_placeholder_image)
            .into(object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    photoIv.setImageBitmap(bitmap)
                    shimmerLayout.visibility = View.GONE
                    shimmerLayout.stopShimmer()
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    photoIv.setImageResource(R.drawable.ic_file_download_off_24)
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    shimmerLayout.visibility = View.VISIBLE
                    shimmerLayout.startShimmer()
                }

            })

        toolbarTitleTv.text = product.name
        toolbarTitleTv.isSelected = true
        brandTv.text = product.brand
        offerNameTv.text = product.name
        categoryTv.text = product.category

        val attrBuilder = java.lang.StringBuilder()
        product.attributes.forEachIndexed { index, attr ->
            if (index != 0) {
                attrBuilder.append("\n")
            }
            attrBuilder.append("${attr.name}: ${attr.value}")
        }
        attributesTv.text = attrBuilder.toString()

        merchantTv.text = product.merchant

        saveIv.setOnClickListener {
            viewModel.switchSavedState(offer!!)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == 0) {
            if (state != State.EXPANDED) {
                setVisibilityLayoutViews(View.VISIBLE)
                setVisibilityToolbarViews(View.GONE)
            }
            state = State.EXPANDED
        } else if (abs(verticalOffset) >= appBarLayout?.totalScrollRange!!) {
            if (state != State.COLLAPSED) {
                setVisibilityLayoutViews(View.GONE)
                setVisibilityToolbarViews(View.VISIBLE)
            }
            state = State.COLLAPSED
        } else {
            state = State.IDLE
        }
    }

    private fun setVisibilityLayoutViews(visibility: Int) {
        binding.offerNameTv.visibility = visibility
    }

    private fun setVisibilityToolbarViews(visibility: Int) {
        binding.toolbarTitleTv.visibility = visibility
        binding.brandTv.visibility = visibility
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as TaskKbView?)?.showNavView()
        _binding = null
    }

}

private enum class State {
    EXPANDED, COLLAPSED, IDLE
}
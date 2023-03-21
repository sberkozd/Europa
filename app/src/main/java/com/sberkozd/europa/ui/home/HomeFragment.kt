package com.sberkozd.europa.ui.home

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sberkozd.europa.R
import com.sberkozd.europa.databinding.FragmentHomeBinding
import com.sberkozd.europa.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding?.apply { registerForContextMenu(this.fragmentHomeBtnFilter) }
        _binding?.apply { registerForContextMenu(this.fragmentHomeBtnSort) }

        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CountryListAdapter(clickListener = { country ->
            findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(country)
                )
        })

        binding.rvCountryList.adapter = adapter

        binding.etSearchView.textChanges()
            .debounce(500)
            .onEach { query ->
                if (query.toString().length > 2) {
                    adapter.submitList(homeViewModel.countryListResponse.value.filter {
                        it.name?.common?.lowercase()?.contains(query.toString().lowercase())
                            ?: false
                    }.toMutableList())
                } else {
                    adapter.submitList(homeViewModel.countryListResponse.value.toMutableList())
                }

            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.fragmentHomeBtnSort.setOnClickListener {
            binding.fragmentHomeBtnSort.showContextMenu(
                binding.fragmentHomeBtnSort.x,
                binding.fragmentHomeBtnSort.y
            )
        }


        binding.fragmentHomeBtnFilter.setOnClickListener {
            homeViewModel.showFilterDialog()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.countryListResponse.collect {
                    adapter.submitList(it.toMutableList())
                }
            }
        }

        homeViewModel.eventsFlow.onEach { event ->
            when (event) {
                is HomeViewModel.SortEvent.SortAlphabetically,
                is HomeViewModel.SortEvent.SortByPopulation,
                is HomeViewModel.SortEvent.SortByArea -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        adapter.submitList((event as HomeViewModel.SortEvent).list.toMutableList())
                        delay(50)
                        binding.rvCountryList.scrollToPosition(0)
                    }

                }
                is HomeViewModel.Event.ShowFilterDialog -> {
                    showFilterDialog(event.subRegionList, event.lastSelectedFilter)
                }
                is HomeViewModel.Event.FilterBySubRegion -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        adapter.submitList(event.list.toMutableList())
                        delay(50)
                        binding.rvCountryList.scrollToPosition(0)
                    }
                }
            }
        }.launchIn(lifecycleScope)

    }

    private fun showFilterDialog(subRegionList: List<String>, lastSelectedFilter: String) {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.layout_bottom_sheet)

        dialog.findViewById<AutoCompleteTextView>(R.id.dropdown_subRegion)?.apply {
            ArrayAdapter(
                requireActivity(),
                R.layout.layout_dropdown_item,
                subRegionList
            ).also { adapter ->
                setAdapter(adapter)
            }
        }

        if (lastSelectedFilter.isNotEmpty()) {
            dialog.findViewById<AutoCompleteTextView>(R.id.dropdown_subRegion)
                ?.setText(lastSelectedFilter, false)
        }

        dialog.findViewById<Button>(R.id.btn_apply)?.setOnClickListener {
            val subRegion =
                dialog.findViewById<AutoCompleteTextView>(R.id.dropdown_subRegion)?.text.toString()
            homeViewModel.filterBySubRegion(subRegion)
            dialog.dismiss()
        }

        dialog.show()

    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle("Sort")
        requireActivity().menuInflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.alphabetic -> {
                homeViewModel.sortByAlphabetically()
            }

            R.id.population -> {
                homeViewModel.sortByPopulation()
            }

            R.id.area -> {
                homeViewModel.sortByArea()
            }
        }

        return super.onContextItemSelected(item)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
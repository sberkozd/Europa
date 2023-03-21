package com.sberkozd.europa.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sberkozd.europa.R
import com.sberkozd.europa.data.models.Languages
import com.sberkozd.europa.databinding.FragmentDetailBinding
import com.sberkozd.europa.loadSvg
import com.sberkozd.europa.toMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val args: DetailFragmentArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.ivCountryFlag.loadSvg(args.country.flags?.svg)
        binding.tvCapitalName.text = args.country.capital?.first()
        binding.tvCountryArea.text = (args.country.area.toString() + " k㎡")
        args.country.languages?.let {
            binding.tvCountryLanguages.text =
                toMap<Languages>(it).values.filterNotNull().toString().removePrefix("[")
                    .removeSuffix("]")
        }
        binding.tvCountryName.text = args.country.name?.common.toString()
        binding.tvCountryPopulation.text = args.country.population.toString()
        if (args.country.borders != null) {
            binding.tvCountryListOfBorders.text =
                args.country.borders.toString().removePrefix("[").removeSuffix("]")
        } else {
            binding.tvCountryListOfBorders.setText(R.string.Island)
        }
        binding.tvCountrySubRegion.text = args.country.subregion.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
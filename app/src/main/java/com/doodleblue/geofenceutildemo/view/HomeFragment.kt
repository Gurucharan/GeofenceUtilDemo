package com.doodleblue.geofenceutildemo.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.doodleblue.geofenceutildemo.GeofenceUtilApp
import com.doodleblue.geofenceutildemo.R
import com.doodleblue.geofenceutildemo.databinding.FragmentHomeBinding
import com.doodleblue.geofenceutildemo.util.ItemDecorationSpacing
import com.doodleblue.geofenceutildemo.util.Resource
import com.doodleblue.geofenceutildemo.view.adapter.GeofenceAdapter
import com.doodleblue.geofenceutildemo.view.listener.AddClickListener
import com.doodleblue.geofenceutildemo.view.viewmodel.GeofenceViewModel
import com.doodleblue.geofenceutildemo.view.viewmodel.GeofenceViewModelFactory

private const val TAG = "HomeFragment"

class HomeFragment : Fragment(R.layout.fragment_home), AddClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val geofenceAdapter: GeofenceAdapter by lazy {
        GeofenceAdapter()
    }

    private val viewModel by viewModels<GeofenceViewModel> {
        GeofenceViewModelFactory((activity as GeofenceActivity).appComponent.repository())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as GeofenceUtilApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvGeofences.apply {
            adapter = geofenceAdapter
            addItemDecoration(
                ItemDecorationSpacing(
                    resources.getDimension(R.dimen.vertical_spacing).toInt()
                )
            )
        }
        binding.listener = this
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAvailableGofences()
    }

    private fun showAvailableGofences() {
        viewModel.geofences.observe(viewLifecycleOwner) { geofences ->
            when (geofences) {
                is Resource.Loading -> binding.showLoader = true
                is Resource.Error -> {
                    binding.showLoader = false
                    binding.showError = true
                }
                is Resource.Success -> {
                    binding.showLoader = false
                    geofenceAdapter.geofences = geofences.data!!
                }
            }
        }
    }

    override fun onAddClick(view: View) {
        findNavController().navigate(R.id.action_homeFragment_to_addGeofenceFragment)
    }

}
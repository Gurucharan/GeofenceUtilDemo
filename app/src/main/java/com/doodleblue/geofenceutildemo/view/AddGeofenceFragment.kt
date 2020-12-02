package com.doodleblue.geofenceutildemo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.doodleblue.geofenceutildemo.R
import com.doodleblue.geofenceutildemo.data.model.Location
import com.doodleblue.geofenceutildemo.data.model.Reminder
import com.doodleblue.geofenceutildemo.databinding.FragmentAddGeofenceutildemoBinding
import com.doodleblue.geofenceutildemo.util.Resource
import com.doodleblue.geofenceutildemo.util.Type
import com.doodleblue.geofenceutildemo.util.Type.*
import com.doodleblue.geofenceutildemo.util.hideKeyboard
import com.doodleblue.geofenceutildemo.view.listener.SubmitClickListener
import com.doodleblue.geofenceutildemo.view.viewmodel.AddGeofenceViewModel
import com.doodleblue.geofenceutildemo.view.viewmodel.AddGeofenceViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_geofenceutildemo.view.*

class AddGeofenceFragment : Fragment(R.layout.fragment_add_geofenceutildemo), SubmitClickListener {

    private lateinit var binding: FragmentAddGeofenceutildemoBinding
    private val viewModel by viewModels<AddGeofenceViewModel> {
        AddGeofenceViewModelFactory((activity as GeofenceActivity).appComponent.repository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddGeofenceutildemoBinding.inflate(inflater, container, false)
        binding.listener = this
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun geofenceStatus() {
        viewModel.submitStatus.observe(this) { status ->
            when (status) {
                is Resource.Loading -> binding.showLoader = true
                is Resource.Success -> {
                    binding.showLoader = false
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.add_geofence_success_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_addGeofenceFragment_to_homeFragment)
                }
                is Resource.Error -> {
                    binding.showLoader = false
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.add_geofence_error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onSubmitClick(view: View) {
        when {
            binding.root.tvLocationName.editText?.text.toString().isEmpty() -> {
                setError(NAME)
            }
            binding.root.tvLatitude.editText?.text.toString()
                .isEmpty() -> {
                setError(LATITUDE)
            }
            binding.root.tvLongitude.editText?.text.toString().isEmpty() -> {
                setError(LONGITUDE)
            }
            else -> {
                hideKeyboard(requireContext(), binding.root)
                viewModel.addGeofence(
                    Reminder(
                        name = binding.root.tvLocationName.editText?.text.toString(),
                        location = Location(
                            binding.root.tvLatitude.editText?.text.toString().toDouble(),
                            binding.root.tvLongitude.editText?.text.toString().toDouble()
                        )
                    )
                )
                geofenceStatus()
            }
        }
    }

    private fun setError(type: Type) {
        when (type) {
            NAME -> binding.root.tvLocationName.editText?.error =
                resources.getString(R.string.required_name_error_message)
            LATITUDE -> binding.root.tvLatitude.editText?.error =
                resources.getString(R.string.required_latitude_error_message)
            LONGITUDE -> binding.root.tvLongitude.editText?.error =
                resources.getString(R.string.required_longitude_error_message)
        }
    }
}
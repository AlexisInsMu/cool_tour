package com.example.cool_tour.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cool_tour.R
import com.example.cool_tour.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by viewModels()
    private var googleMap: GoogleMap? = null

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) @androidx.annotation.RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION]) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            googleMap?.isMyLocationEnabled = true
            viewModel.iniciarSeguimientoUbicacion()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        // ← AGREGAR DESDE AQUÍ
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.fabSeleccionarRuta.setOnClickListener {
            findNavController().navigate(R.id.action_map_to_route_selection)
        }

        // Observar ruta seleccionada desde RouteSelectionFragment
        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<List<String>>("rutaSeleccionada")
            ?.observe(viewLifecycleOwner) { poisIds ->
                val poisSeleccionados = viewModel.pois.value
                    ?.filter { it.id in poisIds } ?: emptyList()
                if (poisSeleccionados.isNotEmpty()) {
                    viewModel.trazarRuta(poisSeleccionados)
                }
            }
        // ← HASTA AQUÍ
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }

        viewModel.pois.observe(viewLifecycleOwner) { pois ->
            map.clear()
            pois.forEach { poi ->
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(LatLng(poi.latitud, poi.longitud))
                        .title(poi.nombre)
                        .snippet(poi.descripcion)
                )
                marker?.tag = poi.id
            }
            if (pois.isNotEmpty()) {
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(pois.first().latitud, pois.first().longitud), 14f
                    )
                )
            }
        }


        map.setOnMarkerClickListener { marker: Marker ->
            val poiId = marker.tag as? String
            poiId?.let {
                val bundle = android.os.Bundle().apply { putString("poiId", it) }
                findNavController().navigate(R.id.action_map_to_poi, bundle)
            }
            true
        }
        viewModel.rutaPolilinea.observe(viewLifecycleOwner) { opciones ->
            opciones?.let { map.addPolyline(it) }
        }

        viewModel.cargarPOIs()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
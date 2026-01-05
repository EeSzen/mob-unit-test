package com.eeszen.locationtracking

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.eeszen.locationtracking.service.LocationService
import com.eeszen.locationtracking.ui.navigation.AppNav
import com.eeszen.locationtracking.ui.theme.MOBStarterAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MOBStarterAppTheme {
                ComposeApp()
            }
        }

        checkPermissionAndStart()
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){ perms ->
        if (perms.all {it.value}) {
            startTracking()
        }
    }

    private fun checkPermissionAndStart(){
        val perms = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//            perms.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//        }

        val missing = perms.filter {
            ContextCompat.checkSelfPermission(
                this, it
            ) != PackageManager.PERMISSION_GRANTED
        }

        if (missing.isEmpty()) {
            checkBGPermissionAndStart()
        } else{
            locationPermissionLauncher.launch(missing.toTypedArray())
        }
    }

    private fun checkBGPermissionAndStart(){
//        val perms = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED){
                    bgPermissionLauncher.launch(
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    )
            }
        }

    }

    private val bgPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if (it){
            startTracking()
        }
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){ perms ->
        if (perms.all {it.value}){
            checkBGPermissionAndStart()
        }
    }

    private fun startTracking(){
        ContextCompat.startForegroundService(
            this,
            Intent(this, LocationService::class.java)
        )
    }

    private fun stopTracking(){
        stopService(Intent(this,LocationService::class.java))
    }
}

@Composable
fun ComposeApp(){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            AppNav()
        }
    }
}
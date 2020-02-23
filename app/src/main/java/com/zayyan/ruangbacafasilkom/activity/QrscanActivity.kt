package com.zayyan.ruangbacafasilkom.activity

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ViewfinderView
import com.zayyan.ruangbacafasilkom.R
import java.util.*

class QrscanActivity : Activity(),DecoratedBarcodeView.TorchListener {

    private var capture: CaptureManager? = null
    private var barcodeScannerView: DecoratedBarcodeView? = null
    private var switchFlashlightButton: Button? = null
    private var viewfinderView: ViewfinderView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner) as DecoratedBarcodeView
        barcodeScannerView!!.setTorchListener(this)

        switchFlashlightButton = findViewById(R.id.switch_flashlight) as Button

        viewfinderView = findViewById(R.id.zxing_viewfinder_view) as ViewfinderView

        if (!hasFlash()) {
            switchFlashlightButton!!.visibility = View.GONE
        }

        capture = CaptureManager(this, barcodeScannerView!!)
        capture!!.initializeFromIntent(intent, savedInstanceState)
        capture!!.decode()

        changeMaskColor(null)
    }

    override fun onResume() {
        super.onResume()
        capture!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture!!.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return barcodeScannerView!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    fun switchFlashlight(view: View) {
        if (getString(R.string.turn_on_flashlight) == switchFlashlightButton!!.text) {
            barcodeScannerView!!.setTorchOn()
        } else {
            barcodeScannerView!!.setTorchOff()
        }
    }

    fun changeMaskColor(view: View?) {
        val rnd = Random()
        val color = Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    override fun onTorchOn() {
        switchFlashlightButton!!.setText(R.string.turn_off_flashlight)
    }

    override fun onTorchOff() {
        switchFlashlightButton!!.setText(R.string.turn_on_flashlight)
    }
}

package com.rogergcc.nearplacestest99minutes.ui.utils

//import com.webserveis.mysubscriptions.R
import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.TypedValue
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


/*
https://stackoverflow.com/questions/50617598/how-to-declare-startactivityforresult-in-one-line-in-kotlin
 */


fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, text, duration).show() }

fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, textId, duration).show() }

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }


fun TextInputEditText.onDone(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callback.invoke()
        }
        false
    }
}

fun TextInputLayout.markRequired() {
    hint = "$hint *"
}

fun TextInputLayout.markRequiredInRed() {
    hint = buildSpannedString {
        append(hint)
        color(Color.RED) { append(" *") } // Mind the space prefix.
    }
}

@RequiresPermission(android.Manifest.permission.VIBRATE)
fun Context.vibrate(pattern: LongArray = longArrayOf(0, 150)) {
    val vibrator =
        applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator? ?: return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(
            VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE)
        )

    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(pattern, -1)
    }
}


/**
 * Helper functions to simplify permission checks/requests.
 */
fun Context.hasPermission(permission: String): Boolean {

    // Background permissions didn't exit prior to Q, so it's approved by default.
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q
    ) {
        return true
    }

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Toolbar.setNavigationIconColor(@ColorInt color: Int) = navigationIcon?.mutate()?.let {
    it.setTint(color)
    this.navigationIcon = it
}


@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true,
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}


//fun Context.hasTwoPanels(): Boolean {
//    val res: Resources = resources
//    return res.getBoolean(R.bool.two_panels)
//}


fun getAttrColor(context: Context, @AttrRes attrId: Int): Int {
    val typedValue = TypedValue()
    val theme = context.theme
    theme.resolveAttribute(attrId, typedValue, true)
    return typedValue.data
}

fun Number.roundTo(
    numFractionDigits: Int,
) = "%.${numFractionDigits}f".format(this, Locale.ENGLISH).toDouble()

fun Float.roundToOneDecimalPlace(): Float {
    val df = DecimalFormat("#.#", DecimalFormatSymbols(Locale.ENGLISH)).apply {
        roundingMode = RoundingMode.HALF_UP
    }
    return df.format(this).toFloat()
}

fun Float.roundTo(decimalPlaces: Int): Float {
    return "%.${decimalPlaces}f".format(Locale.ENGLISH, this).toFloat()
}

fun NavController.doIfCurrentDestination(
    @IdRes destination: Int,
    action: NavController.() -> Unit,
) {
    if (this.currentDestination?.id == destination) {
        action()
    }
}

fun Fragment.navigateAction(action: NavDirections) {
    val navController = this.findNavController()
    if (navController.currentDestination?.getAction(action.actionId) == null) {
        return
    } else {
        navController.navigate(action)
    }
}

fun Fragment.navigateActionBundle(action: NavDirections, bundle: Bundle) {
    val navController = this.findNavController()
    if (navController.currentDestination?.getAction(action.actionId) == null) {
        return
    } else {
        navController.navigate(action.actionId, bundle)
    }
}

fun Fragment.safeNavigateFromNavController(directions: NavDirections) {
    val navController = findNavController()
    val destination = navController.currentDestination as FragmentNavigator.Destination
    if (javaClass.name == destination.className) {
        navController.navigate(directions)
    }
}

fun Context.copyToClipboard(content: String) {
    val clipboardManager = ContextCompat.getSystemService(this, ClipboardManager::class.java)!!
    val clip = ClipData.newPlainText("clipboard", content)
    clipboardManager.setPrimaryClip(clip)
}



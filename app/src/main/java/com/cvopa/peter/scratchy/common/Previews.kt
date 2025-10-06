package com.cvopa.peter.scratchy.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

const val PhoneMediumLandScape = "spec:shape=Normal,width=896,height=414,unit=dp,dpi=420"

@Preview(
    apiLevel = 35,
    name = "Pixel 7",
    group = "devices",
    locale = "cs",
    showBackground = true,
    device = Devices.PIXEL_7,
    showSystemUi = true,
)
@Preview(
    apiLevel = 35,
    name = "Pixel 7 dark",
    group = "devices",
    showBackground = true,
    device = Devices.PIXEL_7,
    uiMode = UI_MODE_NIGHT_YES,
    showSystemUi = true,
)
@Preview(name = "PhoneLandScape", device = PhoneMediumLandScape, showBackground = true)
annotation class ScratchyPreviews

@Preview(name = "PhoneLandScape", device = PhoneMediumLandScape, showBackground = true)
annotation class ScratchyPreviewsComponents

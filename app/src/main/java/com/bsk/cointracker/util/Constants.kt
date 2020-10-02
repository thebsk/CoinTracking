package com.bsk.cointracker.util

object Constants {
    // Worker names
    const val WORK_NAME_SYNC_COIN = "workSyncCoin"

    // Keys
    const val KEY_COINS = "coins"
    const val KEY_ID = "id"
    const val KEY_USER_ID = "userId"
    const val KEY_COIN_ID = "keyCoinId"
    const val KEY_COIN_NAME = "keyCoinName"
    const val KEY_COIN_PRICE = "keyCoinPrice"

    // Request Codes
    const val REQUEST_CODE_SIGN_IN = 111

    // Notification constants
    @JvmField
    val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Coin Tracker Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever coin sync work starts"
    const val CHANNEL_ID = "COIN_NOTIFICATION"
    const val NOTIFICATION_ID = 1
}
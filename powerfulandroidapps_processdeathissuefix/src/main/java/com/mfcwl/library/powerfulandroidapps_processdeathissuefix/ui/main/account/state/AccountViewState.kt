package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.state

import android.os.Parcelable
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.models.AccountProperties
import kotlinx.android.parcel.Parcelize

const val ACCOUNT_VIEW_STATE_BUNDLE_KEY = "com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.state.AccountViewState"

@Parcelize
class AccountViewState(
    var accountProperties: AccountProperties? = null
): Parcelable
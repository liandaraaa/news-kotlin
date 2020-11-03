package com.lianda.news.presentation.main

import com.lianda.news.R
import com.lianda.news.base.BaseActivity
import com.lianda.news.data.preference.AppPreference
import com.lianda.news.domain.entities.City
import com.lianda.news.presentation.viewmodel.CityViewModel
import com.lianda.news.utils.common.ResultState
import com.lianda.news.utils.constants.PreferenceKeys
import com.lianda.news.utils.extentions.getDeviceId
import com.lianda.news.utils.extentions.observe
import com.lianda.news.utils.extentions.showToast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val cityViewModel:CityViewModel by viewModel()
    private val preference:AppPreference by inject()

    override val layout: Int = R.layout.activity_main

    override fun onPreparation() {
        val deviceId = getDeviceId(this)
        preference.storeString(PreferenceKeys.KEY_DEVICE_ID, deviceId)
    }

    override fun onIntent() {

    }

    override fun onUi() {

    }

    override fun onAction() {

    }

    override fun onObserver() {
       observe(
           liveData = cityViewModel.fetchCity(),
           action = ::manageStateCity
       )
    }

    private fun manageStateCity(result: ResultState<List<City>>){
        when(result){
            is ResultState.Success ->{
                tvCity.text = result.data.firstOrNull()?.name
            }
            is ResultState.Error ->{
                showToast(message = result.throwable.message?:"Error")
            }
            is ResultState.Failed ->{
                showToast(message = result.message)
            }
            is ResultState.Loading ->{
                showToast(message ="Loading")
            }
        }
    }

}
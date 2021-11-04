package uk.co.conjure.custom_views_demo.terms_and_conditions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel as AndroidViewModel

@HiltViewModel
class TermsAndConditionsViewModel @Inject constructor() : AndroidViewModel() {
    private val acceptedConditions = MutableLiveData(false)

    fun getAcceptedConditions() = acceptedConditions as LiveData<Boolean>

    fun setAcceptedConditions(accepted: Boolean) = acceptedConditions.postValue(accepted)
}
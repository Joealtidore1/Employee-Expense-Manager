package com.impactech.expenser.presentation.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impactech.expenser.domain.ProfileRepository
import com.impactech.expenser.domain.model.Constant.user
import com.impactech.expenser.domain.model.Constant.userId
import com.impactech.expenser.domain.model.Employee
import com.impactech.expenser.presentation.states_and_events.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: ProfileRepository
): ViewModel(){
    val employee = MutableLiveData<Employee?>(null)

    val isSuccess = MutableLiveData<Boolean>()
    val isFailed = MutableLiveData<String?>(null)

    init {
        viewModelScope.launch {
            repository.initialize()
        }

    }

    fun onEvent(event: AuthEvent){
        viewModelScope.launch(Dispatchers.IO) {
            when(event){
                is AuthEvent.Login -> {

                    val res = repository.getEmployee(event.username, event.password)
                    isSuccess.postValue(res != null)
                }
                is AuthEvent.Logout -> {
                    employee.postValue(null)
                    user = null
                    userId = 0
                }
                is AuthEvent.Register -> {
                    val res = repository.verifyEmployee(event.employee.username)
                    if(res != null){
                        if(event.employee.username != "demo") {
                            isFailed.postValue("An employee with this username already exists")
                            isSuccess.postValue(false)
                        }
                        return@launch
                    }
                    repository.addEmployee(event.employee)
                    isSuccess.postValue(true)
                }
            }
        }
    }

}
package com.impactech.expenser.presentation.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impactech.expenser.domain.ExpenseInterface
import com.impactech.expenser.domain.model.Expense
import com.impactech.expenser.presentation.states_and_events.ExpenseEvents
import com.impactech.expenser.presentation.states_and_events.ExpenseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale.filter
import javax.inject.Inject

@HiltViewModel
class ExpenserViewModel @Inject constructor(
    val repository: ExpenseInterface
): ViewModel() {
    var state by mutableStateOf(ExpenseStates())
    val expenses = MutableLiveData<List<Expense>>()
    val merchants = MutableLiveData<List<String>>()
    val tempExpense = mutableListOf<Expense>()

    init {
        viewModelScope.launch {
            merchants.postValue(repository.getAllMerchant())
            filter()
        }
    }

    fun onEvent(events: ExpenseEvents) {
        viewModelScope.launch {
            when(events){
                is ExpenseEvents.Filter ->{
                    filter()
                }
                is ExpenseEvents.AddExpense ->{
                    repository.addExpense(events.expense)
                }
                is ExpenseEvents.UpdateExpense ->{
                    repository.updateExpense(events.expense)
                }
                is ExpenseEvents.ClearFilter ->{
                    state = state.copy(
                        minDate = null,
                        maxDate = null,
                        minTotal =  null,
                        maxTotal = null,
                        merchant = null,
                        new = true,
                        inProgress = true,
                        reimbursed = true
                    )
                    filter()
                }
            }

        }
    }

    private suspend fun filter() {
        state.apply {
            if(new && inProgress && reimbursed){
                expenses.postValue(
                    repository.getExpensesByFilter(
                        maxDate = maxDate,
                        minDate = minDate,
                        maxTotal = maxTotal,
                        minTotal = minTotal,
                        merchant = merchant
                    )
                )
            }else if(new && inProgress){
                expenses.postValue(
                    repository.getExpensesByNotFilter(
                        maxDate = maxDate,
                        minDate = minDate,
                        maxTotal = maxTotal,
                        minTotal = minTotal,
                        merchant = merchant,
                        status = "Reimbursed"
                    )
                )
            }else if(new && reimbursed){
                expenses.postValue(
                    repository.getExpensesByNotFilter(
                        maxDate = maxDate,
                        minDate = minDate,
                        maxTotal = maxTotal,
                        minTotal = minTotal,
                        merchant = merchant,
                        status = "In Progress"
                    )
                )
            }else if(reimbursed && inProgress){
                expenses.postValue(
                    repository.getExpensesByNotFilter(
                        maxDate = maxDate,
                        minDate = minDate,
                        maxTotal = maxTotal,
                        minTotal = minTotal,
                        merchant = merchant,
                        status = "New"
                    )
                )
            }else{
                if(new){
                    expenses.postValue(
                        repository.getExpensesByFilter(
                            maxDate = maxDate,
                            minDate = minDate,
                            maxTotal = maxTotal,
                            minTotal = minTotal,
                            merchant = merchant,
                            status = "New"
                        )
                    )
                }else if(inProgress){
                    expenses.postValue(
                        repository.getExpensesByFilter(
                            maxDate = maxDate,
                            minDate = minDate,
                            maxTotal = maxTotal,
                            minTotal = minTotal,
                            merchant = merchant,
                            status = "In Progress"
                        )
                    )
                }else{
                    expenses.postValue(
                        repository.getExpensesByFilter(
                            maxDate = maxDate,
                            minDate = minDate,
                            maxTotal = maxTotal,
                            minTotal = minTotal,
                            merchant = merchant,
                            status = "Reimbursed"
                        )
                    )
                }
            }
        }
    }
}

package com.impactech.expenser.data.local

/*class temp {
    if(merchant == null && min == null && max == null){
        val res = tempExpense.filter {
            it.date in (from?.rangeTo(to) ?: return@launch)
        }
        expenses.postValue(res)
    }else if(merchant == null && min == null){
        val res = tempExpense.filter {
            it.total <= (max ?: return@launch)
        }
        expenses.postValue(res)
    }else if(merchant == null && max == null) {
        val res = tempExpense.filter {
            it.total >= (min ?: return@launch)
        }
        expenses.postValue(res)
    }else if(min == null && max == null) {
        val res = tempExpense.filter {
            it.merchant == merchant
        }
        expenses.postValue(res)
    }
    else if(min == null) {
        val res = tempExpense.filter {
            it.total <= (max ?: return@launch) && it.merchant == merchant
        }
        expenses.postValue(res)
    }else if(max == null) {
        val res = tempExpense.filter {
            it.total >= min && it.merchant == merchant
        }
        expenses.postValue(res)
    }else{
        val res = tempExpense.filter {
            it.total >= min && it.merchant == merchant && it.total <= max
        }
        expenses.postValue(res)
    }
}*/

/*if(merchant == null && min == null && max == null){
    val res = tempExpense.filter {
        it.date in (from?.rangeTo(to) ?: return@launch)
    }
    expenses.postValue(res)
}else if(merchant == null && min == null){
    val res = tempExpense.filter {
        it.total <= (max ?: return@launch) && it.date <= to
    }
    expenses.postValue(res)
}else if(merchant == null && max == null) {
    val res = tempExpense.filter {
        it.total >= (min ?: return@launch)&& it.date <= to
    }
    expenses.postValue(res)
}else if(min == null && max == null) {
    val res = tempExpense.filter {
        it.merchant == merchant && it.date <= to
    }
    expenses.postValue(res)
}
else if(min == null) {
    val res = tempExpense.filter {
        it.total <= (max ?: return@launch) && it.merchant == merchant && it.date <= to
    }
    expenses.postValue(res)
}else if(max == null) {
    val res = tempExpense.filter {
        it.total >= min && it.merchant == merchant && it.date <= to
    }
    expenses.postValue(res)
}else{
    val res = tempExpense.filter {
        it.total >= min && it.merchant == merchant && it.total <= max && it.date <= to
    }
    expenses.postValue(res)
}*/

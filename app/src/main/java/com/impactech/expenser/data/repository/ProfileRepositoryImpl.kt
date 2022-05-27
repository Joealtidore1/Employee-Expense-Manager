package com.impactech.expenser.data.repository

import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import com.impactech.expenser.data.local.ExpenseDatabase
import com.impactech.expenser.data.local.entity.EmployeeEntity
import com.impactech.expenser.data.local.entity.MerchantEntity
import com.impactech.expenser.data.mapper.toEmployee
import com.impactech.expenser.data.mapper.toEmployeeEntity
import com.impactech.expenser.domain.ProfileRepository
import com.impactech.expenser.domain.model.Constant.user
import com.impactech.expenser.domain.model.Constant.userId
import com.impactech.expenser.domain.model.Employee
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    db: ExpenseDatabase
) : ProfileRepository {
    val dao = db.profileDao
    val dao1 = db.expenseDao
    override suspend fun getEmployee(username: String, password: String): Employee? {

        val res = dao.getEmployee(username, password)?.toEmployee()
        res?.apply {
            userId = id
            user = this
        }
        return res
    }

    override suspend fun verifyEmployee(username: String) = dao.getEmployee(username)?.toEmployee()

    override suspend fun addEmployee(employee: Employee) {
        dao.addEmployee(employee.toEmployeeEntity())
    }

    override suspend fun getEmployeeDetails() = dao.getEmployee().toEmployee()

    override suspend fun initialize() {
        if(verifyEmployee("demo") == null) {
            dao.addEmployee(EmployeeEntity())
            val mer = listOf("Taxi", "Hotel", "Restaurant", "Parking", "Shuttle", "Ride sharing", "Fast food", "Airline", "Office supplies", "Breakfast", "Electronics", "Rental car")
            val merchants = mutableListOf<MerchantEntity>()
            for(m in mer.indices){
                merchants.add(MerchantEntity(m+1, mer[m]))
            }
            dao1.addMerchant(merchants)
        }
    }
}
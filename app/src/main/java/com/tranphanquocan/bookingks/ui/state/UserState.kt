package com.tranphanquocan.bookingks.ui.state

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.tranphanquocan.bookingks.data.model.Companion
import com.tranphanquocan.bookingks.data.model.PaymentCard

object UserState {
    val isLoggedIn = mutableStateOf(false)
    val userName = mutableStateOf("")

    val companions = mutableStateListOf<Companion>()
    val nextCompanionId = mutableIntStateOf(1)

    val paymentCard = mutableStateOf<PaymentCard?>(null)

    fun addCompanion(
        firstName: String,
        lastName: String,
        birthDate: String,
        gender: String,
        isConsentChecked: Boolean
    ) {
        companions.add(
            Companion(
                id = nextCompanionId.intValue,
                firstName = firstName,
                lastName = lastName,
                birthDate = birthDate,
                gender = gender,
                isConsentChecked = isConsentChecked
            )
        )
        nextCompanionId.intValue += 1
    }

    fun updateCompanion(
        id: Int,
        firstName: String,
        lastName: String,
        birthDate: String,
        gender: String,
        isConsentChecked: Boolean
    ) {
        val index = companions.indexOfFirst { it.id == id }
        if (index != -1) {
            companions[index] = companions[index].copy(
                firstName = firstName,
                lastName = lastName,
                birthDate = birthDate,
                gender = gender,
                isConsentChecked = isConsentChecked
            )
        }
    }

    fun deleteCompanion(id: Int) {
        companions.removeAll { it.id == id }
    }

    fun getCompanionById(id: Int): Companion? {
        return companions.find { it.id == id }
    }

    fun savePaymentCard(
        cardHolderName: String,
        cardNumber: String,
        expiryDate: String
    ) {
        paymentCard.value = PaymentCard(
            cardHolderName = cardHolderName,
            cardNumber = cardNumber,
            expiryDate = expiryDate
        )
    }
}
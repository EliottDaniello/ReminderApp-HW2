package com.codemave.reminderapp.ui.addReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemave.reminderapp.Data.Entity.Reminder
import com.codemave.reminderapp.Data.Repository.ReminderRepository
import com.codemave.reminderapp.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
): ViewModel() {
    private val _state = MutableStateFlow(ReminderViewState())

    suspend fun saveReminder(reminder: Reminder): Long {
        return reminderRepository.addReminder(reminder)
    }

    init {
        viewModelScope.launch {
            reminderRepository.reminders().collect { reminders ->
                _state.value = ReminderViewState(reminders)
            }
        }
    }
}

data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)
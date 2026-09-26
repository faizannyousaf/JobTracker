import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.faizanyousaf.jobtracker.models.JobApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JobViewModel : ViewModel() {

    private val repository = JobRepository()

    private val _jobs = MutableStateFlow<List<JobApplication>>(emptyList())
    val jobs: StateFlow<List<JobApplication>> = _jobs

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadJobs() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getJobs()
            if (result.isSuccess) {
                Log.d("listOfJobs","Successs")
                _jobs.value = result.getOrNull() ?: emptyList()
            }
            else{
                Log.e("JOB", "Failed: ${result.exceptionOrNull()?.message}")
            }
            _isLoading.value = false
        }
    }

    fun addJob(company: String, role: String, dateApplied: String, status: String) {
        viewModelScope.launch {
            Log.d("addJob","Add Job")
            val newId = (_jobs.value.maxOfOrNull { it.id } ?: 0) + 1
            val job = JobApplication(newId, company, role, dateApplied, status)
            val result = repository.addJob(job)
            if (result.isSuccess) {
                Log.d("jobAdded","Success")
                loadJobs()
            }
            else{
                Log.e("JOB", "Failed: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun deleteJob(id: Int) {
        viewModelScope.launch {
            val result = repository.deleteJob(id)
            if (result.isSuccess) {
                loadJobs()
            }
        }
    }
}
import dev.faizanyousaf.jobtracker.models.JobApplication

class JobRepository {

    private val api = RetrofitClient.jobApi

    suspend fun getJobs(): Result<List<JobApplication>> {
        return try {
            val jobs = api.getJobs()
            Result.success(jobs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addJob(job: JobApplication): Result<JobApplication> {
        return try {
            val result = api.addJob(job)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteJob(id: Int): Result<Unit> {
        return try {
            api.deleteJob(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
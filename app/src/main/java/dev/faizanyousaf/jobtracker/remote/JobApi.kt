import dev.faizanyousaf.jobtracker.models.JobApplication
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path

interface JobApi {

    @GET("jobs")
    suspend fun getJobs(): List<JobApplication>

    @POST("jobs")
    suspend fun addJob(@Body job: JobApplication): JobApplication

    @DELETE("jobs/{id}")
    suspend fun deleteJob(@Path("id") id: Int)
}
package tampdph33277.fpoly.asm_application;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tampdph33277.fpoly.asm_application.DTO.Student_DTO;

public interface Student_API {
    @PUT("students/{id}")
    Call<Student_DTO> updateStudent(@Path("id") String studentId, @Body Student_DTO student);
    @DELETE("students/{id}")
    Call<Void> deleteStudent(@Path("id") String studentId);
    @GET("students")
    Call<List<Student_DTO>> getStudents();
    @POST("students")
    Call<Student_DTO> addStudent(@Body Student_DTO student);

}

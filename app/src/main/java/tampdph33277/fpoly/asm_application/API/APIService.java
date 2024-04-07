package tampdph33277.fpoly.asm_application.API;


import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tampdph33277.fpoly.asm_application.DTO.Car;

public interface ApiService {
    String DOMAIN = "http://192.168.2.102:3000/";
    @GET("lay")
    Call<Response<ArrayList<Car>>> getCars();
    @Multipart
    @POST("them-co-anh")
    Call<Response<Car>> addCar(
            @Part("tenXe") RequestBody tenXe,
            @Part("gia") RequestBody gia,
            @Part("loaiXe") RequestBody loaiXe,
            @Part MultipartBody.Part image
    );
    @Multipart
    @PUT("cap-nhat/{id}")
    Call<Response<Car>> updateCarById(@Path("id") String id, @Part("tenXe") RequestBody tenXe,
                                      @Part("gia") RequestBody gia,
                                      @Part("loaiXe") RequestBody loaiXe,
                                      @Part MultipartBody.Part image);
    @DELETE("xoa/{id}")
    Call<Response<Car>> deleteCarById(@Path("id") String id);

    @GET("tim-kiem-theo-ten")
    Call<Response<ArrayList<Car>>> searchCar(@Query("key") String key);

    @GET("sap-xep")
    Call<Response<ArrayList<Car>>> sortCarsByPrice(@Query("sort") String sort);
}

package tampdph33277.fpoly.asm_application.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {

    private ApiService apiService;


    public HttpRequest() {
        apiService = new Retrofit.Builder().
                baseUrl(ApiService.DOMAIN).
                addConverterFactory(GsonConverterFactory.create()).
                build().
                create(ApiService.class);
    }
    public  ApiService callApi (){
        return apiService;
    }

}

package tampdph33277.fpoly.asm_application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tampdph33277.fpoly.asm_application.API.ApiService;
import tampdph33277.fpoly.asm_application.API.Response;
import tampdph33277.fpoly.asm_application.Adapter.CarAdapter;
import tampdph33277.fpoly.asm_application.DTO.Car;

public class AddCar extends AppCompatActivity {
    EditText edtTenXe,edtGia,edtLoai;
    Button btnThem,btnCancel;
    ApiService apiService;
    CarAdapter carAdapter;
    ImageView ivChonAnh;
    private  File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        carAdapter = new CarAdapter(AddCar.this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        ivChonAnh = findViewById(R.id.ivChonAnh);
        edtTenXe = findViewById(R.id.edtTenXe);
        edtGia = findViewById(R.id.edtGia);
        edtLoai = findViewById(R.id.edtLoai);
        btnThem = findViewById(R.id.btnThem);
        btnCancel = findViewById(R.id.btnCancel);
        edtLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] loaiXe = {"Ô tô","Xe máy"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddCar.this);
                builder.setTitle("Chọn mức độ khó của công việc");
                builder.setItems(loaiXe, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtLoai.setText(loaiXe[which]);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        ivChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
btnCancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});
        btnThem.setOnClickListener(v -> addCar());
    }
    private File createFileFromUri(Uri path, String name) {
        File _file = new File(AddCar.this.getCacheDir(), name + ".png");
        try {
            InputStream in = AddCar.this.getContentResolver().openInputStream(path);
            if (in == null) {
                Log.e("createFileFromUri", "Input stream is null");
                return null;
            }
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("createFileFromUri", "File created successfully: " + _file.getAbsolutePath());
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("createFileFromUri", "File not found: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("createFileFromUri", "IOException: " + e.getMessage());
        }
        return null;
    }
    private void addCar() {
        String tenXe = edtTenXe.getText().toString().trim();
        String giaStr = edtGia.getText().toString().trim();
        String loaiXe = edtLoai.getText().toString().trim();

        if (tenXe.isEmpty() || giaStr.isEmpty() || loaiXe.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!loaiXe.equals("Ô tô") && !loaiXe.equals("Xe máy")){
            Toast.makeText(this, "Loại Xe phải là Ô tô hoặc Xe máy", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("anh", file.getName(), requestFile);
        RequestBody tenXeRequestBody = RequestBody.create(MediaType.parse("text/plain"), tenXe);
        RequestBody giaRequestBody = RequestBody.create(MediaType.parse("text/plain"), giaStr);
        RequestBody loaiXeRequestBody = RequestBody.create(MediaType.parse("text/plain"), loaiXe);

        Call<Response<Car>> call = apiService.addCar(tenXeRequestBody, giaRequestBody, loaiXeRequestBody, body);
        call.enqueue(new Callback<Response<Car>>() {
            @Override
            public void onResponse(Call<Response<Car>> call, retrofit2.Response<Response<Car>> response) {
                if (response.body().getStatus() == 200) {
                    Response<Car> responseData = response.body();
                    if (responseData != null) {
                        Car addedCar = responseData.getData();
                        if (addedCar != null) {
                            carAdapter.carList.add(addedCar);
                            Toast.makeText(AddCar.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddCar.this, Danh_Sach.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    Log.e("TAG", "Server error: " + response.code());
                    Toast.makeText(AddCar.this, "Thêm xe không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<Car>> call, Throwable t) {
                Toast.makeText(AddCar.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void chooseImage() {
        Log.d("123123", "chooseAvatar: " +123123);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getImage.launch(intent);
    }

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        Uri uri = data.getData();
                        Log.d("RegisterActivity", uri.toString());
                        Log.d("123123", "onActivityResult: " + data);
                        file = createFileFromUri(uri, "anh");
                        Glide.with(AddCar.this)
                                .load(uri)
                                .thumbnail(Glide.with(AddCar.this).load(R.drawable.home))
                                .centerCrop()
                                .circleCrop()
                                .skipMemoryCache(false)
                                .into(ivChonAnh);
                    }
                }

            });




}
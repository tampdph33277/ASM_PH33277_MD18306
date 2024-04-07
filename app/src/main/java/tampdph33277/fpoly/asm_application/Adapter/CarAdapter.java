package tampdph33277.fpoly.asm_application.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import tampdph33277.fpoly.asm_application.DTO.Car;
import tampdph33277.fpoly.asm_application.R;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolderOfCars> {
    public interface OnItemClickListener{
        void onItemClick(String id);
        void updateItem(String id, String tenXe, int gia, String anh, String loaiXe);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.click = onItemClickListener;
    }
    private OnItemClickListener click;
    public List<Car> carList = new ArrayList<>();
    public void setData(List<Car> carList){
        this.carList = carList;
    }
    private Context context;
    @NonNull
    @Override
    public ViewHolderOfCars onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_sach,parent,false);
        return new ViewHolderOfCars(view);
    }
    public CarAdapter(Context context) {
        this.context = context;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderOfCars holder, int position) {
        Car car = carList.get(position);
        if(car == null){
            Log.d("sssssssssssssssss","Danh sách trống"+car.toString());
            return;
        }
         holder.tvTenXe.setText("Tên Xe:  "+car.getTenXe());
         holder.tvGiaXe.setText("Giá Xe:  "+String.valueOf(car.getGia()));
        holder.tvLoaiXe.setText("Loại Xe: "+car.getLoaiXe());
        String imageUrl = car.getFormattedImageUrl();
        if (imageUrl != null) {
            String newUrl = imageUrl.replace("localhost", "192.168.2.102");
            Log.d("sssssssssssssssssssssssssss",newUrl);
            Glide.with(context)
                    .load(newUrl)
                    .thumbnail(Glide.with(context).load(R.drawable.home))
                    .into(holder.imgAvatar);
        } else {
            Log.e("Image URL Error", "Image URL is null");
        }
        holder.btnXoa.setOnClickListener(v -> {
            String id = carList.get(holder.getAdapterPosition()).getId();
            click.onItemClick(id);
        });
        holder.btnSua.setOnClickListener(v -> {
            String Id = carList.get(holder.getAdapterPosition()).getId();
            String tenXe = carList.get(holder.getAdapterPosition()).getTenXe();
            int gia = carList.get(holder.getAdapterPosition()).getGia();
            String anh = imageUrl;
            Log.d("ssssssssssssssss",anh);
            String loaiXe = carList.get(holder.getAdapterPosition()).getLoaiXe();
            click.updateItem(Id,tenXe,gia,anh,loaiXe);
        });
    }



    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class ViewHolderOfCars extends RecyclerView.ViewHolder {
        TextView tvTenXe,tvGiaXe,tvLoaiXe;
        ImageView imgAvatar,btnXoa,btnSua;
        public ViewHolderOfCars(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.ivAnhXe);
            tvTenXe = itemView.findViewById(R.id.tvTenXe);
            tvGiaXe =  itemView.findViewById(R.id.tvGiaXe);
            tvLoaiXe =  itemView.findViewById(R.id.tvLoaiXe);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}

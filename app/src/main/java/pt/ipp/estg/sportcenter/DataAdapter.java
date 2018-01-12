package pt.ipp.estg.sportcenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pmms8 on 1/7/2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context mContext;
    private List<DataModel> mImages;

    public DataAdapter(Context mContext, List<DataModel> mImages) {
        this.mContext = mContext;
        this.mImages = mImages;
    }

    public Context getmContext() {
        return mContext;
    }

    public void addData(DataModel data){
        this.mImages.add(data);
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.image_separator, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataModel currFlower = this.mImages.get(position);
//check data present in database
        if (currFlower.isFromDataBase()) {
            holder.imageView.setImageBitmap(currFlower.getPicture());
        } else {
            //holder.title.setText(currFlower.getName());
//set imageview using picasso
            Picasso.with(holder.itemView.getContext()).load(currFlower.getUrl().getMedium()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return this.mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageV);
        }
    }
}

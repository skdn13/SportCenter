package catalogos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import java.util.List;

import pt.ipp.estg.sportcenter.R;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context mContext;
    private List<Image> mImages;

    public ImageAdapter(Context mContext, List<Image> mImages) {
        this.mContext = mContext;
        this.mImages = mImages;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.image_separator, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image currFlower = this.mImages.get(position);
        if (currFlower.isFromDataBase()) {
            holder.imageView.setImageBitmap(currFlower.getPicture());
        } else {
            Picasso.with(holder.itemView.getContext()).load(currFlower.getUrl().getMedium()).into(holder.imageView);
        }
    }

    public void reset() {
        mImages.clear();
        notifyDataSetChanged();
    }

    public void addData(Image image) {
        mImages.add(image);
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageV);
        }
    }
}

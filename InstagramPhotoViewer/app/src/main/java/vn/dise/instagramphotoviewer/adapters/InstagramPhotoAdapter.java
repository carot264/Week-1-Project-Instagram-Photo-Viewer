package vn.dise.instagramphotoviewer.adapters;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.Date;
import java.util.List;
import vn.dise.instagramphotoviewer.utils.CircleTransform;
import vn.dise.instagramphotoviewer.R;
import vn.dise.instagramphotoviewer.models.InstagramPhoto;


public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotoAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);

        ExpandableTextView tvCaption = (ExpandableTextView) convertView.findViewById(R.id.tvCaption);
        TextView tvUserName = (TextView) convertView.findViewById(  R.id.tvUser);
        TextView tvTimeStamp = (TextView) convertView.findViewById(  R.id.tvTimestamp);
        ImageView iProfilePhoto = (ImageView) convertView.findViewById(R.id.iProfile);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.iPhoto);


        if(photo.timeStamp != "") {
            Date createdAtDate = new Date(Long.parseLong(photo.timeStamp) * 1000);
            Date now = new Date();
            tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(createdAtDate.getTime(), now.getTime(), DateUtils.SECOND_IN_MILLIS));
        }

        tvCaption.setText(photo.caption);
        tvUserName.setText(photo.userName);
        //Clear  out the imageView
        ivPhoto.setImageResource(0);
        iProfilePhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        Picasso.with(getContext()).load(photo.imageProfileUrl).transform(new CircleTransform()).into(iProfilePhoto);

        return convertView;
    }

}

package cs246.ilearntracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ryan Hildreth on 3/4/2015.
 */
public class classListAdapter extends ArrayAdapter {

    private List<Class> items;
    private int layoutResourceId;
    private Context context;

    private static class classHolder{
        public int buttonColorTag;
        public String classButtonString;
    }

    public classListAdapter(Context context, int layoutResourceId, List<Class> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ClassHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        Class theClass = items.get(position);


        holder = new ClassHolder();
        holder.aClass = items.get(position);
        holder.classButton = (Button)row.findViewById(R.id.button_class);
        holder.classButton.setTag(holder.aClass.getClassName());

        holder.color = (TextView)row.findViewById(R.id.button_color_tag);

        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    private void setupItem(ClassHolder holder) {
        holder.classButton.setText(holder.aClass.getClassName());
        holder.color.setBackgroundColor(holder.aClass.getClassColor());
    }

    public static class ClassHolder {
        Class aClass;
        TextView classButton;
        TextView color;
    }
}

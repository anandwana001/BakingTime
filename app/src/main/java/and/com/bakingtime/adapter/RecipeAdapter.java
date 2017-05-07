package and.com.bakingtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import and.com.bakingtime.R;
import and.com.bakingtime.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 07-05-2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.recipeName.setText(recipeList.get(position).getName());
        String path = recipeList.get(position).getImage();
        if(path=="")
            Picasso.with(context).load(R.mipmap.ic_launcher).into(holder.thumbnail);
        else{
            Picasso.with(context).load(path).into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

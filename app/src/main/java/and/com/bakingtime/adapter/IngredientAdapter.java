package and.com.bakingtime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import and.com.bakingtime.R;
import and.com.bakingtime.model.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 14-05-2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.ingredientName.setText(ingredient.getIngredient());
        holder.ingredientMeasure.setText(ingredient.getMeasure());
        holder.ingredientQuantity.setText(""+ingredient.getQuantity());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name)
        TextView ingredientName;
        @BindView(R.id.ingredient_quantity)
        TextView ingredientQuantity;
        @BindView(R.id.ingredient_measure)
        TextView ingredientMeasure;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

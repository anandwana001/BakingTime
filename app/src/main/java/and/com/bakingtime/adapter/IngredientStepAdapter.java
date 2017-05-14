package and.com.bakingtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import and.com.bakingtime.R;
import and.com.bakingtime.model.Ingredient;
import and.com.bakingtime.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 13-05-2017.
 */

public class IngredientStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_INGREDIENT_LABLE = 0;
    private final int VIEW_TYPE_INGREDIENT = 1;
    private final int VIEW_TYPE_STEP_LABLE = 2;
    private final int VIEW_TYPE_STEP = 3;

    private List<Step> stepList;
    private Context context;
    private List<Ingredient> ingredientList;

    private Boolean mTwoPane;

    public IngredientStepAdapter(List<Step> stepList, List<Ingredient> ingredientList,  Boolean mTwoPane, Context context) {
        this.stepList = stepList;
        this.ingredientList = ingredientList;
        this.mTwoPane = mTwoPane;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_INGREDIENT_LABLE;
        }
        if (position == 1 ) {
            return VIEW_TYPE_INGREDIENT;
        }
        if (position == 2) {
            return VIEW_TYPE_STEP_LABLE;
        } else {
            return VIEW_TYPE_STEP;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_INGREDIENT_LABLE) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_lable, parent, false);
            return new ViewHolderIngredientLable(itemView);

        } else if (viewType == VIEW_TYPE_INGREDIENT) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_content, parent, false);
            return new ViewHolderIngredient(itemView);

        } else if (viewType == VIEW_TYPE_STEP_LABLE) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_lable, parent, false);
            return new ViewHolderStepLable(itemView);

        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_content, parent, false);
            return new ViewHolderStep(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_INGREDIENT:
                ViewHolderIngredient viewHolderIngredient = (ViewHolderIngredient) holder;
                viewHolderIngredient.bindViews();
                break;
            case VIEW_TYPE_STEP:
                ViewHolderStep viewHolderStep = (ViewHolderStep) holder;
                viewHolderStep.bindViews(position - 3);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3 + stepList.size();
    }

    public class ViewHolderStepLable extends RecyclerView.ViewHolder {

        public ViewHolderStepLable(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class ViewHolderIngredientLable extends RecyclerView.ViewHolder {

        public ViewHolderIngredientLable(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderIngredient extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_size)
        TextView ingredient_size;

        public ViewHolderIngredient(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews() {
            ingredient_size.setText("Total Ingredients = "+ingredientList.size());
        }
    }

    public class ViewHolderStep extends RecyclerView.ViewHolder {

        @BindView(R.id.short_des_step)
        TextView shortDesStep;

        public ViewHolderStep(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(int position) {
            shortDesStep.setText(stepList.get(position).getShortDescription());
        }
    }

}

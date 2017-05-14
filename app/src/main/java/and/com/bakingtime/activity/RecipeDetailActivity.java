package and.com.bakingtime.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import and.com.bakingtime.R;
import and.com.bakingtime.fragment.IngredientDetailFragment;
import and.com.bakingtime.fragment.StepDetailFragment;
import and.com.bakingtime.model.Ingredient;
import and.com.bakingtime.model.Step;
import butterknife.ButterKnife;

/**
 * Created by dell on 13-05-2017.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    private List<Step> stepList;
    private List<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {

            ingredientList = getIntent().getParcelableArrayListExtra("ingredient");
            stepList = getIntent().getParcelableArrayListExtra("step");

            if(ingredientList!=null){

                IngredientDetailFragment ingredientDetailFragment = new IngredientDetailFragment();
                ingredientDetailFragment.setIngList(ingredientList);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.item_detail_container, ingredientDetailFragment)
                        .commit();
            } else{

                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setPosition(getIntent().getIntExtra("position",0));
                stepDetailFragment.setStepList(stepList);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.item_detail_container, stepDetailFragment)
                        .commit();
            }
        }
    }
}

package and.com.bakingtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import and.com.bakingtime.R;
import and.com.bakingtime.adapter.IngredientStepAdapter;
import and.com.bakingtime.fragment.IngredientDetailFragment;
import and.com.bakingtime.fragment.StepDetailFragment;
import and.com.bakingtime.model.Ingredient;
import and.com.bakingtime.model.Recipe;
import and.com.bakingtime.model.Step;
import and.com.bakingtime.util.RecyclerTouchListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 13-05-2017.
 */

public class RecipeListActivity extends AppCompatActivity {

    @BindView(R.id.item_list)
    RecyclerView recipe_step_ing_recyclerView;

    private IngredientStepAdapter ingredientStepAdapter;

    private boolean mTwoPane;

    private List<Ingredient> ingredientList;
    private List<Step> stepList;

    private String LIST_STATE_KEY = "list_state";
    private Parcelable mListState;
    private LinearLayoutManager mLayoutManager;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            recipe = bundle.getParcelable("object");
        }

        ingredientList = recipe.getIngredients();
        stepList = recipe.getSteps();

        Log.v(RecipeListActivity.class.getSimpleName(), "ingredients List  = " + ingredientList.toString());
        Log.v(RecipeListActivity.class.getSimpleName(), "steps List  = " + stepList.toString());

        mLayoutManager = new LinearLayoutManager(this);
        recipe_step_ing_recyclerView.setLayoutManager(mLayoutManager);
        ingredientStepAdapter = new IngredientStepAdapter(stepList,ingredientList,mTwoPane, this);
        recipe_step_ing_recyclerView.setAdapter(ingredientStepAdapter);
        mLayoutManager.onRestoreInstanceState(mListState);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
            // set default layout if nothing is selected by user
        }

        recipe_step_ing_recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recipe_step_ing_recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle bundle1 = new Bundle();

                if(position == 1){

                    bundle1.putParcelableArrayList("ingredient", (ArrayList<? extends Parcelable>) ingredientList);

                    if(mTwoPane){

                        IngredientDetailFragment newFragment = new IngredientDetailFragment();
                        newFragment.setIngList(ingredientList);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, newFragment)
                                .commit();
                    }else{
                        Intent intent = new Intent(getBaseContext(), RecipeDetailActivity.class);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                } else if(position > 2) {

                    bundle1.putParcelableArrayList("step", (ArrayList<? extends Parcelable>) stepList);
                    bundle1.putInt("position", position -3);

                    if(mTwoPane){

                        StepDetailFragment stepDetailFragment = new StepDetailFragment();
                        stepDetailFragment.setPosition(position-3);
                        stepDetailFragment.setStepList(stepList);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, stepDetailFragment)
                                .commit();
                    } else{
                        Intent intent = new Intent(getBaseContext(), RecipeDetailActivity.class);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onLongClick(View view, int position) {}
        }));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setTitle(recipe.getName());
        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }
}

package and.com.bakingtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import and.com.bakingtime.R;
import and.com.bakingtime.adapter.RecipeAdapter;
import and.com.bakingtime.model.Recipe;
import and.com.bakingtime.networking.BakingClient;
import and.com.bakingtime.networking.BakingInterface;
import and.com.bakingtime.util.RecyclerTouchListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_internet)
    TextView noInternet;

    private boolean mTwoPane;

    private List<Recipe> recipeArrayList = new ArrayList<>();
    private RecipeAdapter mRecipeAdapter;
    private LinearLayoutManager mLayoutManager;

    private String LIST_STATE_KEY = "list_state";
    private Parcelable mListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTwoPane = getResources().getBoolean(R.bool.is_tablet);

        if (mTwoPane) {
            mLayoutManager = new GridLayoutManager(this, 3);
        } else {
            mLayoutManager = new GridLayoutManager(this, 1);
        }

        recyclerView.setLayoutManager(mLayoutManager);
        mRecipeAdapter = new RecipeAdapter(recipeArrayList, MainActivity.this);
        recyclerView.setAdapter(mRecipeAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Recipe recipe = recipeArrayList.get(position);
                Bundle b = new Bundle();
                b.putParcelable("object", recipe);

                Intent recipeIntent = new Intent(MainActivity.this, RecipeListActivity.class);
                recipeIntent.putExtras(b);
                startActivity(recipeIntent);

            }
            @Override
            public void onLongClick(View view, int position) {}
        }));

        getData();
    }

    private void getData() {
        BakingInterface bakingInterface = BakingClient.getClient().create(BakingInterface.class);
        Call<List<Recipe>> call = bakingInterface.getRecipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipeList = response.body();
                recipeArrayList = recipeList;
                mRecipeAdapter = new RecipeAdapter(recipeList, MainActivity.this);
                recyclerView.setAdapter(mRecipeAdapter);
                mLayoutManager.onRestoreInstanceState(mListState);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                if (t instanceof IOException) {
                    noInternet.setVisibility(View.VISIBLE);
                }
            }
        });
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
        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }
}

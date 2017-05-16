package and.com.bakingtime.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import and.com.bakingtime.R;
import and.com.bakingtime.adapter.IngredientAdapter;
import and.com.bakingtime.model.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 13-05-2017.
 */

public class IngredientDetailFragment extends Fragment {

    @BindView(R.id.ingredient_recyclerView)
    RecyclerView ingredientRecyclerView;

    private List<Ingredient> ingredientList;

    private IngredientAdapter ingredientAdapter;

    public static final String LIST_IN = "ingredient";

    public IngredientDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState!= null){
            ingredientList = savedInstanceState.getParcelableArrayList(LIST_IN);
        }

        View rootView = inflater.inflate(R.layout.ingredient_detail, container, false);
        ButterKnife.bind(this, rootView);

        if(ingredientList!=null){
            ingredientRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

            ingredientAdapter = new IngredientAdapter(ingredientList);
            ingredientRecyclerView.setAdapter(ingredientAdapter);
        }
        else{
            Toast.makeText(getContext(),"no Ingredients present",Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    public void setIngList(List<Ingredient> ingredientList){
        this.ingredientList = ingredientList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_IN, (ArrayList<? extends Parcelable>) ingredientList);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.ingre);
    }
}

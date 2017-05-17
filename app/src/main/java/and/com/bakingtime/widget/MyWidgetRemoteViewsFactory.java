package and.com.bakingtime.widget;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import and.com.bakingtime.R;
import and.com.bakingtime.model.Recipe;
import and.com.bakingtime.networking.BakingClient;
import and.com.bakingtime.networking.BakingInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static and.com.bakingtime.activity.MainActivity.recipeArrayList;

/**
 * Created by dell on 16-05-2017.
 */

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    public MyWidgetRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        getData();
    }

    public void getData() {
        BakingInterface bakingInterface = BakingClient.getClient().create(BakingInterface.class);
        Call<List<Recipe>> call = bakingInterface.getRecipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipeList = response.body();
                recipeArrayList = recipeList;
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipeArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {


        Log.v(mContext.getClass().getSimpleName(), "position = "+position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);
        rv.setTextViewText(R.id.recipe_name, recipeArrayList.get(position).getName());
        rv.removeAllViews(R.id.ingerdient_list);

        for (int i=0;i<recipeArrayList.get(position).getIngredients().size();i++){
            RemoteViews  ing= new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_item);
            ing.setTextViewText(R.id.ingredient,recipeArrayList.get(position).getIngredients().get(i).getIngredient());
            ing.setTextViewText(R.id.measure,recipeArrayList.get(position).getIngredients().get(i).getMeasure());
            ing.setTextViewText(R.id.quantity,recipeArrayList.get(position).getIngredients().get(i).getQuantity()+"");

            rv.addView(R.id.ingerdient_list,ing);
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
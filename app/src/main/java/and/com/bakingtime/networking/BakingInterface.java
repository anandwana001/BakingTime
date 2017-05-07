package and.com.bakingtime.networking;

import java.util.List;

import and.com.bakingtime.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dell on 07-05-2017.
 */

public interface BakingInterface {

    @GET("5907926b_baking/baking.json")
    Call<List<Recipe>> getRecipe();
}

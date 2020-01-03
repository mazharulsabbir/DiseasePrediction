package school.of.thought.utils;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import school.of.thought.model.Dengue;

public interface DiseaseAPI {
    @POST("prediction")
    Observable<Dengue> submitAnswers(@Body Dengue dengue);

    @POST("prediction")
    @FormUrlEncoded
    Observable<Dengue> submitAnswers(@FieldMap Map<String, String> response);
}

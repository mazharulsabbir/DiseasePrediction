package school.of.thought.utils;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import school.of.thought.model.Response;

public interface DiseaseAPI {
    @POST("prediction")
    Observable<Response> answer(@Body Response response);
}

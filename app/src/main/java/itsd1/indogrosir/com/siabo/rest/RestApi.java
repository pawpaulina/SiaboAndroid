package itsd1.indogrosir.com.siabo.rest;

import itsd1.indogrosir.com.siabo.models.*;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Paulina on 1/18/2017.
 */
public interface RestApi
{
    @POST("api/authentikasi")
    Call<Login> getLogin(@Body Login login);

    @GET("user/userdetails")
    Call<User> getUserDetail(@Query("token") String token);

    @GET("api/kalender/{id_user}")
    Call<Plan> getKalender(@Path("id_user") int id_user, @Query("token") String token);

    @POST("api/user/updateloc")
    Call<Loc> Updatelocation(@Body Loc loc, @Query("token") String token);

    @GET("api/kalender/plan/detail/{id_plan}")
    Call<PlanDet> getDetailPlan(@Path("id_plan") int id_plan, @Query("token") String token);

    @GET("api/todo/detail/{id_user}/{id_plan}")
    Call<ToDo> getTugas(@Path("id_user") int id_user, @Path("id_plan") int id_plan, @Query("token") String token);

    @GET("api/todo/detail/{id_user}/{id_plan}")
    Call<TugasPokok> getTugasPokok(@Path("id_user") int id_user, @Path("id_plan") int id_plan, @Query("token") String token);

    @GET("api/todo/detail/{id_user}/{id_plan}/{id_todo}")
    Call<ToDo> getDetailTugas(@Path("id_user") int id_user, @Path("id_plan") int id_plan, @Path("id_todo") int id_todo, @Query("token") String token);

    @POST("api/eks/checkin/{id_user}")
    Call<EksObject> CheckIn(@Body EksObject eks, @Path("id_user") int id_user, @Query("token") String token);

    @GET("api/eks/cekcheckin/{id_plan}")
    Call<EksObject> getCekCheckin(@Path("id_plan") int id_plan, @Query("token") String token);

    @POST("api/eks/submitTugas/{idtodo}")
    Call<Bukti> submitTugas(@Body Bukti bukti, @Path("idtodo") int idtodo, @Query("token") String token);

    @POST("api/eks/submitTP/{idtodo}")
    Call<BuktiTP> submitTP(@Body BuktiTP buktiTP, @Path("idtodo") int idtodo, @Query("token") String token);

    @GET("api/eks/ceksubmit/{idtodo}")
    Call<ToDo.ToDoDetail> cekSubmit(@Path("idtodo") int idtodo, @Query("token") String token);

    @GET("api/eks/ceksubmitTP/{idtodo}")
    Call<TugasPokok.TPDetail> cekSubmitTP(@Path("idtodo") int idtodo, @Query("token") String token);
}

package itsd1.indogrosir.com.siabo.models;

/**
 * Created by Paulina on 6/4/2017.
 */
public class SiDao {
    private int id_user;
    private int id_todo;
    private int id_plan;
    private String token;

    public SiDao(int id_user, int id_todo, int id_plan, String token)
    {
        this.setId_user(id_user);
        this.setId_plan(id_plan);
        this.setId_todo(id_todo);
        this.setToken(token);
    }


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_todo() {
        return id_todo;
    }

    public void setId_todo(int id_todo) {
        this.id_todo = id_todo;
    }

    public int getId_plan() {
        return id_plan;
    }

    public void setId_plan(int id_plan) {
        this.id_plan = id_plan;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Paulina on 1/18/2017.
 */
public class Branch
{
    @SerializedName("id_branch")
    private int id_branch;
    @SerializedName("name")
    private String branch_name;

    public Branch(int id_branch, String branch_name)
    {
        this.id_branch = id_branch;
        this.branch_name = branch_name;
    }

    public int getId_branch()
    {
        return id_branch;
    }

    public void setId_branch(int id_branch)
    {
        this.id_branch = id_branch;
    }

    public String getBranch_name()
    {
        return branch_name;
    }

    public void setBranch_name(String branch_name)
    {
        this.branch_name = branch_name;
    }
}

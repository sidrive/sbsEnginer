package id.geekgarden.esi.data.model.projects;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseProjects{

	@SerializedName("projects")
	private List<ProjectsItem> projects;

	public void setProjects(List<ProjectsItem> projects){
		this.projects = projects;
	}

	public List<ProjectsItem> getProjects(){
		return projects;
	}

	@Override
 	public String toString(){
		return 
			"ResponseProjects{" + 
			"projects = '" + projects + '\'' + 
			"}";
		}
}
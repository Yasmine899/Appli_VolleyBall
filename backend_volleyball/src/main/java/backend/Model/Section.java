package backend.Model;

public class Section {

    private int id;
    private String description;

    public Section(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.description = name;
    }

    @Override
    public String toString(){
        return "Section{" + "id=" + id + ", description=" + description + '}';
    }
}

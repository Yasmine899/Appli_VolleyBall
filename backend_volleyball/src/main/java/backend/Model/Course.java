package backend.Model;

public class Course {

    private String  course_description;
    private int  idChapitre;
    private String title;
    private String Reference_id;

    public Course (){}

    public Course (int course_id,String title,String course_description,int idChapitre,String Reference_id){
        this.Reference_id=Reference_id;
        this.course_description=course_description;
        this.idChapitre=idChapitre;
        this.title=title;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }

    public int getIdChapitre() {
        return idChapitre;
    }

    public void setIdChapitre(int idChapitre) {
        this.idChapitre = idChapitre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReference_id() {
        return Reference_id;
    }

    public void setReference_id(String reference_id) {
        Reference_id = reference_id;
    }
}

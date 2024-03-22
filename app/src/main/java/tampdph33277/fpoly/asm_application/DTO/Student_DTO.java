package tampdph33277.fpoly.asm_application.DTO;

public class Student_DTO {

    private String _id;
    private String name;
    private String studentId;
    private double averageScore;
    private String avatar;

    public Student_DTO() {
    }

    public Student_DTO(String _id, String name, String studentId, double averageScore, String avatar) {
        this._id = _id;
        this.name = name;
        this.studentId = studentId;
        this.averageScore = averageScore;
        this.avatar = avatar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

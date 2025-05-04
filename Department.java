package amy_lihy_project2;

public class Department {
    private String name;
    private int studentsNum;
    private Lecturer[] lecturers;
    private int lecturersNum;

    public Department(String name, int studentsNum) {
        this.name=name;
        this.studentsNum=studentsNum;
    }

    public String getName() {
        return this.name;
    }

    public int getLecturersNum() {
        return lecturersNum;
    }

    public double averageSalary() {
        double totalSalary = 0;

        for (int i = 0; i < lecturersNum; i++) {
                totalSalary += lecturers[i].getSalary();

        }
        return (double) totalSalary/lecturersNum;

    }

}

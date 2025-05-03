package amy_lihy_project2;


import static amy_lihy_project2.CollegeManager.expandBoardArray;

public class Lecturer {
    private String name;
    private int id;
    private DegreeType degreeType;
    private String degreeName;
    private double salary;
    private Department department;
    private Board[]  boards;
    private int boardsJoined;


    public Lecturer(String name, int id, DegreeType degreeType, String degreeName, double salary, Department department) {
        this.name = name;
        this.id = id;
        this.degreeType = degreeType;
        this.degreeName = degreeName;
        this.salary = salary;
        this.department = department;
    }
    public DegreeType getDegreeType() {
        return degreeType;
    }

    public String getName(){
        return name;
    }

    public void joinBoard(Board board){
        if (boards.length==boardsJoined){
            this.boards=expandBoardArray();
        }

        this.boards[boardsJoined++]=board;

    }

    public void leaveBoard(Board board){

        for (int i = 0; i < boardsJoined; i++) {
            if (boards[i].equals(board)) {
                // Shift all boards 'to the left' after the found index of the board we are leaving !
                for (int j = i; j < boardsJoined - 1; j++) {
                    boards[j] = boards[j + 1];
                }
                boards[--boardsJoined] = null; // Decrease count, and make the last null
                break; // we removed it, updated counter and shifted the rest, we can leave now
            }
        }

    }


    public double getSalary() {
        return salary;
    }

    public void setDepartment(Department dept) {
        this.department=dept;
    }

    public Department getDepartment() {
        return department;
    }
}

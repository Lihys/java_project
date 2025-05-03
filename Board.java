package amy_lihy_project2;

import static amy_lihy_project2.CollegeManager.expandLecturerArray;

public class Board {
    private String name;
    private Lecturer[] lecturersInBoard;
    private int lecturersNum;
    private Lecturer headOfBoard;

    public Board(String name, Lecturer headOfBoard) {
        this.name = name;
        this.headOfBoard = headOfBoard;
    }

    public String getName(){
        return name;
    }

    public void assignLecturer(Lecturer newLecturer){
        if (lecturersInBoard.length==lecturersNum){
            lecturersInBoard = expandLecturerArray();
        }
        lecturersInBoard[lecturersNum++] = newLecturer;
    }

    public void removeLecturerFromBoard(Lecturer lecturerToRemove){
        for (int i = 0; i < lecturersNum; i++) {
            if (lecturersInBoard[i].equals(lecturerToRemove)) {
                // Shift all lecturers 'to the left' after the found index we are removing !
                for (int j = i; j < lecturersNum - 1; j++) {
                    lecturersInBoard[j] = lecturersInBoard[j + 1];
                }
                lecturersInBoard[--lecturersNum] = null; // Decrease count, and make the last null
                break; // we removed it, updated counter and shifted the rest, we can leave now :)
            }
        }
    }

    public void setHeadOfBoard(Lecturer headOfBoard) {
        this.headOfBoard = headOfBoard;
    }
}

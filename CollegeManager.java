package amy_lihy_project2;

import java.util.Arrays;
import java.util.Scanner;

public class CollegeManager {
    public static final int DEFAULT_ARRAY_LENGTH = 1;

    private static Lecturer[] lecturers = new Lecturer[DEFAULT_ARRAY_LENGTH];
    private static Board[] boards = new Board[DEFAULT_ARRAY_LENGTH];
    private static Department[] departments = new Department[DEFAULT_ARRAY_LENGTH];

    private static int lecturerCounter;
    private static int boardCounter;
    private static int departmentCounter;

    private static final Scanner scanner = new Scanner(System.in);

    private final String[] MENU_OPTIONS = {
            "Exit",
            "Add lecturer",
            "Add a board",
            "Assign a lecturer to a board",
            "Update Head Of Board",
            "Remove lecturer from board",
            "Add a school department",
            "Display average salary of all lecturers",
            "Display average salary of lecturers in department",
            "Display lecturers' information",
            "Display boards' information",
            //"Assign lecturer to a department"
    };


    void run() {
        System.out.print("Please choose a college name: ");
        String collegeName = scanner.nextLine();

        int userChoice;
        do {
            userChoice = showMenu();

            switch (userChoice) {
                case 0 -> System.out.println("Exiting...");
                case 1 -> addLecturer();
                case 2 -> addBoard();
                case 3 -> assignLecturerToBoard();
                case 4 -> updateHeadOfBoard();
                case 5 -> removeLecturerFromBoard();
                case 6 -> addDepartment();
                case 7 -> displayAvgSalary();
                case 8 -> displayAvgSalaryInDept();
                case 9 -> displayLecturersInfo();
                case 10 -> displayBoardsInfo();
                //case 11 -> assignLecturerToDept();
                default -> System.out.println("Unexpected value.");
            }
        } while (userChoice != 0);

        scanner.close();
    }

    private int showMenu() {
        System.out.println("\n<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>");
        System.out.println("|                         MENU");
        System.out.println("<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>");
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            System.out.println("| " + i + ". " + MENU_OPTIONS[i]);
        }
        System.out.print("Please enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static void addLecturer() {
        System.out.println("---- Add a new lecturer: ----");
        String name;
        int id;
        DegreeType degreeType;
        String degreeName;
        double salary;
        Department department;

        System.out.print("Enter the lecturer's name: ");
        name = scanner.nextLine();

        while (lecturerNameExists(name)) {
            System.out.println("This lecturer already exists. Please enter a different name - ");
            name = scanner.nextLine();
        }

        System.out.print("Enter the lecturer's ID - ");
        id = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the lecturer's degree type (BACHELOR, MASTER, DR, PROF) - ");
        String input = scanner.nextLine().toUpperCase();

        while (!DegreeType.validDegreeType(input)) {
            System.out.println("Invalid input. Please enter - BACHELOR, MASTER, DR, or PROF ");
            input = scanner.nextLine().toUpperCase();
        }

        degreeType = DegreeType.valueOf(input);

        System.out.print("Enter the lecturer's degree name -  ");
        degreeName = scanner.nextLine();

        System.out.print("Enter the lecturer's salary - ");
        salary = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the lecturer's department: ");
        String deptName = scanner.nextLine();

        while (!deptNameExists(deptName)){
            System.out.println("This department doesn't exist. Please enter one of the following existing departments," +
                    "or enter 0 for adding a new department  - ");
            printAllDepartments();

            deptName = scanner.nextLine();

            if (deptName.equals("0")){
                addDepartment();
                System.out.print("Enter the lecturer's department again: ");
                deptName  = scanner.nextLine();
            }
        }
        department = getDepartmentByName(deptName);

        if (lecturerCounter == lecturers.length) {
            lecturers = expandLecturerArray();
        }

        Lecturer newLecturer = new Lecturer( name,  id,  degreeType,  degreeName,  salary,  department);
        lecturers[lecturerCounter++] = newLecturer;

        System.out.println("Lecturer added!");
    }


    private static void addBoard() {
        System.out.println("---- Add a new board: ----");

        System.out.print("Enter the board's name: ");
        String name = scanner.nextLine();

        while (boardNameExists(name)) {
            System.out.println("This board already exists. Please enter a different name - ");
            name = scanner.nextLine();
        }


        Lecturer head = boardsHeadValidation();

        if (boardCounter == boards.length) {
            boards = expandBoardArray();
        }

        boards[boardCounter++] = new Board(name, head);
        System.out.println("Board added.");
    }

    private static Lecturer lecturerValidation(String name){

        while (!lecturerNameExists(name)){
            System.out.println("This lecturer doesn't exist. Please enter one of the following existing lecturers," +
                    "or enter 0 for adding a new lecturer  - ");
            displayLecturersInfo();

            name= scanner.nextLine();

            if (name.equals("0")){
                addLecturer();
                System.out.print("Enter the lecturer's name again: ");
                name = scanner.nextLine();
            }


        }
        return getLecturerByName(name);
    }

    private static Lecturer boardsHeadValidation(){

        System.out.print("Let's assign the board's head: ");
        String candidateName = scanner.nextLine();

        Lecturer candidate = lecturerValidation(candidateName);

        while (!isDrOrHigher(candidate)){
            System.out.print("Only lecturer's who has a DR degree can be head of board. please choose a different lecturer, or enter 0 to add a new lecturer - ");
             candidateName = scanner.nextLine();

            if (candidateName.equals("0")){
                addLecturer();
                System.out.print("Enter the lecturer's name again: ");
                candidateName = scanner.nextLine();
                candidate = lecturerValidation(candidateName);
            }
        }
        return candidate;

    }

    public static Board boardValidation(String name){
        while (!boardNameExists(name)){
            System.out.println("This board doesn't exist. Please enter one of the following existing boards," +
                    "or enter 0 for adding a new board  - ");
            displayBoardsInfo();

            name= scanner.nextLine();

            if (name.equals("0")){
                addBoard();
                System.out.print("Enter the board's name again: ");
                name = scanner.nextLine();
            }

        }
        return getBoardByName(name);

    }


    private void assignLecturerToBoard() {
        System.out.println("---- Assign a lecturer to a board: ----");

        System.out.print("Enter lecturer name: ");
        String lecturerName = scanner.nextLine();

        Lecturer lecturer = lecturerValidation(lecturerName);

        System.out.print("Enter board name: ");
        String boardName = scanner.nextLine();

        Board b = boardValidation(boardName);


        b.assignLecturer(lecturer);
        System.out.println("Lecturer assigned to board!");
    }

    private void updateHeadOfBoard() {
        System.out.println("---- Update Head of Board: ----");

        System.out.print("Enter the board's name: ");
        String name = scanner.nextLine();

        Board b = boardValidation(name);

        Lecturer l = boardsHeadValidation();

        b.setHeadOfBoard(l);

        System.out.println("Lecturer assigned as head of board!");


    }

    private void removeLecturerFromBoard() {
        System.out.println("---- Remove a lecturer from a board: ----");

        System.out.print("Enter the lecturer's name: ");
        String lecturerName = scanner.nextLine();
        Lecturer lecturer = lecturerValidation(lecturerName);

        System.out.print("Enter the board's name: ");
        String boardName = scanner.nextLine();

        Board board = boardValidation(boardName);

        lecturer.leaveBoard(board);
        board.removeLecturerFromBoard(lecturer);

        System.out.println("Lecturer removed from board!");

    }

    private static void addDepartment() {
        System.out.println("---- Add a new department: ----");

        System.out.print("Enter department name: ");
        String name = scanner.nextLine();

        while (deptNameExists(name) ) {
            System.out.println("This department name already exists. Please enter a different name - ");
            name = scanner.nextLine();
        }

        if (departmentCounter == departments.length) {
            departments = expandDepartmentArray();
        }

        System.out.print("Enter amount of students enrolled in the department: ");
        int studentsNum = Integer.parseInt(scanner.nextLine());

        departments[departmentCounter++] = new Department(name, studentsNum);
        System.out.println("Department added.");
    }

    private void displayAvgSalary() {
        if (lecturerCounter == 0) {
            System.out.println("No lecturers available.");
            return;
        }

        double sum = 0;
        for (int i = 0; i < lecturerCounter; i++) {
            sum += lecturers[i].getSalary();
        }

        System.out.println("Average salary of all lecturers: "+ sum / lecturerCounter);
    }

    private void displayAvgSalaryInDept() {
        System.out.println("---- Display average salary in department ----");

        if (departmentCounter == 0 || lecturerCounter == 0) {
            System.out.println("No departments or lecturers available.");
            return;
        }

        System.out.print("Enter department name: ");
        String deptName = scanner.nextLine();

        while (!deptNameExists(deptName)) {
            System.out.println("This department doesn't exist. Please enter a valid department name:");
            printAllDepartments();
            deptName = scanner.nextLine();
        }

        Department dept = getDepartmentByName(deptName);

        if (dept.getLecturersNum()==0) {
            System.out.println("No lecturers in this department.");
            return;
        }

            System.out.println("Average salary in " + deptName + ": " + dept.averageSalary());

    }


    private static void displayLecturersInfo() {
        for (int i = 0; i < lecturerCounter; i++) {
            System.out.println(lecturers[i]);
        }
    }

    private static void displayBoardsInfo() {
        for (int i = 0; i < boardCounter; i++) {
            System.out.println(boards[i].getName() );
        }
    }

  /*  private void assignLecturerToDept() {
        System.out.println("---- Assign a lecturer to a department ----");

        System.out.print("Enter the lecturer's name: ");
        String lecturerName = scanner.nextLine();
        Lecturer lecturer = lecturerValidation(lecturerName);

        System.out.print("Enter the department name: ");
        String deptName = scanner.nextLine();


        lecturer.setDepartment(departmentValidation(deptName));

        System.out.println("Lecturer assigned to department!");
    }*/

    public static Department departmentValidation(String deptName){
        while (!deptNameExists(deptName)) {
            System.out.println("This department doesn't exist. Please enter a valid department name or enter 0 to add a new one:");
            printAllDepartments();
            deptName = scanner.nextLine();

            if (deptName.equals("0")) {
                addDepartment();
                System.out.print("Enter the department name again: ");
                deptName = scanner.nextLine();
            }
        }
        return getDepartmentByName(deptName);
    }

    // Helper methods
    private static boolean lecturerNameExists(String name) {
        for (int i = 0; i < lecturerCounter; i++) {
            if (lecturers[i].getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean boardNameExists(String name) {
        for (int i = 0; i < boardCounter; i++) {
            if (boards[i].getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean deptNameExists(String name) {
        for (int i = 0; i < departmentCounter; i++) {
            if (departments[i].getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static Department getDepartmentByName(String name){
        for (int i = 0; i < departmentCounter; i++) {
            if (departments[i].getName().equalsIgnoreCase(name)) {
                return departments[i];
            }
        }
        return null; // Not found
    }

    private static Lecturer getLecturerByName(String name){
        for (int i = 0; i < lecturerCounter; i++) {
            if (lecturers[i].getName().equalsIgnoreCase(name)) {
                return lecturers[i];
            }
        }
        return null; // Not found
    }

    private static Board getBoardByName(String name){
        for (int i = 0; i < boardCounter; i++) {
            if (boards[i].getName().equalsIgnoreCase(name)) {
                return boards[i];
            }
        }
        return null; // Not found
    }
    public static Lecturer[] expandLecturerArray() {
        return lecturers = Arrays.copyOf(lecturers,lecturerCounter * 2 );
    }

    public static Board[] expandBoardArray() {
        return boards = Arrays.copyOf(boards,boardCounter * 2 );
    }

    public static Department[] expandDepartmentArray() {
        return departments = Arrays.copyOf(departments, departmentCounter * 2);
    }

    private static void printAllDepartments() {
        for (int i = 0; i < departmentCounter; i++) {
            System.out.println("- " + departments[i].getName());
        }
    }


    private static boolean isDrOrHigher(Lecturer candidate){
        return (candidate.getDegreeType() == DegreeType.DR || candidate.getDegreeType() == DegreeType.PROF) ;
    }
}

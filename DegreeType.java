package amy_lihy_project2;

public enum DegreeType {
    BACHELOR,
    MASTER,
    DR,
    PROF;

    // checks is the degree type is valid
    public static boolean validDegreeType(String input) {
        for (DegreeType type : DegreeType.values()) {
            if (type.name().equals(input.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}

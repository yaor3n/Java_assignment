public class SessionManager {
    private static String lecturerUsername;
    private static String studentUsername;

    // Private constructor to prevent instantiation
    private SessionManager() {
    }

    // Singleton instance
    private static class Holder {
        private static final SessionManager INSTANCE = new SessionManager();
    }

    // Get the singleton instance
    public static SessionManager getInstance() {
        return Holder.INSTANCE;
    }

    // Get and set lecturer username
    public static String getLecturerUsername() {
        return lecturerUsername;
    }

    public static void setLecturerUsername(String username) {
        lecturerUsername = username;
    }

    // Get and set student username
    public static String getStudentUsername() {
        return studentUsername;
    }

    public static void setStudentUsername(String username) {
        studentUsername = username;
    }

    // Clear session data (e.g., for logout)
    public static void clearSession() {
        lecturerUsername = null;
        studentUsername = null;
    }
}

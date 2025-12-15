package tms.models;

public class AdminUser extends User {
    public AdminUser(String name, String email) { super(name, email); }
    @Override public String getRole() { return "Admin"; }
}

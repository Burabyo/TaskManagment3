package tms.models;

/** Regular user with limited permissions. */
public class RegularUser extends User {
    public RegularUser(String name, String email) {
        super(name, email);
    }

    @Override
    public String getRole() {
        return "Regular";
    }
}

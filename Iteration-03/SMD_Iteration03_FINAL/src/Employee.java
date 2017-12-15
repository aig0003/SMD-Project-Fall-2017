public class Employee {
    private String username;
    private String password;
    private String name;
    private String profilePicURL;
    private int employeeType;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProfilePicURL() { return profilePicURL; }
    public void setProfilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; }

    public int getEmployeeType() { return employeeType; } //0 for Cashier, 1 for manager
    public void setEmployeeType(int employeeType) { this.employeeType = employeeType; }
}

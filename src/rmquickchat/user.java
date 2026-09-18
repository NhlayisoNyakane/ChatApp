
package rmquickchat;

/**
 *
 * @author Student
 */
public class user {
    

    private String username;
    private String password;
    private String cellphone;

    public user(
            String username,
            String password,
            String cellphone) {

        this.username = username;
        this.password = password;
        this.cellphone = cellphone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellphone() {
        return cellphone;
    }
}


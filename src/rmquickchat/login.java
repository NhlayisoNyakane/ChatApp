
package rmquickchat;

/**
 *
 * @author Student
 */
public class login {
    

    private user registeredUser = null;

    // Check username
    public boolean checkUsername(String username) {

        if (username == null) {
            return false;
        }

        return username.contains("_")
                && username.length() <= 5;
    }

    // Check password complexity
    public boolean checkPasswordComplexity(String password) {

        if (password == null) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {

            if (Character.isUpperCase(c)) {

                hasUpper = true;

            } else if (Character.isDigit(c)) {

                hasDigit = true;

            } else if (!Character.isLetterOrDigit(c)) {

                hasSpecial = true;
            }
        }

        return hasUpper && hasDigit && hasSpecial;
    }

    // Check cellphone
    public boolean checkCellphone(String cellphone) {

        if (cellphone == null) {
            return false;
        }

        String saPattern = "^\\+27\\d{9}$";

        return cellphone.matches(saPattern);
    }

    // Register user
    public String registerUser(
            String username,
            String password,
            String cellphone) {

        if (!checkUsername(username)) {

            return "Username is not correctly formatted. "
                    + "It must contain an underscore "
                    + "and be no more than five characters.";
        }

        if (!checkPasswordComplexity(password)) {

            return "Password is not correctly formatted. "
                    + "It must be at least eight characters long "
                    + "and contain a capital letter, number "
                    + "and special character.";
        }

        if (!checkCellphone(cellphone)) {

            return "Cellphone number is incorrectly formatted. "
                    + "Use the international format, for example "
                    + "+27838968976.";
        }

        registeredUser
                = new user(username, password, cellphone);

        return "Registration successful.";
    }

    // Login
    public boolean loginUser(
            String username,
            String password) {

        if (registeredUser == null) {
            return false;
        }

        return registeredUser.getUsername().equals(username)
                && registeredUser.getPassword().equals(password);
    }

    // Login status
    public String returnLoginStatus(
            boolean loginResult,
            String username) {

        if (!loginResult) {

            return "Username or password incorrect, please try again.";
        }

        String firstName = "";
        String lastName = "";

        if (username != null && username.contains("_")) {

            String[] parts = username.split("_", 2);

            firstName
                    = parts.length > 0 ? parts[0] : "";

            lastName
                    = parts.length > 1 ? parts[1] : "";

        } else {

            firstName = username;
        }

        return "Welcome "
                + firstName
                + ", "
                + lastName
                + ". It is great to see you again.";
    }

    public user getRegisteredUser() {

        return registeredUser;
    }
}


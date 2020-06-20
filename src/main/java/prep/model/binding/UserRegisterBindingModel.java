package prep.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import java.math.BigDecimal;

public class UserRegisterBindingModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private BigDecimal Budget;

    public UserRegisterBindingModel() {
    }
    @Length(min = 2, message = "Username length must be more than two characters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Length(min = 2, message = "Password length must be more than two characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @DecimalMin(value = "0.0", inclusive = false)
    public BigDecimal getBudget() {
        return Budget;
    }

    public void setBudget(BigDecimal budget) {
        Budget = budget;
    }
}

package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account userRegistration(Account user){
        if(user.getUsername().length() != 0 && user.getPassword().length() >= 4) {
            return accountDAO.userRegistration(user);
        }
        return null;
    }

    public Account userLogin(Account user){
        return accountDAO.userLogin(user);
    }
}

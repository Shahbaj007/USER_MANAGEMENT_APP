package in.rahulit.service;

import java.util.Map;

import in.rahulit.bindings.LoginForm;
import in.rahulit.bindings.RegisterForm;
import in.rahulit.bindings.ResetPwdForm;
import in.rahulit.entity.User;

public interface UserService {
	
	public Map<Integer, String> getCountries();
	
	public Map<Integer, String> getStates(Integer countryId);
	
	public Map<Integer, String> getCities(Integer stateId);
	
	public User getUser(String email);
	
	public boolean saveUser(RegisterForm formObj);
	
	public User login(LoginForm formObj);
	
	public boolean resetPassword(ResetPwdForm formObj);
	
}

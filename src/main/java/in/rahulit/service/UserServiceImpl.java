package in.rahulit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.rahulit.bindings.LoginForm;
import in.rahulit.bindings.RegisterForm;
import in.rahulit.bindings.ResetPwdForm;
import in.rahulit.entity.City;
import in.rahulit.entity.Country;
import in.rahulit.entity.State;
import in.rahulit.entity.User;
import in.rahulit.repository.CityRepo;
import in.rahulit.repository.CountryRepo;
import in.rahulit.repository.StateRepo;
import in.rahulit.repository.UserRepo;
import in.rahulit.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	@Autowired
	private CityRepo cityRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public Map<Integer, String> getCountries() {
		List<Country> allCountries = countryRepo.findAll();
		
		Map<Integer, String> countriesMap = new HashMap<>();
		
		allCountries.forEach(c -> {
			countriesMap.put(c.getCountryId(), c.getCountryName());
		});
		
		return countriesMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		
		List<State> allStates = stateRepo.findByCountryId(countryId);
		
		Map<Integer, String> statesMap = new HashMap<>();
		
		allStates.forEach(s -> {
			statesMap.put(s.getStateId(), s.getStateName());
		});
		
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		
		List<City> allCities = cityRepo.findByStateId(stateId);		
		
		Map<Integer, String> citiesMap = new HashMap<>();
		
		allCities.forEach(city -> {
			citiesMap.put(city.getCityId(), city.getCityName());
		});
		
		return citiesMap;
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public boolean saveUser(RegisterForm formObj) {
		
		//To insert the data in DB we need entity object but here data is coming in binding object so we need to copy the data from binding
		//obj to entity obj.. To copy data from one class to another all the fields should have same name and same return type... We use BeanUtils class to copy the data from one class to another
		
		//As user is not entering the password first time while registering so we need to set password before saving the record..
		
		formObj.setPwd(generateRandomPwd());
		formObj.setPwdUpdated("NO");
		
		User userEntity = new User();
		
		BeanUtils.copyProperties(formObj, userEntity);		
		
		userRepo.save(userEntity);
		
		String subject = "Your Account Created - Ashok IT.";
		String body = "Your Password is : " + formObj.getPwd();
		
		return emailUtils.sendEmail(subject, body, formObj.getEmail());
		
	}

	private String generateRandomPwd() {
		
		String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";

		StringBuffer randomString = new StringBuffer(5);
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			int randomIndex = random.nextInt(alphanumericCharacters.length());
			char randomChar = alphanumericCharacters.charAt(randomIndex);
			randomString.append(randomChar);
		}

		return randomString.toString();
		
	}

	@Override
	public User login(LoginForm formObj) {
		return userRepo.findByEmailAndPwd(formObj.getEmail(),formObj.getPwd());
	}

	@Override
	public boolean resetPassword(ResetPwdForm formObj) {
		Optional<User> id = userRepo.findById(formObj.getUserId());
		
		if(id.isPresent()) {
			User user = id.get();
			user.setPwd(formObj.getNewPwd());
			user.setPwdUpdated("YES");
			userRepo.save(user);
			return true;
		}
		
		return false;
	}

}

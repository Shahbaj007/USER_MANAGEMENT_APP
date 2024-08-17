package in.rahulit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rahulit.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
	
	public User findByEmailAndPwd(String email, String pwd);
	
}

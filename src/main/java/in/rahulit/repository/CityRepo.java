package in.rahulit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rahulit.entity.City;

public interface CityRepo extends JpaRepository<City, Integer>{
	
	public List<City> findByStateId(Integer stateId);
	
}

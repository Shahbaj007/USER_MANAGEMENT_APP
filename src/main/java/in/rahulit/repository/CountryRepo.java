package in.rahulit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rahulit.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Integer>{

}

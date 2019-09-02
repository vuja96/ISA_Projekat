package ISA.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ISA.project.model.AvionskaKarta;

public interface AvionskaKartaRepozitorijum extends JpaRepository<AvionskaKarta, Long> {

	@Query("select karta from AvionskaKarta karta where karta.sediste.idSedista = ?1")
	public AvionskaKarta vratiKartu(long id);
	
	@Query("select karta from AvionskaKarta karta where karta.idKarte = ?1")
	public AvionskaKarta vratiKartuPoId(long id);
	
	@Query("select karta.datum, count(karta) from AvionskaKarta karta where "
			+ "karta.let.avioKompanija.kompanijaId = :idKompanije and karta.sediste.status = ISA.project.model.StatusSedista.REZERVISANO group by karta.datum")
	public List<Object[]> vratiStatistikuPoDanu(@Param("idKompanije") long idKompanije);
	
	@Query(value = "select concat(year(datum), '/', week(datum)), count(*) from AvionskaKarta karta where karta.let.avioKompanija.kompanijaId = :idKompanije and karta.sediste.status = ISA.project.model.StatusSedista.REZERVISANO group by concat(year(datum), '/', week(datum))")
	public List<Object[]> vratiStatistikuPoNedelji(@Param("idKompanije") long idKompanije);
	
	@Query(value = "select year(datum), count(*) from AvionskaKarta karta where karta.let.avioKompanija.kompanijaId = :idKompanije and karta.sediste.status = ISA.project.model.StatusSedista.REZERVISANO group by year(datum)")
	public List<Object[]> vratiStatistikuPoGodini(@Param("idKompanije") long idKompanije);
}

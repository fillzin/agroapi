package br.com.agrostok.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.agrostok.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification>{

	List<Notification> findByUserCreatedIdAndReadedAndDeleted(Long userId, boolean readed, boolean deleted);
	
	@Query(value = "SELECT x FROM Notification x WHERE x.userCreatedId =:userId and x.deleted = false")
	Page<Notification> findByFiltros(Long userId, Pageable pageable);
}

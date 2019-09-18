package br.com.agrostok.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.agrostok.dto.NotificationDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.entity.Notification;
import br.com.agrostok.entity.User;
import br.com.agrostok.exception.AppRuntimeException;
import br.com.agrostok.repository.NotificationRepository;
import br.com.agrostok.util.AppUtil;
import br.com.gestaoprocesso.enums.PaginacaoEnum;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private UserService userService;

	@Transactional
	public void createNotification(User user, String message) {
		Notification notification = new Notification();
		notification.setCreatedDate(LocalDateTime.now());
		notification.setMsg(message);
		notification.setReaded(false);
		notification.setDeleted(false);
		notification.setUserCreatedId(user.getId());
 
		notificationRepository.save(notification);
	}

	public List<NotificationDto> listAll(PaginacaoDto paginacaoDto) {
		try {
			PageRequest paginacao = PageRequest.of(PaginacaoEnum.getPage(paginacaoDto.getPagina()),
					PaginacaoEnum.getTotalRegistros(paginacaoDto.getQtdRegistros()));

			Page<Notification> pageNotifications = notificationRepository
					.findByFiltros(userService.getLoggerUser().getId(), paginacao);
			if (!pageNotifications.getContent().isEmpty()) {
				return pageNotifications.getContent().stream().map(not -> {
					return new NotificationDto(not.getMsg(), not.getReaded(), not.getId(), getTimeSended(not.getCreatedDate()));
				}).collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

		return new ArrayList<NotificationDto>();
	}

	private String getTimeSended(LocalDateTime createdDate) {
		LocalDateTime actualDate = LocalDateTime.now();
		if (LocalDateTime.now().getDayOfYear() == createdDate.getDayOfYear()) {
			long minutes = createdDate.until(actualDate, ChronoUnit.MINUTES);
			if (minutes < 60) {
				return minutes + " minutos";
			} else {
				long hours = createdDate.until(actualDate, ChronoUnit.HOURS);
				return hours + " horas";
			}

		} else {
			long days = createdDate.until(actualDate, ChronoUnit.DAYS);
			return days + " dias";
		}

	}

	public List<NotificationDto> listAllUnread() {
		try {
			List<Notification> notifications = notificationRepository
					.findByUserCreatedIdAndReadedAndDeleted(userService.getLoggerUser().getId(), false, false);
			return notifications.stream().map(not -> {
				return new NotificationDto(not.getMsg(), not.getReaded(), not.getId(), getTimeSended(not.getCreatedDate()));
			}).collect(Collectors.toList());
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}
	}

	public void delete(Long id) {
		Optional<Notification> notification = notificationRepository.findById(id);
		if (notification.isPresent()) {
			notification.get().setDeleted(true);
			notificationRepository.save(notification.get());
		}
	}

	public void read(Long id) {
		Optional<Notification> notification = notificationRepository.findById(id);
		if (notification.isPresent()) {
			notification.get().setReaded(true);
			notificationRepository.save(notification.get());
		}

	}

}

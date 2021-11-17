package br.com.agrostok.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.agrostok.dto.StockHistDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.entity.Stock;
import br.com.agrostok.entity.StockHist;
import br.com.agrostok.exception.AppRuntimeException;
import br.com.agrostok.repository.StockHistRepository;
import br.com.agrostok.util.AppUtil;
import br.com.gestaoprocesso.enums.PaginacaoEnum;
import br.com.gestaoprocesso.enums.StockOperationEnum;

@Service
public class StockHistService {

	private static final Logger logger = LogManager.getLogger(StockHistService.class);

	@Autowired
	private StockHistRepository stockHistRepository;

	@Autowired
	private UserService userService;


	@Transactional
	public void createStockHist(Stock stock, StockOperationEnum stockOperationEnum, Integer count) {
		logger.info("Criando historico para o stock: " + stock.getId());
		StockHist stockHist = new StockHist();
		stockHist.setProduct(stock.getProduct());
		stockHist.setCount(count);
		stockHist.setUserCreatedId(userService.getLoggerUser().getId());
		stockHist.setCreatedDate(LocalDateTime.now());
		stockHist.setOperation(stockOperationEnum.getCode());
		stockHist.setStock(stock);
		
		stockHistRepository.save(stockHist);

	}


	public List<StockHistDto> listAll(PaginacaoDto paginacaoDto, String name) {
		try {
			PageRequest paginacao = PageRequest.of(PaginacaoEnum.getPage(paginacaoDto.getPagina()),
					PaginacaoEnum.getTotalRegistros(paginacaoDto.getQtdRegistros()));

			Page<StockHist> pageClients = stockHistRepository.findByFiltros(userService.getLoggerUser().getId(), paginacao, name);
			if (!pageClients.getContent().isEmpty()) {
				return pageClients.getContent().stream().map(stock -> {
					StockHistDto dto = new StockHistDto();
					if (Objects.nonNull(stock.getProduct())) {
						dto.setProductName(stock.getProduct().getName());
					} else {
						dto.setProductName(stock.getStock().getIngrediente().getName());
					}

					dto.setCount(stock.getCount());
					dto.setCreatedDate(stock.getCreatedDate());
					dto.setOperation(StockOperationEnum.getByCode(stock.getOperation()).getDescricao());
					dto.setValue(stock.getStock().getValue());
					return dto;
				}).collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

		return new ArrayList<StockHistDto>();
	}

}

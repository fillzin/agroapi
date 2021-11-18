package br.com.agrostok.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.agrostok.converter.IngredienteConverter;
import br.com.agrostok.entity.Ingrediente;
import br.com.agrostok.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.agrostok.dto.ProductDto;
import br.com.agrostok.dto.SaleDto;
import br.com.agrostok.dto.StockDto;
import br.com.agrostok.dto.StockHistDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.entity.Product;
import br.com.agrostok.entity.Stock;
import br.com.agrostok.entity.StockHist;
import br.com.agrostok.exception.AppBusinessException;
import br.com.agrostok.exception.AppRuntimeException;
import br.com.agrostok.repository.StockRepository;
import br.com.agrostok.util.AppUtil;
import br.com.gestaoprocesso.enums.PaginacaoEnum;
import br.com.gestaoprocesso.enums.StockOperationEnum;

@Service
public class StockService {

//	private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StockHistService stockHistService;

    @Autowired
    private NotificationService notificationService;

//	@Autowired
//	private ModelMapper modelMapper;

    @Transactional
    public void createStock(Product product) {

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setCount(product.getCount());
        stock.setUserCreatedId(userService.getLoggerUser().getId());
        stock.setCreatedDate(LocalDateTime.now());
        stockRepository.save(stock);
        stockHistService.createStockHist(stock, StockOperationEnum.ADD, product.getCount());

    }



    @Transactional
    public void input(SaleDto saleDto) {
        if (Objects.nonNull(saleDto.getIngredienteDto())) {

            Stock stock = new Stock();
            Ingrediente ingrediente = ingredienteRepository.findById(saleDto.getIngredienteDto().getId()).get();
            stock.setIngrediente(ingrediente);
            stock.setValue(saleDto.getValue());
            stock.setCount(saleDto.getCount());
            stock.setUserCreatedId(userService.getLoggerUser().getId());
            stock.setUpdatedDate(LocalDateTime.now());

            stockRepository.save(stock);
            stockHistService.createStockHist(stock, StockOperationEnum.ADD, saleDto.getCount());
        } else {
            insertProduct(saleDto);
        }

    }

    private void insertProduct(SaleDto saleDto) {
        for (ProductDto productDto : saleDto.getProducts()) {
            addFromStock(productDto.getProductId(), productDto.getCount());
        }
    }

    @Transactional
    public void subtractFromStock(Product product, Integer countToSubtract) {
        Optional<Stock> stock = stockRepository.findByProductAndUser(userService.getLoggerUser().getId(), product.getId());
        if (!stock.isPresent()) {
            throw new AppBusinessException(AppUtil.generateRandomString(), "Estoque não encontrado para esse produto.");
        }

        int quantidade = stock.get().getCount() - countToSubtract;
        stock.get().setUserUpdatedId(userService.getLoggerUser().getId());
        stock.get().setUpdatedDate(LocalDateTime.now());
        stock.get().setCount(quantidade);
        stockRepository.save(stock.get());
        stockHistService.createStockHist(stock.get(), StockOperationEnum.SUBTRACT, countToSubtract);

        if (Objects.nonNull(product) && product.getMinStock() > 0) {
            if (quantidade < product.getMinStock()) {
                notificationService.createNotification(userService.getLoggerUser(), "O produto " + product.getName() + " está com estoque baixo.");
            }
        }

    }

    @Transactional
    public void addFromStock(Long productId, Integer countToAdd) {
        Optional<Stock> stock = stockRepository.findByProductAndUser(userService.getLoggerUser().getId(), productId);
        if (!stock.isPresent()) {
            throw new AppBusinessException(AppUtil.generateRandomString(), "Estoque não encontrado para esse produto.");
        }

        int quantidade = stock.get().getCount() + countToAdd;
        stock.get().setUserUpdatedId(userService.getLoggerUser().getId());
        stock.get().setUpdatedDate(LocalDateTime.now());
        stock.get().setCount(quantidade);
        stockRepository.save(stock.get());
        stockHistService.createStockHist(stock.get(), StockOperationEnum.ADD, countToAdd);
    }

    public Optional<Stock> findByProductAndUser(Long id, Long productId) {
        return stockRepository.findByProductAndUser(userService.getLoggerUser().getId(), productId);
    }
    
	public List<StockDto> listAllEntradas(PaginacaoDto paginacaoDto, String name) {
		try {
			PageRequest paginacao = PageRequest.of(PaginacaoEnum.getPage(paginacaoDto.getPagina()),
					PaginacaoEnum.getTotalRegistros(paginacaoDto.getQtdRegistros()));

			Page<Stock> pageClients = stockRepository.findByFiltros(userService.getLoggerUser().getId(), paginacao, name);
			if (!pageClients.getContent().isEmpty()) {
				return pageClients.getContent().stream().map(stock -> {
					StockDto dto = new StockDto();
					if (Objects.nonNull(stock.getProduct())) {
						dto.setProductName(stock.getProduct().getName());
					} else {
						dto.setProductName(stock.getIngrediente().getName());
					}

					dto.setCount(stock.getCount());
					
					
					dto.setValue(stock.getValue());
					return dto;
				}).collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

		return new ArrayList<StockDto>();
	}

}

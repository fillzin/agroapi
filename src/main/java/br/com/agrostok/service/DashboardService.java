package br.com.agrostok.service;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import br.com.agrostok.repository.SaleRepository;
import br.com.agrostok.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agrostok.dto.SaledProductDto;
import br.com.agrostok.dto.TotalSaledDto;
import br.com.agrostok.entity.Sale;

@Service
public class DashboardService {

//	private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private StockRepository stockRepository;

//	@Autowired
//	private UserService userService;

    public List<SaledProductDto> getTopSaleProducts() {
        return productService.findProductsWithTotalSaled().stream().limit(5).collect(Collectors.toList());
    }

    public List<TotalSaledDto> getSaleTotalPerMonth() {
        List<Sale> sales = saleService.getAllSaleByActualYear();

        List<TotalSaledDto> salesProductByMonth = new ArrayList<TotalSaledDto>();
        for (int month = JANUARY.getValue(); month <= DECEMBER.getValue(); month++) {
            List<Sale> salesByMonth = sales.stream().filter(filterByMonth(month)).collect(Collectors.toList());
            if (Objects.nonNull(salesByMonth) && !salesByMonth.isEmpty()) {
                BigDecimal valueByMonth = salesByMonth.stream().map(Sale::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
                salesProductByMonth.add(new TotalSaledDto(Month.of(month).getDisplayName(TextStyle.FULL, new Locale("pt")), valueByMonth));

            } else {
                salesProductByMonth.add(new TotalSaledDto(Month.of(month).getDisplayName(TextStyle.FULL, new Locale("pt")), BigDecimal.ZERO));
            }

        }

        return salesProductByMonth;
    }

    public static Predicate<Sale> filterByMonth(int month) {
        return p -> p.getCreatedDate().getMonth().getValue() == month;
    }

    public List<SaledProductDto> listSalesByProduct() {
        return productService.findProductsWithTotalSaled().stream().limit(20).collect(Collectors.toList());
    }

    public List<SaledProductDto> listSalesByProductAndMonth(LocalDate startDate) {
        return productService.findProductsWithTotalSaledGroupedByMonth(startDate);
    }

    public SaledProductDto listReceitaAndDespesa() {
        SaledProductDto dto = new SaledProductDto().setDespesa(stockRepository.somaDespesa())
                .setReceita(saleRepository.somaReceita());
        dto.setLiquido(dto.getReceita().subtract(dto.getDespesa()));

        return dto;
    }
}

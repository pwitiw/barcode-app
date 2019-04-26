//package com.frontwit.barcodeapp.synchronization;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.frontwit.barcodeapp.orderprocess.domain.Stage;
//import com.frontwit.barcodeapp.domain.order.dto.ComponentDto;
//import com.frontwit.barcodeapp.domain.order.dto.OrderDetailDto;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.persistence.EntityManager;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static com.frontwit.barcodeapp.synchronization.ComponentSymbol.*;
//
//public class SynchronizationService {
//
//    private static final Logger LOG = LoggerFactory.getLogger(SynchronizationService.class);
//
//    private static final Integer ID = 0;
//    private static final Integer NR = 1;
//    private static final Integer POSITION = 2;
//    private static final Integer ORDER_DATE = 3;
//    private static final Integer CUSTOMER_NR = 4;
//    private static final Integer DESCRIPTION = 5;
//    private static final Integer FEATURES = 6;
//    private static final Integer CUSTOMER_NAME = 7;
//    private static final Integer ROUTE = 8;
//
//    private static final String DICTIONARY_QUERY = "SELECT id, name FROM tdictionary";
//
//    private EntityManager entityManager;
//    private ObjectMapper objectMapper;
//
//    public SynchronizationService(final EntityManager entityManager, final ObjectMapper objectMapper) {
//        this.entityManager = entityManager;
//        this.objectMapper = objectMapper;
//    }
//
////    public List<OrderDetailDto> getOrders() {
////        List<Object[]> ordersToProcess = entityManager.createNativeQuery(getOrdersQuery()).setMaxResults(4000)
////                .getResultList();
////        Map<Integer, String> dictionary = getDictionary();
////        return ordersToProcess.stream()
////                .map(o -> {
////                    try {
////                        return createOrder(o, dictionary);
////                    } catch (IOException e) {
////                        LOG.error(String.format("Bad json format while synchronizing order: %d", (int) o[ID]), e.getMessage());
////                    }
////                    return null;
////                })
////                .filter(Objects::nonNull)
////                .collect(Collectors.toList());
////    }
//
//    private Map<Integer, String> getDictionary() {
//        final List<Object[]> dictionaryToProcess = entityManager.createNativeQuery(DICTIONARY_QUERY).getResultList();
//        Map<Integer, String> dictionary = new HashMap<>();
//        dictionaryToProcess.forEach(entry -> dictionary.put((Integer) entry[0], (String) entry[1]));
//        return dictionary;
//    }
//
//    private OrderDetailDto createOrder(Object[] o, Map<Integer, String> dictionary) throws IOException {
//        OrderDetailDto order = new OrderDetailDto();
//        order.setExtId(Long.valueOf(o[ID].toString()));
//        order.setName(getValueOrEmpty(String.valueOf(o[NR].toString())));
//        order.setOrderedAt(LocalDate.parse(String.valueOf(o[ORDER_DATE])));
//        order.setCustomer(getValueOrEmpty(o[CUSTOMER_NAME]));
//        order.setRoute(getValueOrEmpty(o[ROUTE]));
//        order.setStage(Stage.MILLING);
//        setComponents(order, String.valueOf(o[POSITION]));
//        setComment(order, o);
//        setOrderInfo(order, String.valueOf(o[FEATURES]), dictionary);
//        return order;
//    }
//
//    private void setComment(OrderDetailDto order, Object o[]) {
//        String separator = o[CUSTOMER_NR] != null && o[DESCRIPTION] != null ? "; " : "";
//        String comment = getValueOrEmpty(o[DESCRIPTION]);
//        String additionalInfo = getValueOrEmpty(o[CUSTOMER_NR]);
//        order.setComment(comment + separator + additionalInfo);
//    }
//
//    private void setOrderInfo(OrderDetailDto order, String jsonObject, final Map<Integer, String>
//            dictionary) throws IOException {
//        Map<String, String> infoMap = objectMapper.readValue(jsonObject, HashMap.class);
//        String cutter = dictionary.create(Integer.valueOf(infoMap.create(CUTTER.getSymbol()))).toUpperCase();
//        String color = dictionary.create(Integer.valueOf(infoMap.create(COLOR.getSymbol()))).toUpperCase();
//        String size = dictionary.create(Integer.valueOf(infoMap.create(SIZE.getSymbol()))).toUpperCase();
//        order.setCutter(cutter);
//        order.setColor(color);
//        order.setSize(size);
//    }
//
//    private void setComponents(OrderDetailDto order, String jsonArray) throws IOException {
//        Map<String, String>[] componentMaps = objectMapper.readValue(jsonArray, HashMap[].class);
//        List<ComponentDto> components = Stream.of(componentMaps)
//                .map(this::parseComponent)
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
//        order.setComponents(components);
//    }
//
//    private List<ComponentDto> parseComponent(final Map<String, String> map) {
//        List<ComponentDto> components = new ArrayList<>();
//        Integer height = Integer.valueOf(map.create(LENGTH.getSymbol()));
//        Integer width = Integer.valueOf(map.create(WIDTH.getSymbol()));
//        Integer quantity = Integer.valueOf(map.create(QUANTITY.getSymbol()));
//        String comment = map.create(COMMENT.getSymbol());
//        for (int i = 0; i < quantity; i++) {
//            components.add(new ComponentDto(height, width, comment));
//        }
//        return components;
//    }
//
//    // TODO either add lastModified condition or modify whole sync mechanism
//    private String getOrdersQuery() {
//        return "SELECT " +
//                "z.id, z.numer, z.pozycje, data_z, z.nr_zam_kl, z.opis, z.cechy, k.nazwa, k.trasa " +
//                "FROM tzamowienia z JOIN tklienci k " +
//                "ON z.tklienci_id = k.id";
//    }
//
//    private String getValueOrEmpty(Object o) {
//        return o != null ? String.valueOf(o).trim() : "";
//    }
//}

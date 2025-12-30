package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 저장(준영속 상태 -> merge 사용)
     * 모든 필드를 변경하기때문에 실무에서 사용하기에 위험성이 높음
     */
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 상품 저장(준영속 상태 -> 영속 상태로 변경 -> 변경 감지 기능 사용)
     * 특정 필드만 업데이트 할 수 있기 때문에 실무에서 사용 권장
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);

        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

        // findItem.change(price, name, stockQuantity)
        // change를 엔티티의 비즈니스 로직으로 추가하여 엔티티 레벨에서 변경하는 구조로 설계하는 것이 좋음
    }

    /**
     * 모든 상품 조회
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 단일 상품 조회
     */
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}

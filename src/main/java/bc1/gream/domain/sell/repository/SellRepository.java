package bc1.gream.domain.sell.repository;

import bc1.gream.domain.sell.entity.Sell;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Sell.class, idClass = Long.class)
public interface SellRepository {

    Sell save(Sell sell);
}
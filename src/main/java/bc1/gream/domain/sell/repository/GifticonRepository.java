package bc1.gream.domain.sell.repository;

import bc1.gream.domain.sell.entity.Gifticon;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Gifticon.class, idClass = Long.class)
public interface GifticonRepository {

    Gifticon save(Gifticon gifticon);
}

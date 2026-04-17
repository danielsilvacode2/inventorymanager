package danielalves.github.GerenciadorVendas.produto;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository  extends JpaRepository<Produto, UUID> {


    Optional<Produto> findBySku(String sku);
}

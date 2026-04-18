package danielalves.github.GerenciadorVendas.produto.service;

import danielalves.github.GerenciadorVendas.produto.Produto;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecs {


    public static Specification<Produto> specsNome(String nome) {
        return ((root, query, cb) -> cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%"));
    }


}

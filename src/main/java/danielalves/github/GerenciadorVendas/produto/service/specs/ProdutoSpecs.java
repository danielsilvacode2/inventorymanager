package danielalves.github.GerenciadorVendas.produto.service.specs;

import danielalves.github.GerenciadorVendas.produto.Produto;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecs {


    public static Specification<Produto> nomeLike(String nome) {
        return ((root, query, cb) -> cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%"));
    }


    public static Specification<Produto> skuLike(String sku) {
        return ((root, query, cb) -> cb.like(cb.upper(root.get("sku")), "%" + sku.toUpperCase() + "%"));
    }


    public static Specification<Produto> descricaoLike(String descricao) {
        return ((root, query, cb) -> cb.like(cb.upper(root.get("descricao")), "%" + descricao.toUpperCase() + "%"));
    }


    public static Specification<Produto> precoEqual(Double preco){
        return ((root, query, cb) ->  cb.equal(root.get("preco"), preco));
    }
}

package danielalves.github.GerenciadorVendas.produto.service;


import danielalves.github.GerenciadorVendas.exceptions.OperacaoNaoPermitidaException;
import danielalves.github.GerenciadorVendas.produto.Produto;
import danielalves.github.GerenciadorVendas.produto.ProdutoRepository;
import danielalves.github.GerenciadorVendas.produto.service.specs.ProdutoSpecs;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProdutoService {


    private final ProdutoRepository repository;
    private final ProdutoValidation validation;

    public void salvar(Produto produto) {

        validation.validar(produto);
        repository.save(produto);

    }

    public Optional<Produto> buscarProduto(UUID id) {

        Optional<Produto> produto = repository.findById(id);

        return produto;

    }

    public void atualizarProduto(Produto produto) {


        if (produto.getId() == null) {
            throw new IllegalArgumentException("E necessario que o produto ja esteja na base");
        }


        validation.validar(produto);
        repository.save(produto);


    }

    public void deletarProduto(Produto produto) {


        if (produto.getQuantidade() == 0) {
            repository.delete(produto);
        } else {
            throw new OperacaoNaoPermitidaException("esse produto ainda esta em estoque n e possivel deletar ele");
        }

    }


    public Page<Produto> pesquisaProduto(String nome, String sku, String descricao, Double preco, Integer page, Integer size) {
        Specification<Produto> specification = (root, query, cb) -> cb.conjunction();

        if (nome != null) {
            specification = specification.and(ProdutoSpecs.nomeLike(nome));
        }

        if (sku != null) {
            specification = specification.and(ProdutoSpecs.skuLike(sku));
        }

        if (descricao != null) {
            specification = specification.and(ProdutoSpecs.descricaoLike(descricao));
        }


        if (preco != null && preco > 0) {
            specification = specification.and(ProdutoSpecs.precoEqual(preco));
        }


        Pageable pageable = PageRequest.of(page, size);

        return repository.findAll(specification, pageable);

    }


}


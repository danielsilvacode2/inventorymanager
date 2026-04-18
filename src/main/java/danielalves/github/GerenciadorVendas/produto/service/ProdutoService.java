package danielalves.github.GerenciadorVendas.produto.service;


import danielalves.github.GerenciadorVendas.exceptions.OperacaoNaoPermitidaException;
import danielalves.github.GerenciadorVendas.produto.Produto;
import danielalves.github.GerenciadorVendas.produto.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.hibernate.dialect.HANADialect;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
        }else {
            throw new OperacaoNaoPermitidaException("esse produto ainda esta em estoque n e possivel deletar ele");
        }

    }


    public List<Produto> pesquisaProduto(String nome){
        Specification<Produto> specification = (root, query, cb) ->  cb.conjunction();

        if(nome != null){
            specification = specification.and(ProdutoSpecs.specsNome(nome));
        }

        return repository.findAll(specification);

    }


}


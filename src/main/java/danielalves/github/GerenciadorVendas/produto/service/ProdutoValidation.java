package danielalves.github.GerenciadorVendas.produto.service;

import danielalves.github.GerenciadorVendas.exceptions.RegistroDuplicadoException;
import danielalves.github.GerenciadorVendas.produto.Produto;
import danielalves.github.GerenciadorVendas.produto.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProdutoValidation {


    private final ProdutoRepository repository;

    public void validar(Produto produto){

        if(validarAuxiliar(produto)){
            throw new RegistroDuplicadoException("Ja existe um produto cadastrado");
        }

    }




    private boolean validarAuxiliar(Produto produto){

        Optional<Produto> possivelProduto = repository.findBySku(produto.getSku());


        if(produto.getId() == null){
            return possivelProduto.isPresent();
        }

        return possivelProduto.isPresent()  && !produto.getId().equals(possivelProduto.get().getId());

    }

}

package danielalves.github.GerenciadorVendas.produto.Controler;

import danielalves.github.GerenciadorVendas.produto.Controler.dto.ProdutoDto;
import danielalves.github.GerenciadorVendas.produto.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toEntity(ProdutoDto dto);

    ProdutoDto toDto(Produto produto);

}

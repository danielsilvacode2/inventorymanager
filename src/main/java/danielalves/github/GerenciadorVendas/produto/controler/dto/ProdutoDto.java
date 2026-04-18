package danielalves.github.GerenciadorVendas.produto.controler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record ProdutoDto(

        UUID id,
        @NotBlank
        String nome,
        @NotBlank
        String sku,
        @NotBlank
        String descricao,
        @Positive
        Double preco,
        @PositiveOrZero
        Integer quantidade) {
}

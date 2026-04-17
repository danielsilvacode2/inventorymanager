package danielalves.github.GerenciadorVendas.produto.Controler;


import danielalves.github.GerenciadorVendas.produto.Controler.dto.ProdutoDto;
import danielalves.github.GerenciadorVendas.produto.Produto;
import danielalves.github.GerenciadorVendas.produto.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("produto")
@AllArgsConstructor
public class ProdutoControler {


    private final ProdutoMapper mapper;
    private final ProdutoService service;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid ProdutoDto dto) {

        Produto produto = mapper.toEntity(dto);
        service.salvar(produto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produto.getId())
                .toUri();

        return ResponseEntity.created(uri).build();

    }


    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> buscarProduto(@PathVariable("id") String id) {

        UUID uuid = UUID.fromString(id);

        return service.buscarProduto(uuid).map(produto -> {
            ProdutoDto dto = mapper.toDto(produto);
            return ResponseEntity.ok(dto);

        }).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable("id") String id, @RequestBody @Valid ProdutoDto dto) {

        UUID uuid = UUID.fromString(id);

        return service.buscarProduto(uuid).map(produto -> {

            produto.setNome(dto.nome());
            produto.setSku(dto.sku());
            produto.setDescricao(dto.descricao());
            produto.setPreco(dto.preco());
            produto.setQuantidade(dto.quantidade());


            service.atualizarProduto(produto);

            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable("id") String id) {


        return service.buscarProduto(UUID.fromString(id)).map(produto -> {
            service.deletarProduto(produto);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());


    }


}

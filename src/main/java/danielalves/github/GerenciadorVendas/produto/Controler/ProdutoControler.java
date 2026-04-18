package danielalves.github.GerenciadorVendas.produto.Controler;


import danielalves.github.GerenciadorVendas.produto.Controler.dto.ProdutoDto;
import danielalves.github.GerenciadorVendas.produto.Produto;
import danielalves.github.GerenciadorVendas.produto.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<ProdutoDto>> pesquisarProduto(
            @RequestParam(value = "nome", required = false)
            String nome,
            @RequestParam(value = "sku", required = false)
            String sku,
            @RequestParam(value = "descricao", required = false)
            String descricao,

            @RequestParam(value = "preco",required = false)
            Double preco
    ) {

        List<ProdutoDto> produtoDtoList = service.pesquisaProduto(nome,sku,descricao,preco)
                .stream()
                .map(produto -> mapper.toDto(produto))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(produtoDtoList);

    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable("id") String id, @RequestBody @Valid ProdutoDto dto) {

        UUID uuid = UUID.fromString(id);

        return service.buscarProduto(uuid).map(produto -> {

            Produto produtoAuxiliar = mapper.toEntity(dto);

            produto.setNome(produtoAuxiliar.getNome());
            produto.setSku(produtoAuxiliar.getSku());
            produto.setDescricao(produtoAuxiliar.getDescricao());
            produto.setPreco(produtoAuxiliar.getPreco());
            produto.setQuantidade(produtoAuxiliar.getQuantidade());


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

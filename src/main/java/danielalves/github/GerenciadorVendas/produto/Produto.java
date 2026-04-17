package danielalves.github.GerenciadorVendas.produto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "produto")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Produto {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
    @Column(name = "descricao", nullable = false, length = 200)
    private String descricao;

    @Column(name = "sku", nullable = false, length = 70, unique = true)
    private String sku;

    @Column(name = "preco", nullable = false)
    private Double preco;
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;


    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime data_cadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime data_atualizacao;


}

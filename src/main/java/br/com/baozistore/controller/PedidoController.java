package br.com.baozistore.controller;

import br.com.baozistore.model.Pedido;
import br.com.baozistore.repository.ClienteRepository;
import br.com.baozistore.repository.PedidoRepository;
import br.com.baozistore.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository repository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    /** Retorna a mensagem de erro, ou null se o pedido for valido. */
    private String validar(Pedido pedido) {
        if (pedido.getClienteId() == null || !clienteRepository.existsById(pedido.getClienteId())) {
            return "Cliente nao encontrado";
        }
        if (pedido.getProdutoId() == null || !produtoRepository.existsById(pedido.getProdutoId())) {
            return "Produto nao encontrado";
        }
        if (pedido.getQuantidade() == null || pedido.getQuantidade() <= 0) {
            return "Quantidade deve ser maior que zero";
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Pedido pedido) {
        String erro = validar(pedido);
        if (erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }
        pedido.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Pedido> listar() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        String erro = validar(pedido);
        if (erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }
        pedido.setId(id);
        return ResponseEntity.ok(repository.save(pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
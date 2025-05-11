package es.upm.dit.isst.isstgrupo07flores.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class ProductoPersonalizado {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private UUID id;

    private BigDecimal costeTotal;

    private UUID floricultorId;

@   OneToMany(mappedBy = "productoPersonalizadoId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FloresEnPersonalizado> floresEnPersonalizados;    

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getCosteTotal() {
        return costeTotal;
    }

    public void setCosteTotal(BigDecimal costeTotal) {
        this.costeTotal = costeTotal;
    }

    public UUID getFloricultorId() {
        return floricultorId;
    }

    public void setFloricultorId(UUID floricultorId) {
        this.floricultorId = floricultorId;
    }

}

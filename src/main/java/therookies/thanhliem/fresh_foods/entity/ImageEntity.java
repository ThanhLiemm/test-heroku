package therookies.thanhliem.fresh_foods.entity;

import javax.persistence.*;

@Entity
@Table(name="image")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="url")
    private String url;
    @Column(name="type")
    private int type;
    @Column(name="status")
    private int status;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
package fr.lille1.univ.android.architecture.btrshop.products.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by charlie on 20/10/16.
 *
 * Immutable model class for a Product.
 */
public final class Product {

    @SerializedName("name")
    private String name;
    @SerializedName("ean")
    private String ean;
    @SerializedName("description")
    private String description;
    @SerializedName("category")
    private String category;
    @SerializedName("poids")
    private String poids;

    /**
     * Constructor.
     *
     * @param name
     * @param ean
     * @param description
     * @param category
     * @param poids
     */
    public Product(String name, String ean, String description, String category, String poids) {
        this.name = name;
        this.ean = ean;
        this.description = description;
        this.category = category;
        this.poids = poids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (getName() != null ? !getName().equals(product.getName()) : product.getName() != null)
            return false;
        if (getEan() != null ? !getEan().equals(product.getEan()) : product.getEan() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(product.getDescription()) : product.getDescription() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(product.getCategory()) : product.getCategory() != null)
            return false;
        return getPoids() != null ? getPoids().equals(product.getPoids()) : product.getPoids() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getEan() != null ? getEan().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getPoids() != null ? getPoids().hashCode() : 0);
        return result;
    }
}
